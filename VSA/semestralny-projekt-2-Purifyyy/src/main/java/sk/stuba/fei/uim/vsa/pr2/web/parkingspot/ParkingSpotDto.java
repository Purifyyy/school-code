package sk.stuba.fei.uim.vsa.pr2.web.parkingspot;

import com.fasterxml.jackson.annotation.JsonCreator;
import sk.stuba.fei.uim.vsa.pr2.domain.ParkingSpot;
import sk.stuba.fei.uim.vsa.pr2.domain.Reservation;

import java.util.List;

public class ParkingSpotDto {

    private Long id;
    private String identifier;
    private String carParkFloor;
    private Long carPark;
    private Boolean free;
    private List<Reservation> reservations;

    public ParkingSpotDto(ParkingSpot ps, List<Reservation> reservations) {
        this.id = ps.getId();
        this.identifier = ps.getSpotIdentifier();
        this.carParkFloor = ps.getFloorLocation().getId().getFloorIdentifier();
        this.carPark = ps.getFloorLocation().getCarParkLocation().getId();
        this.free = ps.isFree();
        this.reservations = reservations;
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ParkingSpotDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCarParkFloor() {
        return carParkFloor;
    }

    public void setCarParkFloor(String carParkFloor) {
        this.carParkFloor = carParkFloor;
    }

    public Long getCarPark() {
        return carPark;
    }

    public void setCarPark(Long carPark) {
        this.carPark = carPark;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "ParkingSpotDto{" +
                "id=" + id +
                ", identifier='" + identifier + '\'' +
                ", carParkFloor='" + carParkFloor + '\'' +
                ", carPark=" + carPark +
                ", free=" + free +
                ", reservations=" + reservations +
                '}';
    }
}
