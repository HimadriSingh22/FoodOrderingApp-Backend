package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "order_item",schema="restaurantdb")
@NamedQueries({
        @NamedQuery(name = "getOrderItemByOrderId",query="select od from OrderItemEntity od where od.order_id=:order_id")
})
public class OrderItemEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer order_item_id;

    @JoinColumn(name = "order_id")
    @NotNull
    private OrderEntity order_id;

    @JoinColumn(name = "item_id")
    @NotNull
    private ItemEntity item_id;

    @Column(name = "quantity")
    @NotNull
    private Integer quantity;

    @Column(name = "price")
    @NotNull
    private Integer price;

    public OrderItemEntity(){}
    public OrderItemEntity(@NotNull OrderEntity order_id, @NotNull ItemEntity item_id, @NotNull Integer quantity, @NotNull Integer price) {
        this.order_id = order_id;
        this.item_id = item_id;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getOrder_item_id() {
        return order_item_id;
    }

    public void setOrder_item_id(Integer order_item_id) {
        this.order_item_id = order_item_id;
    }

    public OrderEntity getOrder_id() {
        return order_id;
    }

    public void setOrder_id(OrderEntity order_id) {
        this.order_id = order_id;
    }

    public ItemEntity getItem_id() {
        return item_id;
    }

    public void setItem_id(ItemEntity item_id) {
        this.item_id = item_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }




}
