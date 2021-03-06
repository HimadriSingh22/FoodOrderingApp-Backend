package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "restaurant",schema = "restaurantdb")
@NamedQueries({@NamedQuery(name = "getAllRestaurants",query = "Select rt from RestaurantEntity rt order by rt.customer_rating DESC "),
@NamedQuery(name = "getrestaurantByName",query="select rt from RestaurantEntity rt where rt.restaurant_name LIKE '%:restaurant_name%'"),
@NamedQuery(name = "getRestaurantById",query = "select rt from RestaurantEntity rt where rt.restaurant_id = :restaurant_id"),
@NamedQuery(name = "getRestaurantByUuid",query="select rt from RestaurantEntity rt where rt.restaurant_uuid= :restaurant_uuid")})
public class RestaurantEntity implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer restaurant_id;

    @Column(name = "uuid")
    @NotNull
    private String restaurant_uuid;

    @Column(name = "restaurant_name")
    @NotNull
    private String restaurant_name;

    @Column(name = "photo_url")
    @NotNull
    private String restaurant_photo_url;

    @Column(name = "customer_rating")
    @NotNull
    private Double customer_rating;

    @Column(name="average_price_for_two")
    @NotNull
    private Integer avg_price_for_two;

    @Column(name = "number_of_customers_rated")
    @NotNull
    private Integer number_of_customers_rated;

    @JoinColumn(name = "address_id")
    @NotNull
    private AddressEntity restaurant_address_id;

    public RestaurantEntity(@NotNull String restaurant_uuid, @NotNull String restaurant_name, @NotNull String restaurant_photo_url, @NotNull Double customer_rating, @NotNull Integer avg_price_for_two, @NotNull Integer number_of_customers_rated, @NotNull AddressEntity restaurant_address_id) {
        this.restaurant_uuid = restaurant_uuid;
        this.restaurant_name = restaurant_name;
        this.restaurant_photo_url = restaurant_photo_url;
        this.customer_rating = customer_rating;
        this.avg_price_for_two = avg_price_for_two;
        this.number_of_customers_rated = number_of_customers_rated;
        this.restaurant_address_id = restaurant_address_id;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getRestaurant_uuid() {
        return restaurant_uuid;
    }

    public void setRestaurant_uuid(String restaurant_uuid) {
        this.restaurant_uuid = restaurant_uuid;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_photo_url() {
        return restaurant_photo_url;
    }

    public void setRestaurant_photo_url(String restaurant_photo_url) {
        this.restaurant_photo_url = restaurant_photo_url;
    }

    public Double getCustomer_rating() {
        return customer_rating;
    }

    public void setCustomer_rating(Double customer_rating) {
        this.customer_rating = customer_rating;
    }

    public Integer getAvg_price_for_two() {
        return avg_price_for_two;
    }

    public void setAvg_price_for_two(Integer avg_price_for_two) {
        this.avg_price_for_two = avg_price_for_two;
    }

    public Integer getNumber_of_customers_rated() {
        return number_of_customers_rated;
    }

    public void setNumber_of_customers_rated(Integer number_of_customers_rated) {
        this.number_of_customers_rated = number_of_customers_rated;
    }

    public AddressEntity getRestaurant_address_id() {
        return restaurant_address_id;
    }

    public void setRestaurant_address_id(AddressEntity restaurant_address_id) {
        this.restaurant_address_id = restaurant_address_id;
    }


}
