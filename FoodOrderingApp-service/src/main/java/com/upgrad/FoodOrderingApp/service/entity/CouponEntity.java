package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "coupon",schema = "restaurantdb")
@NamedQueries({
        @NamedQuery(name = "getCouponDetailsByName",query="select co from CouponEntity co where co.coupon_name = :coupon_name")
})
public class CouponEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer coupon_id;

    @Column(name = "uuid")
    @NotNull
    @Size(max = 200)
    private String coupon_uuid;

    @Column(name = "coupon_name")
    @NotNull
    @Size(max = 255)
    private String coupon_name;

    @Column(name="percent")
    @NotNull
    private Integer percent;

    public Integer getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(Integer coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCoupon_uuid() {
        return coupon_uuid;
    }

    public void setCoupon_uuid(String coupon_uuid) {
        this.coupon_uuid = coupon_uuid;
    }

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }
}
