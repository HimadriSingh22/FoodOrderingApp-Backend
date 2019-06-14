package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.AddressBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.AddressEntity;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin
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
           SaveAddressResponse saveAddressResponse = new SaveAddressResponse().id(savedAddress.getUuid().toString()).status("ADDRESS  SUCCESSFULLY REGISTERED!!");
           return new ResponseEntity<SaveAddressResponse>(saveAddressResponse,HttpStatus.OK);

       }

       @RequestMapping(method=RequestMethod.GET,path="/address/customer")
    public ResponseEntity<List<AddressListResponse>>showAddressList(
            @RequestHeader("authorization")final String authorization)throws AuthorizationFailedException
       {
           List<AddressList> allAddresses = addressBusinessService.showAddressList(authorization);
           List<AddressListResponse> addressListResponseList = new ArrayList<AddressListResponse>();
           for (int i=0;i<allAddresses.size();i++)
           {
               AddressListResponse addressListResponse = new AddressListResponse()
                       .addAddressesItem(allAddresses.get(i).id(allAddresses.get(i).getId()).flatBuildingName(allAddresses.get(i).getFlatBuildingName())
                               .city(allAddresses.get(i).getCity()).locality(allAddresses.get(i).getLocality()).pincode(allAddresses.get(i).getPincode()
                               ).state(allAddresses.get(i).getState()));
                       addressListResponseList.add(addressListResponse);
           }

           return new ResponseEntity<List<AddressListResponse>>(addressListResponseList,HttpStatus.OK);
       }

       @RequestMapping(method = RequestMethod.DELETE,path="/address/{address_id}",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DeleteAddressResponse> deleteSavedAddress(@RequestHeader("authorization")final String authorization, @PathVariable("address_id") final String addressUuid)throws AuthorizationFailedException,AddressNotFoundException
       {
           AddressEntity addressEntity = addressBusinessService.deleteAddress(addressUuid,authorization);
           DeleteAddressResponse deleteAddressResponse=new DeleteAddressResponse().id(UUID.fromString(addressEntity.getUuid().toString())).status("ADDRESS DELETED SUCCESSFULLY");
           return new ResponseEntity<DeleteAddressResponse>(deleteAddressResponse,HttpStatus.OK);
       }

       @RequestMapping(method = RequestMethod.GET,path = "/states",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<StatesListResponse>> showStateList()
       {
           List<StatesList> statesLists = addressBusinessService.showStateList();
           List<StatesListResponse> statesListResponseList = new ArrayList<StatesListResponse>();
           for(int i=0;i<statesLists.size();i++)
           {
               StatesListResponse statesListResponse = new StatesListResponse().addStatesItem(statesLists.get(i).id(statesLists.get(i).getId()).stateName(statesLists.get(i).getStateName()));
               statesListResponseList.add(statesListResponse);
           }
           return new ResponseEntity<List<StatesListResponse>>(statesListResponseList,HttpStatus.OK);

       }


}
