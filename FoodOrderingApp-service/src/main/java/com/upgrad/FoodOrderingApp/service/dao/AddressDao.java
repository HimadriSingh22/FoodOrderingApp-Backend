package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

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

    public List<AddressEntity>getAllAddress(final String customer_id)
    {
        List<CustomerAddressEntity> result = entityManager.createNamedQuery("getAddressByCustomerId",CustomerAddressEntity.class).setParameter("customer_id",customer_id).getResultList();
        List<AddressEntity> resultList = entityManager.createNamedQuery("getAllAddressByAddressId",AddressEntity.class).setParameter("address_id",result).getResultList();
        return resultList;
    }

    public CustomerAddressEntity matchAddressId(String address_uuid)throws AddressNotFoundException
    {
        try{
            AddressEntity addressEntity =entityManager.createNamedQuery("getIdbyAddressUuid",AddressEntity.class).setParameter("address_uuid",address_uuid).getSingleResult();
           if(addressEntity.getId()==null)
           {
               throw new AddressNotFoundException("ANF-003","No address by this id");
           }

           else{

            return entityManager.createNamedQuery("selectCustomerByAddressId",CustomerAddressEntity.class).setParameter("address_id",addressEntity.getId()).getSingleResult();
           }
        }catch (NoResultException no)
        {
            return null;
        }
    }

    public AddressEntity deleteAddress(final String address_uuid)
    {
        try{
           return entityManager.createNamedQuery("deleteAddressById",AddressEntity.class).setParameter("address_uuid",address_uuid).getSingleResult();
        }
        catch (NoResultException no)
        {
            return null;
        }
    }

    public List<StateEntity> getAllStates()
    {
        List<StateEntity> resultList = entityManager.createNamedQuery("getAllStates",StateEntity.class).getResultList();
        return resultList;
    }

    public AddressEntity getAddressByUUID(String address_uuid)
    {
        try{
            return entityManager.createNamedQuery("getAddressByUUID",AddressEntity.class).setParameter("address_uuid",address_uuid).getSingleResult();
        }catch (NoResultException nre)
        {
            return null;
        }
    }

    public CustomerAddressEntity matchCustomer(AddressEntity addressEntity)
    {
        try{
        return entityManager.createNamedQuery("getCustomerByAddressId",CustomerAddressEntity.class).setParameter("address_id",addressEntity.getId()).getSingleResult();}
        catch (NoResultException nre)
        {
            return null;
        }
    }
}
