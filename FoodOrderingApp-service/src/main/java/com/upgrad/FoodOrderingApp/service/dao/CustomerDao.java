package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerDao {
    @PersistenceContext
    private EntityManager entityManager;

    public CustomerEntity createCustomer(CustomerEntity customerEntity)
    {
        entityManager.persist(customerEntity);
        return customerEntity;
    }

    public CustomerEntity getCustomerByContact( final String contact_number)
    {
        try{
            return entityManager.createNamedQuery("customerByContact",CustomerEntity.class).setParameter("contact_number",contact_number).getSingleResult();
        } catch(NoResultException noResultException){
            return null;
        }
    }

    public CustomerAuthEntity createAuthToken(final CustomerAuthEntity customerAuthEntity)
    {
        entityManager.persist(customerAuthEntity);
        return customerAuthEntity;
    }

    public CustomerAuthEntity getCustomerAuthToken(final String accessToken)
    {
        try{
          return entityManager.createNamedQuery("customerAuthTokenByAccessToken",CustomerAuthEntity.class).setParameter("accessToken",accessToken).getSingleResult();
        }catch(NoResultException n)
        {
            return null;
        }
    }

    public void updateCustomer(final CustomerEntity updatedCustomerEntity)
    {
        entityManager.merge(updatedCustomerEntity);
    }

    public void updateCustomerAuthEntity(final CustomerAuthEntity updatedCustomerAuthEntity)
    {
        entityManager.merge(updatedCustomerAuthEntity);
    }
    public CustomerEntity updateCustomerDetails(final CustomerEntity updateCustomer)
    {
       return entityManager.merge(updateCustomer);

    }
    public CustomerEntity updatePassword(final CustomerEntity newPassword)
    {
        return entityManager.merge(newPassword);
    }
}
