package sk.stuba.fei.uim.vsa.pr2.web.car;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.domain.Car;
import sk.stuba.fei.uim.vsa.pr2.domain.Customer;
import sk.stuba.fei.uim.vsa.pr2.domain.ParkingSpot;
import sk.stuba.fei.uim.vsa.pr2.domain.Reservation;
import sk.stuba.fei.uim.vsa.pr2.service.CarParkService;
import sk.stuba.fei.uim.vsa.pr2.web.parkingspot.ParkingSpotDto;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Path("/cars")
public class CarResource {

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
            CarDto cBody = json.readValue(body, CarDto.class);
            if(cBody.getBrand() ==  null || cBody.getModel() ==  null || cBody.getVrp() ==  null ||
                    cBody.getColour() ==  null || cBody.getOwner() == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            Customer customer = null;
            if(cBody.getOwner().getId() != null) {
                customer = (Customer) carParkService.getUser(cBody.getOwner().getId());
                if(customer == null) {
                    return Response.status(Response.Status.BAD_REQUEST).build();
                }
            }
            customer = (Customer) carParkService.createUser(cBody.getOwner().getFirstName(), cBody.getOwner().getLastName(), cBody.getOwner().getEmail());
            if(customer == null) {
                return Response.status(Response.Status.CONFLICT).build();
            }
//            if(customer == null) {
//                customer = (Customer) carParkService.getUser(cBody.getOwner().getEmail());
//                if(customer == null) {
//                    customer = (Customer) carParkService.createUser(cBody.getOwner().getFirstName(), cBody.getOwner().getLastName(), cBody.getOwner().getEmail());
//                    if(customer == null) {
//                        return Response.status(Response.Status.CONFLICT).build();
//                    }
//                }
//            }
            Car c = (Car) carParkService.createCar(customer.getId(), cBody.getBrand(), cBody.getModel(), cBody.getColour(), cBody.getVrp());
            if(c == null) {
                return Response.status(Response.Status.CONFLICT).build();
            }
            CarResponse carResponse = new CarResponse(c, (List<Reservation>)(Object) carParkService.getReservationsByCarId(c.getId()));
            return Response
                    .status(Response.Status.CREATED)
                    .entity(json.writeValueAsString(carResponse))
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
        Customer c = authUser(authorization);
        if(c == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            CarDto cBody = json.readValue(body, CarDto.class);
            Car car = new Car();
            car.setId(id);
            car.setBrand(cBody.getBrand());
            car.setColor(cBody.getColour());
            car.setModel(cBody.getModel());
            car.setRegistrationNumber(cBody.getVrp());
            car = (Car) carParkService.updateCar(car);
            if(car == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(new CarResponse(car, (List<Reservation>)(Object)carParkService.getReservationsByCarId(car.getId()))))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @QueryParam("user") Long userId, @QueryParam("vrp") String vrp) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        List<Car> carList = (List<Car>)(Object)carParkService.getAllCars();
        List<CarResponse> carResponseList = new ArrayList<>();
        try {
            if (userId != null && vrp != null) {
                for(Car c : carList) {
                    if(Objects.equals(c.getOwner().getId(), userId) && Objects.equals(c.getRegistrationNumber(), vrp)) {
                        carResponseList.add(new CarResponse(c, (List<Reservation>) (Object) carParkService.getReservationsByCarId(c.getId())));
                        break;
                    }
                }
            } else if (userId != null) {
                for(Car c : carList) {
                    if(Objects.equals(c.getOwner().getId(), userId)) {
                        carResponseList.add(new CarResponse(c, (List<Reservation>) (Object) carParkService.getReservationsByCarId(c.getId())));
                    }
                }
            } else if (vrp != null) {
                for(Car c : carList) {
                    if(Objects.equals(c.getRegistrationNumber(), vrp)) {
                        carResponseList.add(new CarResponse(c, (List<Reservation>) (Object) carParkService.getReservationsByCarId(c.getId())));
                        break;
                    }
                }
            } else {
                for(Car c : carList) {
                    carResponseList.add(new CarResponse(c, (List<Reservation>) (Object) carParkService.getReservationsByCarId(c.getId())));
                }
            }
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(carResponseList))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long id) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Car c = (Car) carParkService.getCar(id);
        if(c == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(new CarResponse(c, (List<Reservation>) (Object) carParkService.getReservationsByCarId(c.getId()))))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long id) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Car c = (Car) carParkService.deleteCar(id);
        if(c == null) {
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
