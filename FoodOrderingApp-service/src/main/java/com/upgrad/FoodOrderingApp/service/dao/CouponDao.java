package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class CouponDao {
    @PersistenceContext
    private EntityManager entityManager;

    public CouponEntity getCouponDetailsByName(String coupon_name)
    {
        return entityManager.createNamedQuery("getCouponDetailsByName",CouponEntity.class).setParameter("coupon_name",coupon_name).getSingleResult();
    }

    public CouponEntity getCouponByUUID(String coupon_uuid)
    {
        try{
            return entityManager.createNamedQuery("getCouponByUUID",CouponEntity.class).setParameter("coupon_uuid",coupon_uuid).getSingleResult();
        }catch (NoResultException nre)
        {
            return null;
        }
    }
}
