package sk.stuba.fei.uim.vsa.pr2.web.car;

import sk.stuba.fei.uim.vsa.pr2.domain.Car;
import sk.stuba.fei.uim.vsa.pr2.domain.Reservation;
import sk.stuba.fei.uim.vsa.pr2.web.customer.CustomerDto;

import java.util.ArrayList;
import java.util.List;

public class CarResponse {

    private Long id;
    private String brand;
    private String model;
    private String vrp;
    private String colour;
    private Long owner;
    private List<Long> reservations;

    public CarResponse(Car c, List<Reservation> reservations) {
        this.id = c.getId();
        this.brand = c.getBrand();
        this.model = c.getModel();
        this.vrp = c.getRegistrationNumber();
        this.colour = c.getColor();
        this.owner = c.getOwner().getId();
        this.reservations = new ArrayList<>();
        for(Reservation res : reservations) {
            this.reservations.add(res.getId());
        }
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

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public List<Long> getReservations() {
        return reservations;
    }

    public void setReservations(List<Long> reservations) {
        this.reservations = reservations;
    }
}
