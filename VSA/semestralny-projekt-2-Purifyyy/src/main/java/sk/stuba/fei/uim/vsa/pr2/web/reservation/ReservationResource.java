package sk.stuba.fei.uim.vsa.pr2.web.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;
import sk.stuba.fei.uim.vsa.pr2.domain.Customer;
import sk.stuba.fei.uim.vsa.pr2.domain.ParkingSpot;
import sk.stuba.fei.uim.vsa.pr2.domain.Reservation;
import sk.stuba.fei.uim.vsa.pr2.service.CarParkService;
import sk.stuba.fei.uim.vsa.pr2.web.customer.CustomerDto;
import sk.stuba.fei.uim.vsa.pr2.web.parkingspot.ParkingSpotDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

@Path("/reservations")
public class ReservationResource {

    private final CarParkService carParkService = new CarParkService();
    private final ObjectMapper json = new ObjectMapper();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, String body) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        try {
            ReservationDto rBody = json.readValue(body, ReservationDto.class);
            if(rBody.getCar().getId() == null && rBody.getSpot().getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            Reservation res;
            if(rBody.getCoupon() != null) {
                res = (Reservation) carParkService.createReservationWithCoupon(rBody.getSpot().getId(), rBody.getCar().getId(), rBody.getCoupon().getId());
            } else {
                res = (Reservation) carParkService.createReservation(rBody.getSpot().getId(), rBody.getCar().getId());
            }
            if(res == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            ReservationResponse reservationResponse = new ReservationResponse(res);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(json.writeValueAsString(reservationResponse))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/{id}/end" )
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response end(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long id) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        Reservation res = (Reservation) carParkService.getReservationById(id);
        if(res == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if(!Objects.equals(res.getCar().getOwner().getId(), cst.getId())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Reservation reservation = (Reservation) carParkService.endReservationNew(id);
        if(reservation == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        ReservationResponse reservationResponse = new ReservationResponse(reservation);
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(reservationResponse))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @QueryParam("user") Long userId, @QueryParam("spot") Long spotId, @QueryParam("date")String date) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if((spotId == null && date != null) || (spotId != null && date == null)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        List<ReservationResponse> returnList = new ArrayList<>();
        if(userId != null && spotId != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date convertedCurrentDate;
            try {
                convertedCurrentDate = sdf.parse(date);
            } catch (ParseException e) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            List<Reservation> reservationList = (List<Reservation>)(Object)carParkService.getReservations(spotId, convertedCurrentDate);
            for(Reservation res : reservationList) {
                if(Objects.equals(res.getCar().getOwner().getId(), userId)) {
                    returnList.add(new ReservationResponse(res));
                }
            }
        } else if(spotId != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date convertedCurrentDate;
            try {
                convertedCurrentDate = sdf.parse(date);
            } catch (ParseException e) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            List<Reservation> reservationList = (List<Reservation>)(Object)carParkService.getReservations(spotId, convertedCurrentDate);
            for(Reservation res : reservationList) {
                returnList.add(new ReservationResponse(res));
            }
        } else if(userId != null) {
            List<Reservation> reservationList = (List<Reservation>)(Object)carParkService.getUserReservations(userId);
            for(Reservation res : reservationList) {
                returnList.add(new ReservationResponse(res));
            }
        }
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(returnList))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long id) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Reservation reservation = (Reservation) carParkService.getReservationById(id);
        if(reservation == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(new ReservationResponse(reservation)))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long id, String body) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            ReservationDto rBody = json.readValue(body, ReservationDto.class);
            Reservation res = new Reservation();
            res.setId(id);
            res.setStartedAt(rBody.getStart());
            res = (Reservation) carParkService.updateReservation(res);
            if(res == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(new ReservationResponse(res)))
                    .build();
        } catch (JsonProcessingException e) {
            System.out.println("processin err");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    private Customer authUser(String authHeader) {
        String base64Encoded =  authHeader.substring("Basic ".length());
        String decoded = new String(Base64.getDecoder().decode(base64Encoded));
        return (Customer) carParkService.getAuthUser(decoded.split(":")[0], Long.parseLong(decoded.split(":")[1]));
    }
}
