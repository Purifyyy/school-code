package sk.stuba.fei.uim.vsa.pr2.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(name = "Car.findAll", query = "SELECT c FROM Car c"),
        @NamedQuery(name = "Car.findByRegistrationNumber", query = "SELECT c FROM Car c WHERE c.registrationNumber = :plate")})
@Entity
@Table(name="CAR")
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true, nullable = false)
    private String registrationNumber;

    private String brand;

    private String model;

    private String color;

    @ManyToOne
    private Customer owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String ecv) {
        this.registrationNumber = ecv;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public Car() {
    }

    public Car(String registrationNumber, String brand, String model, String color) {
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
        this.color = color;
    }

    public Car(String registrationNumber, String brand, String model, String color, Customer owner) {
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", ownerID=" + owner.getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id) && Objects.equals(registrationNumber, car.registrationNumber) && Objects.equals(brand, car.brand) && Objects.equals(model, car.model) && Objects.equals(color, car.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registrationNumber, brand, model, color, owner);
    }
}
