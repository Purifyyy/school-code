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

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Path("/parkingspots/{id}")
public class ParkingSpotResource2 {

    private final CarParkService carParkService = new CarParkService();
    private final ObjectMapper json = new ObjectMapper();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long id) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        ParkingSpot ps = (ParkingSpot) carParkService.getParkingSpot(id);
        if(ps == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        ParkingSpotDto psDto = new ParkingSpotDto(ps, (List<Reservation>)(Object)carParkService.getReservations(ps.getId()));
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(psDto))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long id, String body) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            ParkingSpotDto psDto = json.readValue(body, ParkingSpotDto.class);
            ParkingSpot ps = new ParkingSpot();
            ps.setId(id);
            ps.setSpotIdentifier(psDto.getIdentifier());
            ps.setFree(ps.isFree());
            System.out.println(ps);
            ps = (ParkingSpot) carParkService.updateParkingSpot(ps);
            if(ps == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(new ParkingSpotDto(ps, (List<Reservation>)(Object)carParkService.getReservations(ps.getId()))))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    public Response delete(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long id) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        ParkingSpot ps = (ParkingSpot) carParkService.deleteParkingSpot(id);
        if(ps == null) {
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
