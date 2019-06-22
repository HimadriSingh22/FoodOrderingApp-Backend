package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CouponDao {
    @PersistenceContext
    private EntityManager entityManager;

    public CouponEntity getCouponDetailsByName(String coupon_name)
    {
        return entityManager.createNamedQuery("getCouponDetailsByName",CouponEntity.class).setParameter("coupon_name",coupon_name).getSingleResult();
    }
}
