package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestaurantDao {
    @PersistenceContext
    private EntityManager entityManager;
    public List<RestaurantEntity> showRestaurantList()
    {
      List<RestaurantEntity> resultList =  entityManager.createNamedQuery("getAllRestaurants",RestaurantEntity.class).getResultList();
      return resultList;
    }

    public List<RestaurantEntity> getRestaurantByName(final String restaurant_name)
    {
        return entityManager.createNamedQuery("getRestaurantByName",RestaurantEntity.class).setParameter("restaurant_name",restaurant_name).getResultList();
    }
   public List<CategoryEntity> getAllCategories()
   {
       return entityManager.createNamedQuery("getAllCategories",CategoryEntity.class).getResultList();
   }
   public CategoryEntity checkCategoryId(String category_uuid)
   {
       try {
           return entityManager.createNamedQuery("getCategoryByUuid", CategoryEntity.class).setParameter("category_uuid", category_uuid).getSingleResult();
       }
       catch (NoResultException no)
       {
           return null;
       }
   }
   public List<RestaurantEntity> getRestaurantsByCategoryId(String category_uuid)
   {
       try{
           CategoryEntity categoryEntity = entityManager.createNamedQuery("getCategoryIdByUuid",CategoryEntity.class).setParameter("category_uuid",category_uuid).getSingleResult();
           List<RestaurantCategoryEntity> restaurantCategoryEntityList = entityManager.createNamedQuery("getRestaurantIdByCategoryId",RestaurantCategoryEntity.class).setParameter("category_id",categoryEntity.getId()).getResultList();
           List<RestaurantEntity> restaurantEntityList =new ArrayList<>();
          for(int i=0;i<restaurantCategoryEntityList.size();i++) {
              RestaurantEntity restaurantEntity = entityManager.createNamedQuery("getRestaurantsById", RestaurantEntity.class).setParameter("restaurant_id", restaurantCategoryEntityList).getSingleResult();
              restaurantEntityList.add(restaurantEntity);
          } return restaurantEntityList;
       }
       catch (NoResultException no)
       {
           return null;
       }
   }

   public RestaurantEntity getRestaurantByUuid(String restaurant_uuid)
   {
       try{
           return entityManager.createNamedQuery("getRestaurantByUuid",RestaurantEntity.class).setParameter("restaurant_uuid",restaurant_uuid).getSingleResult();
       }
       catch (NoResultException no)
       {
           return null;
       }
   }
   public List<CategoryEntity> getCategoroiesByRestaurantId(String restaurant_uuid)
   {
       try{
           RestaurantEntity restaurantEntity = entityManager.createNamedQuery("getRestaurantByUuid",RestaurantEntity.class).setParameter("restaurant_uuid",restaurant_uuid).getSingleResult();
         List<RestaurantCategoryEntity> restaurantCategoryEntityList=  entityManager.createNamedQuery("getAllCategoryIdByRestaurantId",RestaurantCategoryEntity.class).setParameter("restaurant_id",restaurantEntity.getRestaurant_id()).getResultList();
         List<CategoryEntity> categoryEntityList = new ArrayList<>();
         for(int i=0;i<restaurantCategoryEntityList.size();i++)
         {
        CategoryEntity categoryEntity = entityManager.createNamedQuery("getAllCategoryDetails",CategoryEntity.class).setParameter("category_id",restaurantCategoryEntityList.get(i).getCategory_id())
                 .getSingleResult();
         categoryEntityList.add(categoryEntity);}
         return categoryEntityList;
       }catch (NoResultException no)
       {
           return null;
       }
   }

   public List<ItemEntity> getAllItems(String restaurant_uuid)
   {
       try
       {
           RestaurantEntity restaurantEntity = entityManager.createNamedQuery("getRestaurantByUuid",RestaurantEntity.class).setParameter("restaurant_uuid",restaurant_uuid).getSingleResult();
           List<RestaurantItemEntity> restaurantItemEntityList = entityManager.createNamedQuery("getAllItemIdByRestaurantId",RestaurantItemEntity.class).setParameter("restaurant_id",restaurantEntity.getRestaurant_id()).getResultList();
           List<ItemEntity> itemEntityList = new ArrayList<>();
           for(int i =0;i<restaurantItemEntityList.size();i++)
           {
               ItemEntity itemEntity = entityManager.createNamedQuery("getAllItems",ItemEntity.class).setParameter("item_id",restaurantItemEntityList.get(i).getItem_id()).getSingleResult();
               itemEntityList.add(itemEntity);

           }
           return itemEntityList;
       }catch (NoResultException no)
       {
           return null;
       }
   }

   public List<CategoryEntity> getCategoryNameById(String category_uuid)
   {
       return entityManager.createNamedQuery("getCategoryByUuid",CategoryEntity.class).setParameter("category_uuid",category_uuid).getResultList();
   }

   public RestaurantEntity updateRestaurantRating( RestaurantEntity restaurantEntity)
   {
      return entityManager.merge(restaurantEntity);
   }

}
