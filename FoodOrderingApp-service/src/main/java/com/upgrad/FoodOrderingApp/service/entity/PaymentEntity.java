package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "payment",schema = "restaurantdb")

public class PaymentEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="uuid")
    @NotNull
    private String payment_uuid;

    @Column(name = "payment_name")
    @NotNull
    private String payment_name;

    public PaymentEntity(@NotNull String payment_uuid, @NotNull String payment_name) {
        this.payment_uuid = payment_uuid;
        this.payment_name = payment_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPayment_uuid() {
        return payment_uuid;
    }

    public void setPayment_uuid(String payment_uuid) {
        this.payment_uuid = payment_uuid;
    }

    public String getPayment_name() {
        return payment_name;
    }

    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name;
    }
}
