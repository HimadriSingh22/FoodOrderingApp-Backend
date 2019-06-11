package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CustomerBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
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

    @RequestMapping(method = RequestMethod.POST,path="/customer/login",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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

    @RequestMapping(method=RequestMethod.POST,path="/customer/logout",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LogoutResponse> logout(@RequestHeader("authorization")final String authorization)throws AuthorizationFailedException
    {
        CustomerAuthEntity customerAuthEntity = customerBusinessService.logout(authorization);
        CustomerEntity customer = customerAuthEntity.getCustomer();
        LogoutResponse logoutResponse = new LogoutResponse().id(customer.getUuid()).message("LOGGED OUT SUCCESSFULLY");
        return new ResponseEntity<LogoutResponse>(logoutResponse,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT,path="/customer",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdateCustomerResponse> update(@RequestHeader("authorization")final String authorization,final UpdateCustomerRequest updateCustomerRequest)throws AuthorizationFailedException , UpdateCustomerException
    {
        CustomerEntity updateCustomer =new CustomerEntity();
        updateCustomer.setFirstname(updateCustomerRequest.getFirstName());
        updateCustomer.setLastname(updateCustomerRequest.getLastName());
         final CustomerEntity updatedCustomerEntity = customerBusinessService.update(authorization,updateCustomer);
        UpdateCustomerResponse updateCustomerResponse=new UpdateCustomerResponse().id(updatedCustomerEntity.getUuid()).firstName(updatedCustomerEntity.getFirstname())
                .lastName(updatedCustomerEntity.getLastname()).status("CUSTOMER DETAILS UPDATED SUCCESSFULLY");
        return new ResponseEntity<UpdateCustomerResponse>(updateCustomerResponse,HttpStatus.OK);

    }
}
