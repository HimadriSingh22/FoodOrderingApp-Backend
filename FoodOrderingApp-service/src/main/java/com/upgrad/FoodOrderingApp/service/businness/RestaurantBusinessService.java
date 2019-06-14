package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
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
}
