package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.PaymentListResponse;
import com.upgrad.FoodOrderingApp.api.model.PaymentResponse;
import com.upgrad.FoodOrderingApp.service.businness.PaymentBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/")
public class PaymentController {

    @Autowired
    PaymentBusinessService paymentBusinessService;
    @RequestMapping(method = RequestMethod.GET,path="/payment",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PaymentListResponse>getPaymentMethods()
    {
        List<PaymentEntity> paymentEntityList = paymentBusinessService.getPaymentMethods();
        PaymentListResponse paymentListResponse = new PaymentListResponse();

        for(PaymentEntity paymentEntity:paymentEntityList)
        {
            PaymentResponse paymentResponse = new PaymentResponse().id(UUID.fromString(paymentEntity.getPayment_uuid())).paymentName(paymentEntity.getPayment_name());
            paymentListResponse.addPaymentMethodsItem(paymentResponse);
        }

        return new ResponseEntity<PaymentListResponse>(paymentListResponse,HttpStatus.OK);
    }
}
