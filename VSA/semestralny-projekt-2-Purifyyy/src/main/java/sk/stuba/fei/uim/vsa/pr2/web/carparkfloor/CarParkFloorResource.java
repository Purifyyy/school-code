package sk.stuba.fei.uim.vsa.pr2.web.carparkfloor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.domain.CarPark;
import sk.stuba.fei.uim.vsa.pr2.domain.CarParkFloor;
import sk.stuba.fei.uim.vsa.pr2.domain.Customer;
import sk.stuba.fei.uim.vsa.pr2.domain.ParkingSpot;
import sk.stuba.fei.uim.vsa.pr2.service.CarParkService;
import sk.stuba.fei.uim.vsa.pr2.web.carpark.CarParkDto;
import sk.stuba.fei.uim.vsa.pr2.web.parkingspot.ParkingSpotDto;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Path("/carparks/{id}/floors")
public class CarParkFloorResource {

    private final CarParkService carParkService = new CarParkService();
    private final ObjectMapper json = new ObjectMapper();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFloors(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long id) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        CarPark cp = (CarPark) carParkService.getCarPark(id);
        if(cp == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        List<CarParkFloorResponse> carParkFloorResponses = new ArrayList<>();
        for(CarParkFloor cpf : cp.getFloors()) {
            carParkFloorResponses.add(new CarParkFloorResponse(cpf));
        }
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(carParkFloorResponses))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @GET
    @Path("/{identifier}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long id, @PathParam("identifier") String identifier) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        CarParkFloor cpf = (CarParkFloor) carParkService.getCarParkFloor(id, identifier);
        if(cpf == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(new CarParkFloorResponse(cpf)))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long id, String body) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            CarParkFloorDto cpfBody = json.readValue(body, CarParkFloorDto.class);
            List<ParkingSpot> parkingSpotList = new ArrayList<>();
            if(cpfBody.getSpots() != null) {
                for(ParkingSpotDto spotDto : cpfBody.getSpots()) {
                    ParkingSpot ps = new ParkingSpot();
                    ps.setSpotIdentifier(spotDto.getIdentifier());
                    ps.setFree(true);
                    parkingSpotList.add(ps);
                }
            }
            CarParkFloor cpf = (CarParkFloor) carParkService.createCarParkFloorWithSpots(id, cpfBody.getIdentifier(), parkingSpotList);
            if(cpf == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response
                    .status(Response.Status.CREATED)
                        .entity(json.writeValueAsString(new CarParkFloorResponse(cpf)))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/{identifier}")
    public Response delete(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long id, @PathParam("identifier") String identifier) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        CarParkFloor cpf = (CarParkFloor) carParkService.deleteCarParkFloor(id, identifier);
        if(cpf == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    private Customer authUser(String authHeader) {
        String base64Encoded =  authHeader.substring("Basic ".length());
        String decoded = new String(Base64.getDecoder().decode(base64Encoded));
        return (Customer) carParkService.getAuthUser(decoded.split(":")[0], Long.parseLong(decoded.split(":")[1]));
    }
}
