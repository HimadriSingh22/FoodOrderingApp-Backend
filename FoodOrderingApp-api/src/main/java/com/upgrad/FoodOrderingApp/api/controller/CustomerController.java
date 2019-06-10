package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.SignupCustomerRequest;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerResponse;
import com.upgrad.FoodOrderingApp.service.businness.CustomerBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/")
public class CustomerController {

    @Autowired
    CustomerBusinessService customerBusinessService;
    @RequestMapping(method = RequestMethod.POST,path="/customer/signup",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupCustomerResponse> signup(final SignupCustomerRequest signupCustomerRequest){
       final CustomerEntity customerEntity=new CustomerEntity();
       customerEntity.setFirstname(signupCustomerRequest.getFirstName());
       customerEntity.setLastname(signupCustomerRequest.getLastName());
       customerEntity.setEmail(signupCustomerRequest.getEmailAddress());
       customerEntity.setPassword(signupCustomerRequest.getPassword());
       customerEntity.setUuid(UUID.randomUUID().toString());
       final CustomerEntity createdCustomerEntity = customerBusinessService.signup(customerEntity);
       SignupCustomerResponse customerResponse = new SignupCustomerResponse().id(createdCustomerEntity.getUuid()).status("CUSTOMER SUCCESSFULLY REGISTERED");
       return new ResponseEntity<SignupCustomerResponse>(customerResponse,HttpStatus.CREATED);
    }
}
