package sk.stuba.fei.uim.vsa.pr2.web.customer;

import sk.stuba.fei.uim.vsa.pr2.domain.Car;
import sk.stuba.fei.uim.vsa.pr2.domain.Customer;
import sk.stuba.fei.uim.vsa.pr2.domain.DiscountCoupon;
import sk.stuba.fei.uim.vsa.pr2.web.car.CarDto;

import java.util.ArrayList;
import java.util.List;

public class CustomerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Long> cars;
    private List<Long> coupons;

    public CustomerResponse(Customer c) {
        this.id = c.getId();
        this.firstName = c.getName();
        this.lastName= c.getSurname();
        this.email = c.getEmail();
        this.cars = new ArrayList<>();
        for(Car car : c.getCars()) {
            this.cars.add(car.getId());
        }
        this.coupons = new ArrayList<>();
        for(DiscountCoupon coupon : c.getCoupons()) {
            this.coupons.add(coupon.getId());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Long> getCars() {
        return cars;
    }

    public void setCars(List<Long> cars) {
        this.cars = cars;
    }

    public List<Long> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Long> coupons) {
        this.coupons = coupons;
    }
}
