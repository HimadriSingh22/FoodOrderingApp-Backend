package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "category_item",schema = "restaurantdb")
@NamedQueries({
        @NamedQuery(name = "getCategoryItemList",query = "select ct from CategoryItemEntity ct where ct.category_id = :category_id")
})
public class CategoryItemEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "item_id")
    @NotNull
    private ItemEntity item_id;

    @JoinColumn(name = "category_id")
    @NotNull
    private CategoryEntity category_id;

    public CategoryItemEntity(@NotNull ItemEntity item_id, @NotNull CategoryEntity category_id) {
        this.item_id = item_id;
        this.category_id = category_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemEntity getItem_id() {
        return item_id;
    }

    public void setItem_id(ItemEntity item_id) {
        this.item_id = item_id;
    }

    public CategoryEntity getCategory_id() {
        return category_id;
    }

    public void setCategory_id(CategoryEntity category_id) {
        this.category_id = category_id;
    }
}

