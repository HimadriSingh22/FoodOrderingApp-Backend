package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CouponDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class OrderBusinessService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CouponDao couponDao;

    public CouponEntity getCouponByName(String accessToken , String coupon_name)throws AuthorizationFailedException,CouponNotFoundException
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

        if(coupon_name==null)
        {
            throw new CouponNotFoundException("CPF-002","Coupon name field should not be empty");
        }
       CouponEntity couponEntity = couponDao.getCouponDetailsByName(coupon_name);
        if(couponEntity==null)
        {
            throw new CouponNotFoundException("CPF-001","No coupon by this name");
        }
        return couponEntity;
    }

    public List<OrderEntity> getCustomerOrders(String accessToken)throws AuthorizationFailedException
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

       List<OrderEntity> orderEntityList = orderDao.getCustomerOrders(customerAuthEntity);
        return orderEntityList;
    }
}
