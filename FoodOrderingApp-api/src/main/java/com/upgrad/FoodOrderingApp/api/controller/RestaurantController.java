package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/")
public class RestaurantController {
@Autowired
private RestaurantBusinessService restaurantBusinessService;
    @RequestMapping(method = RequestMethod.GET,path="/restaurant",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<RestaurantListResponse>> showRestaurantList()
    {
        List<RestaurantList> restaurantLists = restaurantBusinessService.showRestaurantList();
        List<RestaurantListResponse> restaurantListResponseList = new ArrayList<RestaurantListResponse>();
        List<RestaurantDetailsResponseAddress> restaurantDetailsResponseAddresses =new ArrayList<RestaurantDetailsResponseAddress>();
        List<RestaurantDetailsResponseAddressState> restaurantDetailsResponseAddressStates=new ArrayList<RestaurantDetailsResponseAddressState>();
        for(int i=0;i<restaurantLists.size();i++)
        {
            RestaurantListResponse restaurantListResponse =new RestaurantListResponse().addRestaurantsItem(restaurantLists.get(i).id(restaurantLists.get(i).getId())
                    .restaurantName(restaurantLists.get(i).getRestaurantName()).photoURL(restaurantLists.get(i).getPhotoURL()).customerRating(restaurantLists.get(i).getCustomerRating())
            .numberCustomersRated(restaurantLists.get(i).getNumberCustomersRated()).averagePrice(restaurantLists.get(i).getAveragePrice()).address(restaurantDetailsResponseAddresses.get(i).id(restaurantDetailsResponseAddresses.get(i).getId())
                            .flatBuildingName(restaurantDetailsResponseAddresses.get(i).getFlatBuildingName()).locality(restaurantDetailsResponseAddresses.get(i).getLocality())
                    .city(restaurantDetailsResponseAddresses.get(i).getCity()).pincode(restaurantDetailsResponseAddresses.get(i).getPincode()).state(restaurantDetailsResponseAddressStates.get(i).id(restaurantDetailsResponseAddressStates.get(i).getId())
                                    .stateName(restaurantDetailsResponseAddressStates.get(i).getStateName()))));

            restaurantListResponseList.add(restaurantListResponse);

        }
        return new ResponseEntity<List<RestaurantListResponse>>(restaurantListResponseList,HttpStatus.FOUND);
    }

    //@RequestMapping(method=RequestMethod)
}
