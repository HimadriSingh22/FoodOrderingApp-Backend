package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.ItemList;
import com.upgrad.FoodOrderingApp.api.model.ItemListResponse;
import com.upgrad.FoodOrderingApp.service.businness.ItemBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/")
public class ItemController {
    @Autowired
    private ItemBusinessService itemBusinessService;
    @RequestMapping(method = RequestMethod.GET,path="/item/restaurant/{restaurant_id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ItemListResponse> getItemsByPopularity(@PathVariable("restaurant_uuid")final String restaurant_uuid)throws RestaurantNotFoundException
    {
        List<ItemEntity> itemEntityList = itemBusinessService.getItemsByPopularity(restaurant_uuid);
        ItemListResponse itemListResponse= new ItemListResponse();
        int count =0;
        for(ItemEntity itemEntity:itemEntityList)
        {
            if(count<5)
            {
                ItemList itemList = new ItemList().id(UUID.fromString(itemEntity.getItem_uuid()
                )).itemName(itemEntity.getItem_name()).price(itemEntity.getPrice())
                        .itemType(ItemList.ItemTypeEnum.fromValue(itemEntity.getType().getValue()));
                        itemListResponse.add(itemList);
                        count = count+1;
            }else{
                break;
            }

        }

 return new ResponseEntity<ItemListResponse>(itemListResponse,HttpStatus.OK);
}
}