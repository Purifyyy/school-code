package sk.stuba.fei.uim.vsa.pr2.web.customer;

import com.fasterxml.jackson.annotation.JsonCreator;
import sk.stuba.fei.uim.vsa.pr2.domain.Customer;
import sk.stuba.fei.uim.vsa.pr2.domain.DiscountCoupon;
import sk.stuba.fei.uim.vsa.pr2.domain.Reservation;
import sk.stuba.fei.uim.vsa.pr2.web.car.CarDto;

import java.util.List;

public class CustomerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<CarDto> cars;
    private List<DiscountCoupon> coupons;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CustomerDto() {}

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

    public List<CarDto> getCars() {
        return cars;
    }

    public void setCars(List<CarDto> cars) {
        this.cars = cars;
    }

    public List<DiscountCoupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<DiscountCoupon> coupons) {
        this.coupons = coupons;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", cars=" + cars +
                ", coupons=" + coupons +
                '}';
    }
}


