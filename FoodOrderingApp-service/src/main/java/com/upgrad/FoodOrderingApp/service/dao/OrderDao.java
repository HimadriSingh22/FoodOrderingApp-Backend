package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;


@Repository

public class OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

 public List<ItemEntity> getOrdersByRestaurant(RestaurantEntity restaurantEntity)
 {
     try{

         List<OrderEntity> orderEntityList = entityManager.createNamedQuery("getOrderIdByRestaurantId",OrderEntity.class).setParameter("restaurant_id",restaurantEntity.getRestaurant_id()).getResultList();

         List<OrderItemEntity> orderItemEntityList = new ArrayList<>();


     for(int i=0;i<orderEntityList.size();i++)
     {
       OrderItemEntity orderItemEntity =   entityManager.createNamedQuery("getOrderItemByOrderId",OrderItemEntity.class).setParameter("order_id",orderEntityList.get(i).getOrder_id()).getSingleResult();
        orderItemEntityList.add(orderItemEntity);
     }

     List<ItemEntity> itemEntityList = new ArrayList<>();

     for(int j=0;j<orderItemEntityList.size();j++)
     {
         ItemEntity itemEntity = entityManager.createNamedQuery("getItemDetailsByItemId",ItemEntity.class).setParameter("item_id",orderItemEntityList.get(j).getItem_id()).getSingleResult();
         itemEntityList.add(itemEntity);
     }


     return itemEntityList;
     }
     catch (NoResultException nre)
     {
         return null;
     }
 }

public List<OrderEntity> getCustomerOrders(CustomerAuthEntity customerAuthEntity)
{
    try {
        return entityManager.createNamedQuery("getOrdersByCustomerId", OrderEntity.class).setParameter("customer_id", customerAuthEntity.getCustomer_id()).getResultList();
    }catch (NoResultException nre)
    {
        return  null;
    }
}
}

