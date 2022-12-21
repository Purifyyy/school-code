package sk.stuba.fei.uim.vsa.pr1a.entities;

import sk.stuba.fei.uim.vsa.pr1a.CarParkFloorID;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="CAR_PARK_FLOOR")
public class CarParkFloor implements Serializable {

    @EmbeddedId
    private CarParkFloorID id;

    @ManyToOne
    @MapsId("carParkLocationID")
    private CarPark carParkLocation;

    @OneToMany(cascade=CascadeType.REMOVE, mappedBy="floorLocation")
    private List<ParkingSpot> spots;

    public CarParkFloor() {
    }
    public CarParkFloor(CarParkFloorID id) {
        this.id = id;
    }

    public CarParkFloorID getId() {
        return id;
    }

    public void setId(CarParkFloorID id) {
        this.id = id;
    }

    public List<ParkingSpot> getSpots() {
        return spots;
    }

    public void setSpots(List<ParkingSpot> spots) {
        this.spots = spots;
    }

    public CarPark getCarParkLocation() {
        return carParkLocation;
    }

    public void setCarParkLocation(CarPark location) {
        this.carParkLocation = location;
    }

    @Override
    public String toString() {
        return "CarParkFloor{" +
                "id=" + id.getFloorIdentifier() +
                ", carParkLocation=" + carParkLocation +
                '}';
    }
}