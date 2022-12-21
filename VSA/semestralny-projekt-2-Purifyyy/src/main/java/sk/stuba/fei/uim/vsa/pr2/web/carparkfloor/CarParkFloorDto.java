package sk.stuba.fei.uim.vsa.pr2.web.carparkfloor;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import sk.stuba.fei.uim.vsa.pr2.CarParkFloorID;
import sk.stuba.fei.uim.vsa.pr2.domain.CarPark;
import sk.stuba.fei.uim.vsa.pr2.domain.CarParkFloor;
import sk.stuba.fei.uim.vsa.pr2.domain.ParkingSpot;
import sk.stuba.fei.uim.vsa.pr2.web.parkingspot.ParkingSpotDto;

import java.util.List;

public class CarParkFloorDto {

    private String identifier;
    private Long carPark;
    private List<ParkingSpotDto> spots;

//    public CarParkFloorDto(CarParkFloor cpf) {
//        this.identifier = cpf.getId().getFloorIdentifier();
//        this.carPark = cpf.getId().getCarParkLocationID();
//        this.spots = cpf.getSpots();
//    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CarParkFloorDto() {}

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

    public List<ParkingSpotDto> getSpots() {
        return spots;
    }

    public void setSpots(List<ParkingSpotDto> spots) {
        this.spots = spots;
    }

    @Override
    public String toString() {
        return "CarParkFloorDto{" +
                "identifier='" + identifier + '\'' +
                ", carPark=" + carPark +
                ", spots=" + spots +
                '}';
    }
}
