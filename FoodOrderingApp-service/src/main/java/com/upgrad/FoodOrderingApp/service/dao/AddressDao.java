package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository

public class AddressDao {
    @PersistenceContext
    private EntityManager entityManager;

    public AddressEntity saveAddress(final AddressEntity addressEntity)
    {
        entityManager.persist(addressEntity);
        return addressEntity;
    }

    public StateEntity checkState(final String state_uuid)
    {
        try{
       return entityManager.createNamedQuery("getStateByUuid",StateEntity.class).setParameter("state_uuid",state_uuid).getSingleResult();}
       catch (NoResultException no)
       {
           return null;
       }

    }

    public AddressEntity getAllAddress(final String customer_id)
    {
        CustomerAddressEntity customerAddressEntity= entityManager.createNamedQuery("getAddressByCustomerId",CustomerAddressEntity.class).setParameter("customer_id",customer_id).getSingleResult();
        AddressEntity addressEntity = entityManager.createNamedQuery("getAllAddressByAddressId",AddressEntity.class).setParameter("address_id",customerAddressEntity).getSingleResult();
        return addressEntity;
    }
}
