package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "restaurant",schema = "restaurantdb")
@NamedQueries({@NamedQuery(name = "getAllRestaurants",query = "Select rt from RestaurantEntity rt order by rt.customer_rating DESC ")})
public class RestaurantEntity {
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
    private Number customer_rating;

    @Column(name="average_price_for_two")
    @NotNull
    private Integer avg_price_for_two;

    @Column(name = "number_of_customers_rated")
    @NotNull
    private Integer number_of_customers_rated;

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

    public Number getCustomer_rating() {
        return customer_rating;
    }

    public void setCustomer_rating(Number customer_rating) {
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

    public String getRestaurant_address_id() {
        return restaurant_address_id;
    }

    public void setRestaurant_address_id(String restaurant_address_id) {
        this.restaurant_address_id = restaurant_address_id;
    }

    @JoinColumn(name = "address_id")
    @NotNull

    private String restaurant_address_id;
}
