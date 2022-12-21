package sk.stuba.fei.uim.vsa.pr1a.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="PARKING_SPOT")
public class ParkingSpot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String spotIdentifier;

    @ManyToOne
    @JoinColumns ({
            @JoinColumn(name="carParkLocation", referencedColumnName = "CARPARKLOCATION_ID"),
            @JoinColumn(name="floorIdentifier", referencedColumnName = "floorIdentifier")
    })
    private CarParkFloor floorLocation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpotIdentifier() {
        return spotIdentifier;
    }

    public void setSpotIdentifier(String spotIdentifier) {
        this.spotIdentifier = spotIdentifier;
    }

    public CarParkFloor getFloorLocation() {
        return floorLocation;
    }

    public void setFloorLocation(CarParkFloor floorLocation) {
        this.floorLocation = floorLocation;
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "spotIdentifier='" + spotIdentifier + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpot that = (ParkingSpot) o;
        return Objects.equals(id, that.id) && Objects.equals(spotIdentifier, that.spotIdentifier) && Objects.equals(floorLocation, that.floorLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, spotIdentifier, floorLocation);
    }
}
