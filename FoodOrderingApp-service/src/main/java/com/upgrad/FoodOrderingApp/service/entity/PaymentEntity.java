package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "payment",schema = "restaurantdb")

public class PaymentEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer payment_id;

    @Column(name="uuid")
    @NotNull
    private String payment_uuid;

    @Column(name = "payment_name")
    @NotNull
    private String payment_name;

    public String getPayment_name() {
        return payment_name;
    }

    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name;
    }

    public Integer getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(Integer payment_id) {
        this.payment_id = payment_id;
    }

    public String getPayment_uuid() {
        return payment_uuid;
    }

    public void setPayment_uuid(String payment_uuid) {
        this.payment_uuid = payment_uuid;
    }
}
