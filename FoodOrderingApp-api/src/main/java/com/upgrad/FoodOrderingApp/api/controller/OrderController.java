package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.ItemBusinessService;
import com.upgrad.FoodOrderingApp.service.businness.OrderBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/")
public class OrderController {

    @Autowired
   private OrderBusinessService orderBusinessService;
    @Autowired
   private ItemBusinessService itemBusinessService;
    @RequestMapping(method=RequestMethod.GET,path = "/order/coupon/{coupon_name}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CouponDetailsResponse> getCouponByName(@PathVariable("coupon_name")final String coupon_name,@RequestHeader("authorization")final String authorization)throws CouponNotFoundException,AuthorizationFailedException
    {
        String accessToken = authorization.split("Bearer")[1];
        CouponEntity couponEntity = orderBusinessService.getCouponByName(accessToken,coupon_name);
        CouponDetailsResponse couponDetailsResponse = new CouponDetailsResponse().id(UUID.fromString(couponEntity.getCoupon_uuid()))
                .couponName(couponEntity.getCoupon_name()).percent(couponEntity.getPercent());
        return new ResponseEntity<CouponDetailsResponse>(couponDetailsResponse,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,path="/order",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CustomerOrderResponse> getCustomerOrders(@RequestHeader("authorization") final String authorization)throws AuthorizationFailedException
    {
        String accessToken = authorization.split("Bearer")[1];
       List<OrderEntity>orderEntityList= orderBusinessService.getCustomerOrders(accessToken);

       CustomerOrderResponse customerOrderResponse = new CustomerOrderResponse();
       for(OrderEntity orderEntity: orderEntityList)
       {
           OrderListCoupon orderListCoupon = new OrderListCoupon().id(UUID.fromString(orderEntity.getCoupon_id().getCoupon_uuid()))
                   .couponName(orderEntity.getCoupon_id().getCoupon_name()).percent(orderEntity.getCoupon_id().getPercent());

           OrderListPayment orderListPayment = new OrderListPayment().id(UUID.fromString(orderEntity.getPayment_id().getPayment_uuid()))
                   .paymentName(orderEntity.getPayment_id().getPayment_name());

           OrderListCustomer orderListCustomer = new OrderListCustomer().id(UUID.fromString(orderEntity.getCustomer_id().getUuid()))
                   .firstName(orderEntity.getCustomer_id().getFirstname()).lastName(orderEntity.getCustomer_id().getLastname())
                   .emailAddress(orderEntity.getCustomer_id().getEmail()).contactNumber(orderEntity.getCustomer_id().getContact_number());

           OrderListAddressState orderListAddressState = new OrderListAddressState().id(UUID.fromString(orderEntity.getAddress_id().getState_id().getState_uuid()))
                   .stateName(orderEntity.getAddress_id().getState_id().getState_name());

           OrderListAddress orderListAddress = new OrderListAddress().id(UUID.fromString(orderEntity.getAddress_id().getUuid().toString()))
                   .flatBuildingName(orderEntity.getAddress_id().getFlat_buil_number()).locality(orderEntity.getAddress_id().getLocality())
                   .city(orderEntity.getAddress_id().getCity()).pincode(orderEntity.getAddress_id().getPincode()).state(orderListAddressState);

           OrderList orderList = new OrderList().id(UUID.fromString(orderEntity.getOrder_uuid())).bill(orderEntity.getBill()).coupon(orderListCoupon)
                   .address(orderListAddress).customer(orderListCustomer).date(orderEntity.getDate().toString()).discount(orderEntity.getDiscount()).payment(orderListPayment);


           for (OrderItemEntity orderItemEntity:itemBusinessService.getItemsByOrder(orderEntity))
           {
               ItemQuantityResponseItem itemQuantityResponseItem = new ItemQuantityResponseItem().id(UUID.fromString(orderItemEntity.getItem_id().getItem_uuid()))
                       .itemName(orderItemEntity.getItem_id().getItem_name()).itemPrice(orderItemEntity.getItem_id().getPrice())
                       .type(ItemQuantityResponseItem.TypeEnum.fromValue(orderItemEntity.getItem_id().getType().getValue()));

               ItemQuantityResponse itemQuantityResponse = new ItemQuantityResponse().item(itemQuantityResponseItem).quantity(orderItemEntity.getQuantity()).price(orderItemEntity.getPrice());

               orderList.addItemQuantitiesItem(itemQuantityResponse);
           }
           customerOrderResponse.addOrdersItem(orderList);

           }
           return new ResponseEntity<CustomerOrderResponse>(customerOrderResponse,HttpStatus.OK);

    }

}
