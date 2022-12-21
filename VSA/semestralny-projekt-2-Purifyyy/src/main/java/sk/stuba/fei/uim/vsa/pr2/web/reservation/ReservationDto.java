package sk.stuba.fei.uim.vsa.pr2.web.reservation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import sk.stuba.fei.uim.vsa.pr2.domain.DiscountCoupon;
import sk.stuba.fei.uim.vsa.pr2.domain.Reservation;
import sk.stuba.fei.uim.vsa.pr2.web.car.CarDto;
import sk.stuba.fei.uim.vsa.pr2.web.discountcoupon.DiscountCouponDto;
import sk.stuba.fei.uim.vsa.pr2.web.parkingspot.ParkingSpotDto;

import java.util.Date;

public class ReservationDto {

    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Bratislava")
    private Date start;
    private Date end;
    private Double prices;
    private CarDto car;
    private ParkingSpotDto spot;
    private DiscountCouponDto coupon;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ReservationDto() {}

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

    public CarDto getCar() {
        return car;
    }

    public void setCar(CarDto car) {
        this.car = car;
    }

    public ParkingSpotDto getSpot() {
        return spot;
    }

    public void setSpot(ParkingSpotDto spot) {
        this.spot = spot;
    }

    public DiscountCouponDto getCoupon() {
        return coupon;
    }

    public void setCoupon(DiscountCouponDto coupon) {
        this.coupon = coupon;
    }
}
