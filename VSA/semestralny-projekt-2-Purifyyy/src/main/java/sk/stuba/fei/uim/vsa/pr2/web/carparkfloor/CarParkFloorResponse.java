package sk.stuba.fei.uim.vsa.pr2.web.carparkfloor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import sk.stuba.fei.uim.vsa.pr2.CarParkFloorID;
import sk.stuba.fei.uim.vsa.pr2.domain.CarPark;
import sk.stuba.fei.uim.vsa.pr2.domain.CarParkFloor;
import sk.stuba.fei.uim.vsa.pr2.domain.ParkingSpot;

import java.util.ArrayList;
import java.util.List;

public class CarParkFloorResponse {

    private String identifier;
    private Long carPark;
    private List<Long> spots;

    public CarParkFloorResponse(CarParkFloor cpf) {
        this.identifier = cpf.getId().getFloorIdentifier();
        this.carPark = cpf.getId().getCarParkLocationID();
        this.spots = new ArrayList<>();
        for(ParkingSpot ps : cpf.getSpots()) {
            spots.add(ps.getId());
        }
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CarParkFloorResponse() {}

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Long getCarPark() {
        return carPark;
    }

    public void setCarPark(Long carPark) {
        this.carPark = carPark;
    }

    public List<Long> getSpots() {
        return spots;
    }

    public void setSpots(List<Long> spots) {
        this.spots = spots;
    }
}
