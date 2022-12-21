package sk.stuba.fei.uim.vsa.pr2.web.carpark;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import sk.stuba.fei.uim.vsa.pr2.domain.CarPark;
import sk.stuba.fei.uim.vsa.pr2.domain.CarParkFloor;
import sk.stuba.fei.uim.vsa.pr2.web.carparkfloor.CarParkFloorDto;
import sk.stuba.fei.uim.vsa.pr2.web.carparkfloor.CarParkFloorResponse;

import java.util.ArrayList;
import java.util.List;

public class CarParkResponse {

    private Long id;
    private String name;
    private String address;
    private int prices;
    private List<CarParkFloorResponse> floors;

    public CarParkResponse(CarPark cp) {
        this.id = cp.getId();
        this.name = cp.getName();
        this.address = cp.getAddress();
        this.prices = cp.getPricePerHour();
        this.floors = new ArrayList<>();
        for(CarParkFloor cpf : cp.getFloors()) {
            this.floors.add(new CarParkFloorResponse(cpf));
        }
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CarParkResponse() {}

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPrices() {
        return prices;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrices(int prices) {
        this.prices = prices;
    }

    public List<CarParkFloorResponse> getFloors() {
        return floors;
    }

    public void setFloors(List<CarParkFloorResponse> floors) {
        this.floors = floors;
    }
}
