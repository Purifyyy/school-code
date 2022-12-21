package sk.stuba.fei.uim.vsa.pr2.web.discountcoupon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.domain.Customer;
import sk.stuba.fei.uim.vsa.pr2.domain.DiscountCoupon;
import sk.stuba.fei.uim.vsa.pr2.domain.ParkingSpot;
import sk.stuba.fei.uim.vsa.pr2.service.CarParkService;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Path("/coupons")
public class DiscountCouponResource {

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
            DiscountCouponDto dcBody = json.readValue(body, DiscountCouponDto.class);
            DiscountCoupon coupon = (DiscountCoupon) carParkService.createDiscountCoupon(dcBody.getName(), dcBody.getDiscount());
            if(coupon == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response
                    .status(Response.Status.CREATED)
                    .entity(json.writeValueAsString(new DiscountCouponResponse(coupon)))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @QueryParam("user") Long userId) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        List<DiscountCoupon> couponList;
        if(userId == null) {
            couponList = (List<DiscountCoupon>)(Object)carParkService.getAllCoupons();
        } else {
            couponList = (List<DiscountCoupon>)(Object)carParkService.getCoupons(userId);
        }
        List<DiscountCouponResponse> responseList = new ArrayList<>();
        for(DiscountCoupon dc : couponList) {
            responseList.add(new DiscountCouponResponse(dc));
        }
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(responseList))
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

        DiscountCoupon dc = (DiscountCoupon) carParkService.getCoupon(id);
        if(dc == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(new DiscountCouponResponse(dc)))
                    .build();
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/{id}/give/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long couponId, @PathParam("userId") Long userId) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Customer customer = (Customer) carParkService.getUser(userId);
        DiscountCoupon discountCoupon = (DiscountCoupon) carParkService.getCoupon(couponId);
        if(customer == null || discountCoupon == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        carParkService.giveCouponToUser(discountCoupon.getId(), customer.getId());
        discountCoupon = (DiscountCoupon) carParkService.getCoupon(discountCoupon.getId());
        if(Objects.equals(discountCoupon.getHolder().getId(), customer.getId())) {
            try {
                return Response
                        .status(Response.Status.OK)
                        .entity(json.writeValueAsString(new DiscountCouponResponse(discountCoupon)))
                        .build();
            } catch (JsonProcessingException e) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorization, @PathParam("id") Long id) {
        Customer cst = authUser(authorization);
        if(cst == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        DiscountCoupon dc = (DiscountCoupon) carParkService.deleteCoupon(id);
        if(dc == null) {
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
