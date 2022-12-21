package sk.stuba.fei.uim.vsa.pr2.web.discountcoupon;

import sk.stuba.fei.uim.vsa.pr2.domain.DiscountCoupon;

public class DiscountCouponResponse {

    private Long id;
    private String name;
    private Integer discount;

    public DiscountCouponResponse(DiscountCoupon dc) {
        this.id = dc.getId();
        this.name = dc.getName();
        this.discount = dc.getPercentageDiscount();
    }

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

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
