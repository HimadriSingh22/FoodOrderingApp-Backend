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

    //Signup request
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

    //login Request
    @RequestMapping(method = RequestMethod.POST,path="/customer/login",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestHeader("authorization") final String authorization) throws AuthenticationFailedException {
        byte[] decode =Base64.getDecoder().decode(authorization.split("Basic")[1]);
        String decodedText = new String(decode);
        String[] decodedArray = decodedText.split(":");
        CustomerAuthEntity customerAuthEntity = customerBusinessService.authenticate(decodedArray[0],decodedArray[1]);
        CustomerEntity customer = customerAuthEntity.getCustomer_id();
        LoginResponse loginResponse = new LoginResponse().id(customer.getUuid()).contactNumber(customer.getContact_number())
                .firstName(customer.getFirstname()).lastName(customer.getLastname()).emailAddress(customer.getEmail()).message("LOGGED IN SUCCESSFULLY");

        HttpHeaders headers = new HttpHeaders();
        headers.add("access-token",customerAuthEntity.getAccessToken());
        return new ResponseEntity<LoginResponse>(loginResponse,headers,HttpStatus.OK);

    }

    //Logout Request
    @RequestMapping(method=RequestMethod.POST,path="/customer/logout",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LogoutResponse> logout(@RequestHeader("authorization")final String authorization)throws AuthorizationFailedException
    {
        String accessToken = authorization.split("Bearer ")[1];
        CustomerAuthEntity customerAuthEntity = customerBusinessService.logout(accessToken);
        CustomerEntity customer = customerAuthEntity.getCustomer_id();
        LogoutResponse logoutResponse = new LogoutResponse().id(customer.getUuid()).message("LOGGED OUT SUCCESSFULLY");
        return new ResponseEntity<LogoutResponse>(logoutResponse,HttpStatus.OK);
    }

    // update Customer Details Request
    @RequestMapping(method = RequestMethod.PUT,path="/customer",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdateCustomerResponse> updateCustomer(@RequestHeader("authorization")final String authorization,final UpdateCustomerRequest updateCustomerRequest)throws AuthorizationFailedException , UpdateCustomerException
    {

        CustomerEntity updateCustomer =new CustomerEntity();
        if (updateCustomer.getFirstname() == null)
        {
            throw new UpdateCustomerException("UCR-002", "FirstName can not be empty!!");
        }

        updateCustomer.setFirstname(updateCustomerRequest.getFirstName());
        updateCustomer.setLastname(updateCustomerRequest.getLastName());

        String accessToken = authorization.split("Bearer ")[1];
         final CustomerEntity updatedCustomerEntity = customerBusinessService.updateCustomer(accessToken,updateCustomer);
        UpdateCustomerResponse updateCustomerResponse=new UpdateCustomerResponse().id(updatedCustomerEntity.getUuid()).firstName(updatedCustomerEntity.getFirstname())
                .lastName(updatedCustomerEntity.getLastname()).status("CUSTOMER DETAILS UPDATED SUCCESSFULLY");
        return new ResponseEntity<UpdateCustomerResponse>(updateCustomerResponse,HttpStatus.OK);

    }

    // update Password Request
    @RequestMapping(method = RequestMethod.PUT,path="/customer/password",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdatePasswordResponse> updatePassword(@RequestHeader("authorization")final String authorization , final UpdatePasswordRequest updatePasswordRequest)throws AuthorizationFailedException,UpdateCustomerException
    {
        CustomerEntity updateOldPassword = new CustomerEntity();
        if(updatePasswordRequest.getOldPassword()==null||updatePasswordRequest.getNewPassword()==null)
        {
            throw new UpdateCustomerException("UCR-003","No field should be empty!!");
        }

        String accessToken = authorization.split("Bearer ")[1];
        updateOldPassword.setPassword(updatePasswordRequest.getNewPassword());
        final CustomerEntity updatedCustomerEntity = customerBusinessService.updatePassword(accessToken,updateOldPassword,updatePasswordRequest.getOldPassword());

        UpdatePasswordResponse updatePasswordResponse=new UpdatePasswordResponse().id(updatedCustomerEntity.getUuid()).status("CUSTOMER PASSWORD UPDATED SUCCESSFULLY!");
        return new ResponseEntity<UpdatePasswordResponse>(updatePasswordResponse,HttpStatus.OK);
    }
}
