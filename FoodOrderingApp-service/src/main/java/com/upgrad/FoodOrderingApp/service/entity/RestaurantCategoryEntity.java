package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="restaurant_category",schema="restaurantdb")
public class RestaurantCategoryEntity implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="restaurant_id")
    @NotNull
    private Integer restaurant_id;

    @Column(name="category_id")
    @NotNull
    private Integer category_id;
}
