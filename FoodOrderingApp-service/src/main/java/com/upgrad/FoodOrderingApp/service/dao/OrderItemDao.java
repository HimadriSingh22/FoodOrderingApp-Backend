package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List getItemsByOrder(OrderEntity orderEntity)
    {
        try {
            return entityManager.createNamedQuery("getOrderItemByOrderId", OrderItemEntity.class).setParameter("order_id", orderEntity.getOrder_id()).getResultList();
        }catch (NoResultException nre)
        {
            return null;
        }
    }
}

