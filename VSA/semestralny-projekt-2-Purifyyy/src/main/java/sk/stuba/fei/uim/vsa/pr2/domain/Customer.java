package sk.stuba.fei.uim.vsa.pr2.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
        @NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email")})
@Entity
@Table(name="USER")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String surname;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="owner")
    private List<Car> cars;

    @OneToMany(cascade=CascadeType.REMOVE, mappedBy="holder")
    private List<DiscountCoupon> coupons;

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<DiscountCoupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<DiscountCoupon> coupons) {
        this.coupons = coupons;
    }

    public void addCoupon(DiscountCoupon dc) {
        this.coupons.add(dc);
    }

    public Customer(){
    }

    public Customer(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(surname, customer.surname) && Objects.equals(email, customer.email) && Objects.equals(cars, customer.cars) && Objects.equals(coupons, customer.coupons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, cars, coupons);
    }
}
