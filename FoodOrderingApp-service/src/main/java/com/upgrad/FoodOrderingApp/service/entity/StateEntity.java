package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="state",schema="restaurantdb")
@NamedQueries({@NamedQuery(name = "getStateByUuid",query="select st from StateEntity st where st.state_uuid= :state_uuid"),
@NamedQuery(name = "getAllStates",query = "select st from StateEntity st")})
public class StateEntity implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="uuid")
    @NotNull
    private String state_uuid;

    @Column(name="state_name")
    @NotNull
    private String state_name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getState_uuid() {
        return state_uuid;
    }

    public void setState_uuid(String uuid) {
        this.state_uuid = uuid;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }
}
