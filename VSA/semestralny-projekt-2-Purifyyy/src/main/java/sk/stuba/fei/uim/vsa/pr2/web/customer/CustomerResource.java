package sk.stuba.fei.uim.vsa.pr2.web.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.domain.Car;
import sk.stuba.fei.uim.vsa.pr2.domain.Customer;
import sk.stuba.fei.uim.vsa.pr2.domain.Reservation;
import sk.stuba.fei.uim.vsa.pr2.service.CarParkService;
import sk.stuba.fei.uim.vsa.pr2.web.car.CarDto;
import sk.stuba.fei.uim.vsa.pr2.web.car.CarResponse;
import sk.stuba.fei.uim.vsa.pr2.web.carpark.CarParkDto;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Path("/users")
public class CustomerResource {

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
            CustomerDto cBody = json.readValue(body, CustomerDto.class);
            Customer person = new Customer();
            person.setName(cBody.getFirstName());
            person.setSurname(cBody.getLastName());
            person.setEmail(cBody.getEmail());
            List<Car> carList = new ArrayList<>();
            if(cBody.getCars() != null) {
                for(CarDto car : cBody.getCars()) {
                    carList.add(new Car(car.getVrp(), car.getBrand(), car.getModel(), car.getColour(), person));
                }
                person.setCars(carList);
            }
            System.out.println(person);
            person = (Customer) carParkService.persistUser(person);
            CustomerResponse customerResponse = new CustomerResponse(person);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(json.writeValueAsString(customerResponse))
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
            CustomerDto cBody = json.readValue(body, CustomerDto.class);
            Customer customer = new Customer(cBody.getFirstName(), cBody.getLastName(), cBody.getEmail());
            customer.setId(id);
            customer = (Customer) carParkService.updateUser(customer);
            if(customer == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(new CustomerResponse(customer)))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long id) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Customer c = (Customer) carParkService.getUser(id);
        if(c == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(new CustomerResponse(c)))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @QueryParam("email") String email) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        List<Customer> customerList = new ArrayList<>();
        if(email != null) {
            Customer c = (Customer) carParkService.getUser(email);
            if(c != null) {
                customerList.add(c);
            }
        } else {
            customerList = (List<Customer>)(Object)carParkService.getUsers();
        }
        List<CustomerResponse> customerResponseList = new ArrayList<>();
        for(Customer c : customerList) {
            customerResponseList.add(new CustomerResponse(c));
        }
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(customerResponseList))
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

        Customer c = (Customer) carParkService.deleteUser(id);
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
