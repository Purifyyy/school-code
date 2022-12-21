package sk.stuba.fei.uim.vsa.pr2.web.discountcoupon;

import com.fasterxml.jackson.annotation.JsonCreator;

public class DiscountCouponDto {

    private Long id;
    private String name;
    private Integer discount;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DiscountCouponDto() {}

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

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer number) {
        this.discount = number;
    }
}
