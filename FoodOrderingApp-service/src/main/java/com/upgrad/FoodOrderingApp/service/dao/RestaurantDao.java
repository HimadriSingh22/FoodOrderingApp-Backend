package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RestaurantDao {
    @PersistenceContext
    private EntityManager entityManager;
    public List<RestaurantEntity> showRestaurantList()
    {
      List<RestaurantEntity> resultList =  entityManager.createNamedQuery("getAllRestaurants",RestaurantEntity.class).getResultList();
      return resultList;
    }
}
