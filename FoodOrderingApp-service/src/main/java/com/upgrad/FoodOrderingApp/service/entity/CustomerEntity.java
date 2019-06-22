package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name="users",schema="quora")
@NamedQueries(value = {
        @NamedQuery(name = "customerByContact", query = "SELECT ct from CustomerEntity ct where ct.contact_number = :contact_number")

})
public class CustomerEntity implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="uuid")
    @NotNull
    private String uuid;

    @Column(name="firstname")
    @NotNull
    @Size(max=200)
    private String firstname;

    @Column(name="lastname")


    @Size(max=200)
    private String lastname;

    @Column(name="email")
    @NotNull
    private String email;

    @Column(name="password")
    @NotNull
    private String password;

    @Column(name="salt")
    @NotNull
    private String salt;

    @Column(name="contact_number")
    @NotNull
    private String contact_number;

    public CustomerEntity(){}
    public CustomerEntity(@NotNull String uuid, @NotNull @Size(max = 200) String firstname, @Size(max = 200) String lastname, @NotNull String email, @NotNull String password, @NotNull String salt, @NotNull String contact_number) {
        this.uuid = uuid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.contact_number = contact_number;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }


    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_no) {
        this.contact_number = contact_number;
    }



}
