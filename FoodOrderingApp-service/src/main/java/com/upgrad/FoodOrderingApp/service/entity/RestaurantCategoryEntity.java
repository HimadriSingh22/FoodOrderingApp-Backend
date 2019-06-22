package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="restaurant_category",schema="restaurantdb")
@NamedQueries({
@NamedQuery(name = "getAllCategoryIdByRestaurantId" , query = "select rc from RestaurantCategoryEntity rc where rc.restaurant_id= :restaurant_id")
})
public class RestaurantCategoryEntity implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name="restaurant_id")
    @NotNull
    private RestaurantEntity restaurant_id;

    @JoinColumn(name="category_id")
    @NotNull
    private CategoryEntity category_id;

    public RestaurantCategoryEntity(@NotNull RestaurantEntity restaurant_id, @NotNull CategoryEntity category_id) {
        this.restaurant_id = restaurant_id;
        this.category_id = category_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RestaurantEntity getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(RestaurantEntity restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public CategoryEntity getCategory_id() {
        return category_id;
    }

    public void setCategory_id(CategoryEntity category_id) {
        this.category_id = category_id;
    }




}
