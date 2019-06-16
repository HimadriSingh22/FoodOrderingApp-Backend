package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantBusinessService {

    @Autowired
    private RestaurantDao restaurantDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public List showRestaurantList()
    {
        List<RestaurantEntity> restaurantEntityList = restaurantDao.showRestaurantList();
        return restaurantEntityList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List getRestaurantByName(String restaurant_name)throws RestaurantNotFoundException
    {
        if(restaurant_name==null)
        {
            throw new RestaurantNotFoundException("RNF-003","Restaurant name field should not be empty");
        }
        List listOfrestaurants = restaurantDao.getRestaurantByName(restaurant_name);
        if(listOfrestaurants==null)
        {
            return null;
        }
        return listOfrestaurants;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List getAllCategories()
    {
        return restaurantDao.getAllCategories();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List getRestaurantByCategoryId(String category_uuid)throws CategoryNotFoundException
    {
        if(category_uuid==null)
        {
            throw new CategoryNotFoundException("CNF-001","Category id field should not be empty");
        }

        CategoryEntity categoryEntity = restaurantDao.checkCategoryId(category_uuid);
        if(categoryEntity==null)
        {
            throw new CategoryNotFoundException("CNF-002","No category by this id");
        }
        List listOfRestaurantsByCategory = restaurantDao.getRestaurantsByCategoryId(category_uuid);

        if(listOfRestaurantsByCategory==null)
        {
            return null;
        }
        return listOfRestaurantsByCategory;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RestaurantEntity getRestaurantByUuid(String restaurant_uuid )throws RestaurantNotFoundException
    {
        if(restaurant_uuid==null)
        {
            throw new RestaurantNotFoundException("RNF-002","Restaurant id field should not be empty");
        }

        RestaurantEntity restaurantEntity = restaurantDao.getRestaurantByUuid(restaurant_uuid);
        if(restaurantEntity==null)
        {
            throw new RestaurantNotFoundException("RNF-001","No restaurant by this id");
        }

        return restaurantEntity;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List getCategoriesByRestaurantId(String restaurant_uuid)
    {
        return restaurantDao.getCategoroiesByRestaurantId(restaurant_uuid);
    }

}
