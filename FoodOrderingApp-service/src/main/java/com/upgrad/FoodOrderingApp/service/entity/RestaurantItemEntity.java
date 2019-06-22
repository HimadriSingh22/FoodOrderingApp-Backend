package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="restaurant_item",schema="restaurantdb")
@NamedQueries({@NamedQuery(name = "getAllItemIdByRestaurantId",query="SELECT it.item_id from RestaurantItemEntity it where it.restaurant_id = :restaurant_id")})
public class RestaurantItemEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer restaurant_item_id;

    @JoinColumn(name = "item_id")
    @NotNull
    private Integer item_id;

    @JoinColumn(name = "restaurant_id")
    @NotNull
    private RestaurantEntity restaurant_id;

    public RestaurantItemEntity(@NotNull Integer item_id, @NotNull RestaurantEntity restaurant_id) {
        this.item_id = item_id;
        this.restaurant_id = restaurant_id;
    }

    public Integer getRestaurant_item_id() {
        return restaurant_item_id;
    }

    public void setRestaurant_item_id(Integer restaurant_item_id) {
        this.restaurant_item_id = restaurant_item_id;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public RestaurantEntity getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(RestaurantEntity restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
}

