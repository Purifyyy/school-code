package sk.stuba.fei.uim.vsa.pr2.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NamedQueries({
        @NamedQuery(name = "Reservation.findAllByParkingSpot", query = "SELECT r FROM Reservation r WHERE r.parkingSpot.id = :pId")})
@Entity
@Table(name="RESERVATION")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date startedAt;

    private Date endedAt;

    private Double price;

    @OneToOne
    @JoinColumn
    private ParkingSpot parkingSpot;

    @OneToOne
    @JoinColumn
    private Car car;

    @OneToOne
    @JoinColumn
    private DiscountCoupon coupon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Date endedAt) {
        this.endedAt = endedAt;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public DiscountCoupon getCoupon() {
        return coupon;
    }

    public void setCoupon(DiscountCoupon coupon) {
        this.coupon = coupon;
    }

    public Reservation() {
    }

    public Reservation(Date startedAt, ParkingSpot parkingSpot, Car car) {
        this.startedAt = startedAt;
        this.parkingSpot = parkingSpot;
        this.car = car;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", startedAt=" + startedAt +
                ", parkingSpotID=" + parkingSpot.getId() +
                ", carID=" + car.getId() +
                '}';
    }
}
