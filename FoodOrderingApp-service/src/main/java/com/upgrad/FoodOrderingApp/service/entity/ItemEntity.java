package com.upgrad.FoodOrderingApp.service.entity;

import com.upgrad.FoodOrderingApp.service.common.ItemType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "item",schema="restaurantdb")
@NamedQueries({
        @NamedQuery(name = "getAllItems",query = "select it from ItemEntity it where it.item_id = :item_id"),
        @NamedQuery(name = "getItemDetailsByItemId",query = "select it from ItemEntity it where it.item_id = :item_id"),
        @NamedQuery(name="getItemByUuid",query="select it from ItemEntity it where it.item_uuid = :uuid")
})
public class ItemEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer item_id;

    @Column(name="uuid")
    @NotNull
    private String item_uuid;

    @Column(name="item_name")
    @NotNull
    private String item_name;

    @Column(name="price")
    @NotNull
    private Integer price;

    @Column(name = "type")
    @NotNull
    private ItemType type;

    public ItemEntity(@NotNull String item_uuid, @NotNull String item_name, @NotNull Integer price, @NotNull ItemType type) {
        this.item_uuid = item_uuid;
        this.item_name = item_name;
        this.price = price;
        this.type = type;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public void setItem_id(Integer item_id) {
        this.item_id = item_id;
    }

    public String getItem_uuid() {
        return item_uuid;
    }

    public void setItem_uuid(String item_uuid) {
        this.item_uuid = item_uuid;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
}
