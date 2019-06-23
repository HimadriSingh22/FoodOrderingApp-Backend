package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="address", schema="restaurantdb")
@NamedQueries({
@NamedQuery(name = "getAllAddressByAddressId",query="select ad from AddressEntity ad where ad.id= :address_id"),
        @NamedQuery(name = "deleteAddressById",query = "delete from AddressEntity ad where ad.uuid =:address_uuid"),
        @NamedQuery(name = "getIdByAddressUuid",query = "select ad from AddressEntity ad  where ad.uuid= :address_uuid"),
        @NamedQuery(name = "getAddressByUUID",query = "select ad from AddressEntity ad where ad.uuid= :address_uuid")
})

public class AddressEntity implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="uuid")
    @NotNull
    private String uuid;

    @Column(name="flat_buil_number")
    @NotNull
    private String flat_buil_number;

    @Column(name="locality")
    @NotNull
    private String locality;

    @Column(name="city")
    @NotNull
    private String city;

    @Column(name="pincode")
    @NotNull
    private String pincode;

    @JoinColumn(name="state_id")
    @NotNull
    private StateEntity state_id;

    @Column(name="active")
    @NotNull
    private Integer active;


    public AddressEntity(){}
    public AddressEntity( String uuid,  String flat_buil_number,  String locality,  String city,  String pincode, StateEntity state_id,  String active) {
        this.uuid = uuid;
        this.flat_buil_number = flat_buil_number;
        this.locality = locality;
        this.city = city;
        this.pincode = pincode;
        this.state_id = state_id;
        this.active = 1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFlat_buil_number() {
        return flat_buil_number;
    }

    public void setFlat_buil_number(String flat_buil_number) {
        this.flat_buil_number = flat_buil_number;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public StateEntity getState_id() {
        return state_id;
    }

    public void setState_id(StateEntity state_id) {
        this.state_id = state_id;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}

