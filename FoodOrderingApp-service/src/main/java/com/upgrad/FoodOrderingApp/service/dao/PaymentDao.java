package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class PaymentDao {
    @PersistenceContext
    private EntityManager entityManager;

    public PaymentEntity getPaymentByUUID(String payment_uuid)
    {
        try{
            return entityManager.createNamedQuery("getPaymentByUUID",PaymentEntity.class).setParameter("payment_uuid",payment_uuid).getSingleResult();
        }catch (NoResultException nre)
        {
            return null;
        }
    }
}
