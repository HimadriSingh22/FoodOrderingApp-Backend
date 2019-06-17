package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.InvalidRatingException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class RestaurantBusinessService {

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private CustomerDao customerDao;

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

    //fetching restaurant details using restaurant_uuid
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

    //fetching all the categories offerd by a particular restaurant
    @Transactional(propagation = Propagation.REQUIRED)
    public List getCategoriesByRestaurantId(String restaurant_uuid)
    {
        return restaurantDao.getCategoroiesByRestaurantId(restaurant_uuid);
    }

    //fetching Item List for a particular restaurant
    @Transactional(propagation = Propagation.REQUIRED)
    public List getAllItems(String restaurant_uuid)
    {
        return restaurantDao.getAllItems(restaurant_uuid);
    }

    //fetching category names
    @Transactional(propagation = Propagation.REQUIRED)
    public List getCategoriesByCategoryId(String category_uuid)
    {
        return restaurantDao.getCategoryNameById(category_uuid);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RestaurantEntity updateRestaurantRating(String accessToken,String restaurant_uuid,Double customer_rating)throws AuthorizationFailedException,RestaurantNotFoundException,InvalidRatingException
    {
        CustomerAuthEntity customerAuthEntity = customerDao.getCustomerAuthToken(accessToken);

        if(customerAuthEntity==null)
        {
            throw new AuthorizationFailedException("ATHR-001","Customer is not logged in!");
        }

        final ZonedDateTime now = ZonedDateTime.now();
        if(customerAuthEntity.getLogoutAt().isBefore(now))
        {
            throw new AuthorizationFailedException("ATHR-002","Customer is logged out ! Login again to access this endpoint!");

        }
        if(customerAuthEntity.getExpiresAt().isBefore(now))
        {
            throw new AuthorizationFailedException("ATHR-003","Your session is expired.Log in again to access this endpoint!");
        }
        if(restaurant_uuid==null)
        {
            throw new RestaurantNotFoundException("RNF-002","Restaurant id field should not be empty");
        }

        RestaurantEntity restaurantEntity = restaurantDao.getRestaurantByUuid(restaurant_uuid);

        if(restaurantEntity==null)
        {
            throw new RestaurantNotFoundException("RNF-001","No restaurant by this id");
        }
        if(customer_rating==null||customer_rating>5.0||customer_rating<1.0)
        {
            throw new InvalidRatingException("IRE-001","Restaurant should be in the range of 1 to 5");
        }

        Double newAverageRating = Math.round(customer_rating/(restaurantEntity.getNumber_of_customers_rated()+1) + restaurantEntity.getCustomer_rating()*100.0)/100.0;
        restaurantEntity.setCustomer_rating(newAverageRating);
        restaurantEntity.setNumber_of_customers_rated(restaurantEntity.getNumber_of_customers_rated()+1);
         return restaurantDao.updateRestaurantRating(restaurantEntity);

    }

}
