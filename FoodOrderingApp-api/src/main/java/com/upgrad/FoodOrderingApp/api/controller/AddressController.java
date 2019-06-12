package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.SaveAddressRequest;
import com.upgrad.FoodOrderingApp.api.model.SaveAddressResponse;
import com.upgrad.FoodOrderingApp.service.businness.AddressBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
@RequestMapping("/")
public class AddressController
{
    @Autowired
    private AddressBusinessService addressBusinessService;
       @RequestMapping(method = RequestMethod.POST,path="/address",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
       public ResponseEntity<SaveAddressResponse> saveAddress(
               @RequestHeader("authorization")final  String authorization , SaveAddressRequest saveAddressRequest)throws SaveAddressException,AuthorizationFailedException,AddressNotFoundException
       {
           AddressEntity addressEntity = new AddressEntity();
           addressEntity.setFlat_buil_number(saveAddressRequest.getFlatBuildingName());
           addressEntity.setCity(saveAddressRequest.getCity());
           addressEntity.setLocality(saveAddressRequest.getLocality());
           addressEntity.setPincode(saveAddressRequest.getPincode());
           addressEntity.setState_id(saveAddressRequest.getStateUuid());
           addressEntity.setActive("1");
           final AddressEntity savedAddress = addressBusinessService.saveAddress(authorization,addressEntity);
           SaveAddressResponse saveAddressResponse = new SaveAddressResponse().id(savedAddress.getUuid()).status("ADDRESS  SUCCESSFULLY REGISTERED!!");
           return new ResponseEntity<SaveAddressResponse>(saveAddressResponse,HttpStatus.OK);

       }

}
