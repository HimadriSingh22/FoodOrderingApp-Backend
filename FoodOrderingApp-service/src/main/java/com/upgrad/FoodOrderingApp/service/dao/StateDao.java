package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.StateEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class StateDao {
    @PersistenceContext
    private EntityManager entityManager;

    public StateEntity getStateByUUID(String state_uuid)
    {
        try{
            return entityManager.createNamedQuery("getStateByUuid",StateEntity.class)
                    .setParameter("state_uuid",state_uuid).getSingleResult();
        }catch (NoResultException nre)
        {
            return null;
        }
    }
}
