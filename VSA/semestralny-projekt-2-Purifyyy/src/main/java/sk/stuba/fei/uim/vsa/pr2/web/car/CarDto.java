package sk.stuba.fei.uim.vsa.pr2.web.car;

import com.fasterxml.jackson.annotation.JsonCreator;
import sk.stuba.fei.uim.vsa.pr2.domain.Car;
import sk.stuba.fei.uim.vsa.pr2.domain.ParkingSpot;
import sk.stuba.fei.uim.vsa.pr2.domain.Reservation;
import sk.stuba.fei.uim.vsa.pr2.web.customer.CustomerDto;

import java.util.List;

public class CarDto {

    private Long id;
    private String brand;
    private String model;
    private String vrp;
    private String colour;
    private CustomerDto owner;
    private List<Reservation> reservations;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CarDto() {}

    @Override
    public String toString() {
        return "CarDto{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", vrp='" + vrp + '\'' +
                ", colour='" + colour + '\'' +
                ", owner=" + owner +
                ", reservations=" + reservations +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVrp() {
        return vrp;
    }

    public void setVrp(String vrp) {
        this.vrp = vrp;
    }

    public String getColour() {
        return colour;
    }

    public void setColor(String color) {
        this.colour = color;
    }

    public CustomerDto getOwner() {
        return owner;
    }

    public void setOwner(CustomerDto owner) {
        this.owner = owner;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
