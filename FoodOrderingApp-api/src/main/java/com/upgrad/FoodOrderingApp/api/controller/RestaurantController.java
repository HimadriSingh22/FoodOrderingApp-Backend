package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CustomerBusinessService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.InvalidRatingException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/")
public class RestaurantController {
    @Autowired
    private RestaurantBusinessService restaurantBusinessService;
    @Autowired
    private CustomerBusinessService customerBusinessService;

    @RequestMapping(method = RequestMethod.GET, path = "/restaurant", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<RestaurantListResponse>> showRestaurantList() {
        List<RestaurantList> restaurantLists = restaurantBusinessService.showRestaurantList();
        List<RestaurantListResponse> restaurantListResponseList = new ArrayList<RestaurantListResponse>();
        List<RestaurantDetailsResponseAddress> restaurantDetailsResponseAddresses = new ArrayList<RestaurantDetailsResponseAddress>();
        List<RestaurantDetailsResponseAddressState> restaurantDetailsResponseAddressStates = new ArrayList<RestaurantDetailsResponseAddressState>();
        for (int i = 0; i < restaurantLists.size(); i++) {
            RestaurantListResponse restaurantListResponse = new RestaurantListResponse().addRestaurantsItem(restaurantLists.get(i).id(restaurantLists.get(i).getId())
                    .restaurantName(restaurantLists.get(i).getRestaurantName()).photoURL(restaurantLists.get(i).getPhotoURL()).customerRating(restaurantLists.get(i).getCustomerRating())
                    .numberCustomersRated(restaurantLists.get(i).getNumberCustomersRated()).averagePrice(restaurantLists.get(i).getAveragePrice())
                    .address(restaurantDetailsResponseAddresses.get(i).id(restaurantDetailsResponseAddresses.get(i).getId())
                            .flatBuildingName(restaurantDetailsResponseAddresses.get(i).getFlatBuildingName()).locality(restaurantDetailsResponseAddresses.get(i).getLocality())
                            .city(restaurantDetailsResponseAddresses.get(i).getCity()).pincode(restaurantDetailsResponseAddresses.get(i).getPincode()).state(restaurantDetailsResponseAddressStates.get(i).id(restaurantDetailsResponseAddressStates.get(i).getId())
                                    .stateName(restaurantDetailsResponseAddressStates.get(i).getStateName()))));

            restaurantListResponseList.add(restaurantListResponse);

        }
        return new ResponseEntity<List<RestaurantListResponse>>(restaurantListResponseList, HttpStatus.FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/restaurant/name/{restaurant_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<RestaurantDetailsResponse>> getRestaurantByName(@PathVariable("restaurant_name") final String restaurant_name) throws RestaurantNotFoundException {
        List<RestaurantList> restaurantByName = restaurantBusinessService.getRestaurantByName(restaurant_name);

        List<CategoryList> categoryLists = restaurantBusinessService.getAllCategories();
        List<RestaurantDetailsResponse> restaurantDetailsResponses = new ArrayList<RestaurantDetailsResponse>();
        if (restaurantByName == null) {
            return new ResponseEntity<List<RestaurantDetailsResponse>>(restaurantDetailsResponses, HttpStatus.NOT_FOUND);
        }


        List<RestaurantDetailsResponseAddressState> restaurantDetailsResponseAddressStates = new ArrayList<RestaurantDetailsResponseAddressState>();
        List<RestaurantDetailsResponseAddress> restaurantDetailsResponseAddresses = new ArrayList<RestaurantDetailsResponseAddress>();

        for (int i = 0; i < restaurantByName.size(); i++) {
            RestaurantDetailsResponse restaurantDetailsResponse = new RestaurantDetailsResponse().id(restaurantByName.get(i).getId()).restaurantName(restaurantByName.get(i).getRestaurantName())
                    .photoURL(restaurantByName.get(i).getPhotoURL()).averagePrice(restaurantByName.get(i).getAveragePrice()).customerRating(restaurantByName.get(i).getCustomerRating())
                    .address(restaurantDetailsResponseAddresses.get(i).id(restaurantDetailsResponseAddresses.get(i).getId())
                            .flatBuildingName(restaurantDetailsResponseAddresses.get(i).getFlatBuildingName()).locality(restaurantDetailsResponseAddresses.get(i).getLocality())
                            .city(restaurantDetailsResponseAddresses.get(i).getCity()).pincode(restaurantDetailsResponseAddresses.get(i).getPincode())
                            .state(restaurantDetailsResponseAddressStates.get(i).id(restaurantDetailsResponseAddressStates.get(i).getId())
                                    .stateName(restaurantDetailsResponseAddressStates.get(i).getStateName())));
            restaurantDetailsResponses.add(restaurantDetailsResponse);
        }
        return new ResponseEntity<List<RestaurantDetailsResponse>>(restaurantDetailsResponses, HttpStatus.FOUND);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/restaurant/category/{category_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<RestaurantDetailsResponse>> getRestaurantByCategoryId(@PathVariable("category_uuid") final String category_uuid) throws CategoryNotFoundException {

        List<RestaurantList> restaurantLists = restaurantBusinessService.getRestaurantByCategoryId(category_uuid);
        List<RestaurantDetailsResponse> restaurantDetailsResponses = new ArrayList<RestaurantDetailsResponse>();
        if (restaurantLists == null) {
            return new ResponseEntity<List<RestaurantDetailsResponse>>(restaurantDetailsResponses, HttpStatus.NOT_FOUND);
        }
        List<RestaurantDetailsResponseAddressState> restaurantDetailsResponseAddressStates = new ArrayList<RestaurantDetailsResponseAddressState>();
        List<RestaurantDetailsResponseAddress> restaurantDetailsResponseAddresses = new ArrayList<RestaurantDetailsResponseAddress>();

        List<CategoryList> categoryLists = restaurantBusinessService.getCategoriesByCategoryId(category_uuid);
        for (int i = 0; i < restaurantLists.size(); i++) {
            RestaurantDetailsResponse restaurantDetailsResponse = new RestaurantDetailsResponse().id(restaurantLists.get(i).getId()).restaurantName(restaurantLists.get(i).getRestaurantName())
                    .photoURL(restaurantLists.get(i).getPhotoURL()).averagePrice(restaurantLists.get(i).getAveragePrice()).customerRating(restaurantLists.get(i).getCustomerRating())
                    .address(restaurantDetailsResponseAddresses.get(i).id(restaurantDetailsResponseAddresses.get(i).getId())
                            .flatBuildingName(restaurantDetailsResponseAddresses.get(i).getFlatBuildingName()).locality(restaurantDetailsResponseAddresses.get(i).getLocality())
                            .city(restaurantDetailsResponseAddresses.get(i).getCity()).pincode(restaurantDetailsResponseAddresses.get(i).getPincode())
                            .state(restaurantDetailsResponseAddressStates.get(i).id(restaurantDetailsResponseAddressStates.get(i).getId())
                                    .stateName(restaurantDetailsResponseAddressStates.get(i).getStateName())));
                  for(int j=0;j<categoryLists.size();j++)
                  {
                      CategoryList categoryList= new CategoryList().categoryName(categoryLists.get(j).getCategoryName());
                      restaurantDetailsResponse.addCategoriesItem(categoryList);
                  }
            restaurantDetailsResponses.add(restaurantDetailsResponse);
        }

        return new ResponseEntity<List<RestaurantDetailsResponse>>(restaurantDetailsResponses, HttpStatus.FOUND);
    }

    @RequestMapping(method = RequestMethod.GET , path = "/api/restaurant/{retaurant_id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantDetailsResponse> getRestaurantById(@PathVariable("restaurant_uuid")final String restaurant_uuid)throws RestaurantNotFoundException
    {
        RestaurantEntity restaurantEntity = restaurantBusinessService.getRestaurantByUuid(restaurant_uuid);
        RestaurantDetailsResponseAddress restaurantDetailsResponseAddress = new RestaurantDetailsResponseAddress();
        RestaurantDetailsResponseAddressState restaurantDetailsResponseAddressState = new RestaurantDetailsResponseAddressState();
        RestaurantDetailsResponse restaurantDetailsResponse = new RestaurantDetailsResponse().id(UUID.fromString(restaurantEntity.getRestaurant_uuid()))
                .restaurantName(restaurantEntity.getRestaurant_name()).customerRating(BigDecimal.valueOf(restaurantEntity.getCustomer_rating())).averagePrice(restaurantEntity.getAvg_price_for_two())
                .photoURL(restaurantEntity.getRestaurant_photo_url()).numberCustomersRated(restaurantEntity.getNumber_of_customers_rated())
                .address(restaurantDetailsResponseAddress.id(restaurantDetailsResponseAddress.getId()).flatBuildingName(restaurantDetailsResponseAddress.getFlatBuildingName())
                        .locality(restaurantDetailsResponseAddress.getLocality()).city(restaurantDetailsResponseAddress.getCity()).pincode(restaurantDetailsResponseAddress.getPincode())
                .state(restaurantDetailsResponseAddressState.id(restaurantDetailsResponseAddressState.getId()).stateName(restaurantDetailsResponseAddressState.getStateName())));

        List<CategoryList> categoryLists = restaurantBusinessService.getCategoriesByRestaurantId(restaurant_uuid);
        CategoryDetailsResponse categoryDetailsResponse= new CategoryDetailsResponse();

        List<ItemList> itemLists = restaurantBusinessService.getAllItems(restaurant_uuid);
        ItemListResponse itemListResponse = new ItemListResponse();

        for (int i =0;i<categoryLists.size();i++) {
            CategoryList categoryList = new CategoryList().id(categoryLists.get(i).getId()).categoryName(categoryLists.get(i).getCategoryName());
            for(int j=0;j<itemLists.size();j++) {
                ItemList itemList = new ItemList().id(itemLists.get(j).getId()).itemName(itemLists.get(j).getItemName()).price(itemLists.get(j).getPrice()).itemType(itemLists.get(j).getItemType());
            categoryDetailsResponse.addItemListItem(itemList);
            }
            restaurantDetailsResponse.addCategoriesItem(categoryList);
        }

        return new ResponseEntity<RestaurantDetailsResponse>(restaurantDetailsResponse,HttpStatus.FOUND);
    }

    @RequestMapping(method = RequestMethod.PUT,path = "/api/restaurant/{restaurant_id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantUpdatedResponse> updateRestaurant(@PathVariable("restaurant_uuid")final String restaurant_uuid,@PathVariable("customer_rating") final Double customer_rating,@RequestHeader("authorization")final String authorization)
            throws RestaurantNotFoundException,AuthorizationFailedException,InvalidRatingException
    {
        String accessToken = authorization.split("Bearer ")[1];
        RestaurantEntity restaurantEntity = restaurantBusinessService.updateRestaurantRating(accessToken,restaurant_uuid,customer_rating);

        RestaurantUpdatedResponse restaurantUpdatedResponse = new RestaurantUpdatedResponse().id(UUID.fromString(restaurantEntity.getRestaurant_uuid())).status("RESTAURANT RATING UPDATED SUCCESSFULLY");
        return new ResponseEntity<RestaurantUpdatedResponse>(restaurantUpdatedResponse,HttpStatus.OK);
    }

}
