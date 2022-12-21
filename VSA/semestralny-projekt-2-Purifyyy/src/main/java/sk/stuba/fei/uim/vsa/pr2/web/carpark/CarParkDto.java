package sk.stuba.fei.uim.vsa.pr2.web.carpark;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import sk.stuba.fei.uim.vsa.pr2.domain.CarPark;
import sk.stuba.fei.uim.vsa.pr2.domain.CarParkFloor;
import sk.stuba.fei.uim.vsa.pr2.web.carparkfloor.CarParkFloorDto;

import java.util.ArrayList;
import java.util.List;

public class CarParkDto {

    private Long id;
    private String name;
    private String address;
    private int prices;
    private List<CarParkFloorDto> floors;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CarParkDto() {}

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

    public List<CarParkFloorDto> getFloors() {
        return floors;
    }

    public void setFloors(List<CarParkFloorDto> floors) {
        this.floors = floors;
    }

    @Override
    public String toString() {
        return "CarParkDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", prices=" + prices +
                ", floors=" + floors +
                '}';
    }
}
