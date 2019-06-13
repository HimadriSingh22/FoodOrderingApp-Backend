package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="customer_address" , schema="restaurantdb")
@NamedQueries({
        @NamedQuery(name = "getAddressByCustomer_id",query="Select ad from CustomerAddressEntity ad where ad.customer_id= :customer_id")
})
public class CustomerAddressEntity implements Serializable {

 @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


 @JoinColumn(name="customer_id")
    @NotNull
    private String customer_id;

 @JoinColumn(name="address_id")
    @NotNull
    private String address_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }
}
