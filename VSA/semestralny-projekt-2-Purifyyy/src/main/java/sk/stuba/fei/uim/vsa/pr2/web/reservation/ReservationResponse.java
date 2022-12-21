package sk.stuba.fei.uim.vsa.pr2.web.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import sk.stuba.fei.uim.vsa.pr2.domain.DiscountCoupon;
import sk.stuba.fei.uim.vsa.pr2.domain.Reservation;
import sk.stuba.fei.uim.vsa.pr2.web.car.CarDto;
import sk.stuba.fei.uim.vsa.pr2.web.parkingspot.ParkingSpotDto;

import java.util.Date;

public class ReservationResponse {

    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Bratislava")
    private Date start;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Bratislava")
    private Date end;
    private Double prices;
    private Long car;
    private Long spot;
    private Long coupon;

    public ReservationResponse(Reservation res) {
        System.out.println(res);
        this.id = res.getId();
        this.start = res.getStartedAt();
        this.end = res.getEndedAt();
        this.prices = res.getPrice();
        this.car = res.getCar().getId();
        this.spot = res.getParkingSpot().getId();
        if(res.getCoupon() != null) {
            this.coupon = res.getCoupon().getId();
        } else {
            this.coupon = null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Double getPrices() {
        return prices;
    }

    public void setPrices(Double prices) {
        this.prices = prices;
    }

    public Long getCar() {
        return car;
    }

    public void setCar(Long car) {
        this.car = car;
    }

    public Long getSpot() {
        return spot;
    }

    public void setSpot(Long spot) {
        this.spot = spot;
    }

    public Long getCoupon() {
        return coupon;
    }

    public void setCoupon(Long coupon) {
        this.coupon = coupon;
    }
}
