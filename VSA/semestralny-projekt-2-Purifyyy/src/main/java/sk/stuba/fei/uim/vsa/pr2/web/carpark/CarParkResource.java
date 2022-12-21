package sk.stuba.fei.uim.vsa.pr2.web.carpark;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.CarParkFloorID;
import sk.stuba.fei.uim.vsa.pr2.domain.*;
import sk.stuba.fei.uim.vsa.pr2.service.CarParkService;
import sk.stuba.fei.uim.vsa.pr2.web.carparkfloor.CarParkFloorDto;
import sk.stuba.fei.uim.vsa.pr2.web.parkingspot.ParkingSpotDto;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Path("/carparks")
public class CarParkResource {

    private final CarParkService carParkService = new CarParkService();
    private final ObjectMapper json = new ObjectMapper();

    @GET
    @Path("/{id}/spots")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long id, @QueryParam("free") Boolean free) {
        Customer c = authUser(authorization);
        if(c == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        List<ParkingSpotDto> psdList = new ArrayList<>();
        List<ParkingSpot> list = new ArrayList<>();
        if (free == null) {
            list = (List<ParkingSpot>)(Object) carParkService.getParkingSpotsList(id);
        } else {
            CarPark cp = (CarPark) carParkService.getCarPark(id);
            if(cp == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            if(free) {
                list = carParkService.getAvailableParkingSpots(cp.getName())
                        .values()
                        .stream()
                        .flatMap(List::stream)
                        .map(spot -> (ParkingSpot) spot)
                        .collect(Collectors.toList());
            } else {
                list = carParkService.getOccupiedParkingSpots(cp.getName())
                        .values()
                        .stream()
                        .flatMap(List::stream)
                        .map(spot -> (ParkingSpot) spot)
                        .collect(Collectors.toList());
            }
        }
        for(ParkingSpot ps : list) {
            psdList.add(new ParkingSpotDto(ps, (List<Reservation>)(Object) carParkService.getReservations(ps.getId())));
        }
        try {
            return Response.status(Response.Status.OK).entity(json.writeValueAsString(psdList)).build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @QueryParam("name") String name) {
        Customer c = authUser(authorization);
        if(c == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        List<CarParkResponse> carParks = new ArrayList<>();
        if (name == null) {
            List<CarPark> cpList = (List<CarPark>)(Object)carParkService.getCarParks();
            for(CarPark cp : cpList) {
                carParks.add(new CarParkResponse(cp));
            }
            try {
                return Response
                        .status(Response.Status.OK)
                        .entity(json.writeValueAsString(carParks))
                        .build();
            } catch (JsonProcessingException e) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
        CarPark cp = (CarPark) carParkService.getCarPark(name);
        if(cp == null) {
            throw new NotFoundException();
        }
        carParks.add(new CarParkResponse(cp));
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(carParks))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, String body) {
        Customer c = authUser(authorization);
        if(c == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        try {
            CarParkDto cpBody = json.readValue(body, CarParkDto.class);
            CarPark nCp = new CarPark();
            nCp.setName(cpBody.getName());
            nCp.setAddress(cpBody.getAddress());
            nCp.setPricePerHour(cpBody.getPrices());

            List<String> floorIdentifiers = new ArrayList<>();
            List<String> spotIdentifiers = new ArrayList<>();
            if(cpBody.getFloors() != null) {
                for (CarParkFloorDto cpfDto : cpBody.getFloors()) {
                    if (floorIdentifiers.contains(cpfDto.getIdentifier())) {
                        return Response.status(Response.Status.CONFLICT).build();
                    }
                    floorIdentifiers.add(cpfDto.getIdentifier());
                    if(cpfDto.getSpots() != null) {
                        for (ParkingSpotDto psDto : cpfDto.getSpots()) {
                            if (spotIdentifiers.contains(psDto.getIdentifier())) {
                                return Response.status(Response.Status.CONFLICT).build();
                            }
                            spotIdentifiers.add(psDto.getIdentifier());
                        }
                    }
                }
                List<CarParkFloor> floors = new ArrayList<>();
                for (CarParkFloorDto cpfDto : cpBody.getFloors()) {
                    CarParkFloor nCpf = new CarParkFloor();
                    nCpf.setCarParkLocation(nCp);

                    CarParkFloorID TESTID = new CarParkFloorID();
                    TESTID.setFloorIdentifier(cpfDto.getIdentifier());
                    nCpf.setId(TESTID);

                    List<ParkingSpot> parkingSpotList = new ArrayList<>();
                    if(cpfDto.getSpots() != null) {
                        for (ParkingSpotDto psDto : cpfDto.getSpots()) {
                            ParkingSpot nPs = new ParkingSpot();
                            nPs.setFree(true);
                            nPs.setSpotIdentifier(psDto.getIdentifier());
                            nPs.setFloorLocation(nCpf);
                            parkingSpotList.add(nPs);
                        }
                    }
                        nCpf.setSpots(parkingSpotList);
                    floors.add(nCpf);
                }
                nCp.setFloors(floors);
            } else {
                nCp.setFloors(new ArrayList<>());
            }
            nCp = (CarPark) carParkService.createCarParkWithFloorsAndSpots(nCp);
            if(nCp == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response
                    .status(Response.Status.CREATED)
                    .entity(json.writeValueAsString(new CarParkResponse(nCp)))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long id) {
        Customer c = authUser(authorization);
        if(c == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        CarPark cp = (CarPark) carParkService.getCarPark(id);
        if(cp == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(new CarParkResponse(cp)))
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
            CarParkDto cpDto = json.readValue(body, CarParkDto.class);
            CarPark cp = new CarPark();
            cp.setId(id);
            cp.setAddress(cpDto.getAddress());
            cp.setPricePerHour(cpDto.getPrices());
            cp.setName(cpDto.getName());
            cp = (CarPark) carParkService.updateCarPark(cp);
            if(cp == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(new CarParkResponse(cp)))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long id) {
        Customer c = authUser(authorization);
        if(c == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        CarPark cp = (CarPark) carParkService.deleteCarPark(id);
        if(cp == null) {
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
