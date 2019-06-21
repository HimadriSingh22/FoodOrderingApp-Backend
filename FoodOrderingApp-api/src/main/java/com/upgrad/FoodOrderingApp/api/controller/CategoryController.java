package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CategoryBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/")
public class CategoryController {
    @Autowired
    CategoryBusinessService categoryBusinessService;

    @RequestMapping(method =RequestMethod.GET ,path="/category",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<CategoryDetailsResponse>> getAllCategories()
    {
        List<CategoryEntity> categoryEntityList = categoryBusinessService.getAllCategories();
        List<CategoryDetailsResponse> categoryDetailsResponseList = new ArrayList<>();
        for(int i=0;i<categoryEntityList.size();i++) {
            CategoryDetailsResponse categoryDetailsResponse = new CategoryDetailsResponse().id(UUID.fromString(categoryEntityList.get(i).getCategory_uuid()))
                    .categoryName(categoryEntityList.get(i).getCategory_name());
            categoryDetailsResponseList.add(categoryDetailsResponse);
        }
        return new ResponseEntity <List<CategoryDetailsResponse>>(categoryDetailsResponseList,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,path = "/category/{category_id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoryDetailsResponse> getCategoryByUuid(@PathVariable("category_uuid")final String category_uuid)throws CategoryNotFoundException
    {
      CategoryEntity categoryEntity = categoryBusinessService.getCategoryByUuid(category_uuid);
      List<ItemList> itemListList = categoryBusinessService.getAllItems(category_uuid);
        //ItemListResponse itemListResponse = new ItemListResponse();
        CategoryDetailsResponse categoryDetailsResponse = new CategoryDetailsResponse().id(UUID.fromString(categoryEntity.getCategory_uuid())).categoryName(categoryEntity.getCategory_name());
        for(int i=0;i<itemListList.size();i++)
        {
            ItemList itemList=new ItemList().id(itemListList.get(i).getId()).itemName(itemListList.get(i).getItemName()).price(itemListList.get(i).getPrice()).itemType(itemListList.get(i).getItemType());
            categoryDetailsResponse.addItemListItem(itemList);
        }
        return new ResponseEntity<CategoryDetailsResponse>(categoryDetailsResponse,HttpStatus.OK);

    }
}
