package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

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

    public void updateCustomer(final CustomerEntity updatedCustomerEntity)
    {
        entityManager.merge(updatedCustomerEntity);
    }
}
