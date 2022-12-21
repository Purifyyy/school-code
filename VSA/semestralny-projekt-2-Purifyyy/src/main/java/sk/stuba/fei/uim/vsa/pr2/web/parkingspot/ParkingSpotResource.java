package sk.stuba.fei.uim.vsa.pr2.web.parkingspot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.domain.*;
import sk.stuba.fei.uim.vsa.pr2.service.CarParkService;
import sk.stuba.fei.uim.vsa.pr2.web.carpark.CarParkDto;
import sk.stuba.fei.uim.vsa.pr2.web.carparkfloor.CarParkFloorDto;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/carparks/{id}/floors/{identifier}/spots")
public class ParkingSpotResource {

    private final CarParkService carParkService = new CarParkService();
    private final ObjectMapper json = new ObjectMapper();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long id, @PathParam("identifier") String identifier, String body) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            ParkingSpotDto psBody = json.readValue(body, ParkingSpotDto.class);
            ParkingSpot ps = (ParkingSpot) carParkService.createParkingSpot(id, identifier, psBody.getIdentifier());
            if(ps == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            psBody = new ParkingSpotDto(ps, (List<Reservation>)(Object) carParkService.getReservations(ps.getId()));
            return Response
                    .status(Response.Status.CREATED)
                    .entity(json.writeValueAsString(psBody))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long id, @PathParam("identifier") String identifier) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        CarPark cp = (CarPark) carParkService.getCarPark(id);
        CarParkFloor cpf = (CarParkFloor) carParkService.getCarParkFloor(id, identifier);
        if(cp == null || cpf == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<ParkingSpot> spots = (List<ParkingSpot>)(Object)carParkService.getParkingSpots(id, identifier);
        List<ParkingSpotDto> psList = new ArrayList<>();
        for(ParkingSpot ps : spots) {
            psList.add(new ParkingSpotDto(ps, (List<Reservation>)(Object) carParkService.getReservations(ps.getId())));
        }
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(psList))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    private Customer authUser(String authHeader) {
        String base64Encoded =  authHeader.substring("Basic ".length());
        String decoded = new String(Base64.getDecoder().decode(base64Encoded));
        return (Customer) carParkService.getAuthUser(decoded.split(":")[0], Long.parseLong(decoded.split(":")[1]));
    }
}
