package sk.stuba.fei.uim.vsa.pr2.domain;

import javax.persistence.*;
import java.io.Serializable;

@NamedQueries({
//        @NamedQuery(name = "Reservation.findAllByParkingSpot", query = "SELECT r FROM Reservation r WHERE r.parkingSpot.id = :pId")
        })
@Entity
@Table(name="DISCOUNT_COUPON")
public class DiscountCoupon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private int percentageDiscount;

    @ManyToOne
    private Customer holder;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPercentageDiscount() {
        return percentageDiscount;
    }

    public void setPercentageDiscount(int percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }

    public Customer getHolder() {
        return holder;
    }

    public void setHolder(Customer holder) {
        this.holder = holder;
    }

    public DiscountCoupon() {
    }

    public DiscountCoupon(String name, int percentageDiscount) {
        this.name = name;
        this.percentageDiscount = percentageDiscount;
    }

    @Override
    public String toString() {
        return "DiscountCoupon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", percentageDiscount=" + percentageDiscount +
                '}';
    }
}
