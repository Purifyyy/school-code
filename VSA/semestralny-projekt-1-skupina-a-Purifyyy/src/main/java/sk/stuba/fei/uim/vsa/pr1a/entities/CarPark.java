package sk.stuba.fei.uim.vsa.pr1a.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@NamedQueries({
        @NamedQuery(name = "CarPark.findAll", query = "SELECT cp FROM CarPark cp"),
        @NamedQuery(name = "CarPark.findByName", query = "SELECT cp FROM CarPark cp WHERE cp.name = :name")})
@Entity
@Table(name="CAR_PARK")
public class CarPark implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true, nullable = false)
    private String name;

    private String address;

    @Column(nullable = false)
    private int pricePerHour;

    @OneToMany(cascade=CascadeType.REMOVE, mappedBy= "carParkLocation", fetch = FetchType.EAGER)
    private List<CarParkFloor> floors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(int pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public List<CarParkFloor> getFloors() {
        return floors;
    }

    public void setFloors(List<CarParkFloor> floors) {
        this.floors = floors;
    }

    @Override
    public String toString() {
        return "CarPark{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", pricePerHour=" + pricePerHour +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarPark carPark = (CarPark) o;
        return pricePerHour == carPark.pricePerHour && Objects.equals(id, carPark.id) && Objects.equals(name, carPark.name) && Objects.equals(address, carPark.address) && Objects.equals(floors, carPark.floors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, pricePerHour, floors);
    }
}