package sk.stuba.fei.uim.vsa.pr2;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import sk.stuba.fei.uim.vsa.pr2.web.car.CarResource;
import sk.stuba.fei.uim.vsa.pr2.web.carpark.CarParkResource;
import sk.stuba.fei.uim.vsa.pr2.web.carparkfloor.CarParkFloorResource;
import sk.stuba.fei.uim.vsa.pr2.web.customer.CustomerResource;
import sk.stuba.fei.uim.vsa.pr2.web.discountcoupon.DiscountCouponResource;
import sk.stuba.fei.uim.vsa.pr2.web.parkingspot.ParkingSpotResource;
import sk.stuba.fei.uim.vsa.pr2.web.parkingspot.ParkingSpotResource2;
import sk.stuba.fei.uim.vsa.pr2.web.reservation.ReservationResource;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class Project2Application extends Application {

    static final Set<Class<?>> appClasses = new HashSet<>();

    static {
        appClasses.add(CarParkResource.class);
        appClasses.add(CarParkFloorResource.class);
        appClasses.add(ParkingSpotResource.class);
        appClasses.add(ParkingSpotResource2.class);
        appClasses.add(CarResource.class);
        appClasses.add(CustomerResource.class);
        appClasses.add(ReservationResource.class);
        appClasses.add(DiscountCouponResource.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return appClasses;
    }
}
