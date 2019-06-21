package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "category",schema = "restaurantdb")
@NamedQueries({
        @NamedQuery(name = "getAllCategories",query = "select ct from CategoryEntity ct order by ct.category_name ASC "),
        @NamedQuery(name = "getCategoryByUuid",query = "select ct from CategoryEntity ct where ct.category_uuid = :category_uuid"),
        @NamedQuery(name = "getAllCategoryDetails",query = "select ct from CategoryEntity ct where ct.id =:category_id")
})
public class CategoryEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @NotNull
    @Size(max = 200)
    private String category_uuid;

    @Column(name = "category_name")
    @NotNull
    @Size(max = 255)
    private String category_name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory_uuid() {
        return category_uuid;
    }

    public void setCategory_uuid(String category_uuid) {
        this.category_uuid = category_uuid;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }


}
