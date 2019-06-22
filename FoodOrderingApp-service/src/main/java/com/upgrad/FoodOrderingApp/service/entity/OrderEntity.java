package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.awt.font.NumericShaper;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "orders",schema = "restaurantdb")
@NamedQueries({
        @NamedQuery(name = "getOrderIdByRestaurantId",query = "select od from OrderEntity od where od.restaurant_id= :restaurant_id"),
        @NamedQuery(name = "getOrdersByCustomerId",query = "select od from OrderEntity od where od.customer_id= :customer_id")
})
public class OrderEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer order_id;

    @Column(name="uuid")
    @NotNull
    private String order_uuid;

    @Column(name = "bill")
    @NotNull
    private BigDecimal bill;

    @JoinColumn(name = "coupon_id")
    @NotNull
    private CouponEntity coupon_id;

    @Column(name = "discount")
    @NotNull
    private BigDecimal discount;

    @Column(name = "date")
    @NotNull
    private ZonedDateTime date;

    @JoinColumn(name="payment_id")
    @NotNull
    private PaymentEntity payment_id;

    @JoinColumn(name="customer_id")
    @NotNull
    private CustomerEntity customer_id;

    @JoinColumn(name="address_id")
    @NotNull
    private AddressEntity address_id;


    @JoinColumn(name="restaurant_id")
    @NotNull
    private RestaurantEntity restaurant_id;


    public OrderEntity(@NotNull String order_uuid, @NotNull BigDecimal bill, @NotNull CouponEntity coupon_id, @NotNull BigDecimal discount, @NotNull ZonedDateTime date, @NotNull PaymentEntity payment_id, @NotNull CustomerEntity customer_id, @NotNull AddressEntity address_id, @NotNull RestaurantEntity restaurant_id) {
        this.order_uuid = order_uuid;
        this.bill = bill;
        this.coupon_id = coupon_id;
        this.discount = discount;
        this.date = date;
        this.payment_id = payment_id;
        this.customer_id = customer_id;
        this.address_id = address_id;
        this.restaurant_id = restaurant_id;
    }


    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getOrder_uuid() {
        return order_uuid;
    }

    public void setOrder_uuid(String order_uuid) {
        this.order_uuid = order_uuid;
    }

    public BigDecimal getBill() {
        return bill;
    }

    public void setBill(BigDecimal bill) {
        this.bill = bill;
    }

    public CouponEntity getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(CouponEntity coupon_id) {
        this.coupon_id = coupon_id;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public PaymentEntity getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(PaymentEntity payment_id) {
        this.payment_id = payment_id;
    }

    public CustomerEntity getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(CustomerEntity customer_id) {
        this.customer_id = customer_id;
    }

    public RestaurantEntity getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(RestaurantEntity restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public AddressEntity getAddress_id() {
        return address_id;
    }

    public void setAddress_id(AddressEntity address_id) {
        this.address_id = address_id;
    }


}
