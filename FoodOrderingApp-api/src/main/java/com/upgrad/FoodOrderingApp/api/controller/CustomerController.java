package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.LoginResponse;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerRequest;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerResponse;
import com.upgrad.FoodOrderingApp.service.businness.CustomerBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class CustomerController {

    @Autowired
    CustomerBusinessService customerBusinessService;
    @RequestMapping(method = RequestMethod.POST,path="/customer/signup",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupCustomerResponse> signup(final SignupCustomerRequest signupCustomerRequest) throws SignUpRestrictedException {
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

    @RequestMapping(method = RequestMethod.POST,path="/customer/login",consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestHeader("authorization") final String authorization) throws AuthenticationFailedException {
        byte[] decode =Base64.getDecoder().decode(authorization.split("Basic")[1]);
        String decodedText = new String(decode);
        String[] decodedArray = decodedText.split(":");
        CustomerAuthEntity customerAuthEntity = customerBusinessService.authenticate(decodedArray[0],decodedArray[1]);
        CustomerEntity customer = customerAuthEntity.getCustomer();
        LoginResponse loginResponse = new LoginResponse().id(customer.getUuid()).contactNumber(customer.getContact_no())
                .firstName(customer.getFirstname()).lastName(customer.getLastname()).emailAddress(customer.getEmail()).message("LOGGED IN SUCCESSFULLY");

        HttpHeaders headers = new HttpHeaders();
        headers.add("access-token",customerAuthEntity.getAccessToken());
        return new ResponseEntity<LoginResponse>(loginResponse,headers,HttpStatus.OK);
    }
}
