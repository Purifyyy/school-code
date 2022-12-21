package sk.stuba.fei.uim.vsa.pr2;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CarParkFloorID implements Serializable {

    private Long carParkLocationID;
    private String floorIdentifier;

    public Long getCarParkLocationID() {
        return carParkLocationID;
    }

    public CarParkFloorID() {
    }

    public CarParkFloorID(Long carParkLocationID, String floorIdentifier) {
        this.carParkLocationID = carParkLocationID;
        this.floorIdentifier = floorIdentifier;
    }

    public void setCarParkLocationID(Long carParkLocationID) {
        this.carParkLocationID = carParkLocationID;
    }

    public String getFloorIdentifier() {
        return floorIdentifier;
    }

    public void setFloorIdentifier(String floorIdentifier) {
        this.floorIdentifier = floorIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarParkFloorID that = (CarParkFloorID) o;
        return carParkLocationID.equals(that.carParkLocationID) && floorIdentifier.equals(that.floorIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carParkLocationID, floorIdentifier);
    }
}
