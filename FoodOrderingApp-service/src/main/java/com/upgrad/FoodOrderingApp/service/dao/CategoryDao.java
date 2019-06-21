package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDao {
    @PersistenceContext
    EntityManager entityManager;

public List<CategoryEntity> getAllCategories()
{
    return entityManager.createNamedQuery("getAllCategories",CategoryEntity.class).getResultList();
}

public CategoryEntity getCategoryByUuid(String category_uuid)
{
    try {
        return entityManager.createNamedQuery("getCategoryByUuid", CategoryEntity.class).setParameter("category_uuid", category_uuid).getSingleResult();

        }catch (NoResultException nre)
    {
        return null;
    }

}

public List<ItemEntity> getAllItems(String category_uuid)
{
    try{
        CategoryEntity categoryEntity = entityManager.createNamedQuery("getCategoryByUuid", CategoryEntity.class).setParameter("category_uuid", category_uuid).getSingleResult();

        List<CategoryItemEntity> categoryItemEntityList = entityManager.createNamedQuery("getCategoryItemList", CategoryItemEntity.class)
                .setParameter("category_id", categoryEntity.getId()).getResultList();
        List<ItemEntity> itemEntityList = new ArrayList<>();
        for (int i = 0; i < categoryItemEntityList.size(); i++) {
            ItemEntity itemEntity = entityManager.createNamedQuery("getItemDetailsByItemId", ItemEntity.class).setParameter("item_id", categoryItemEntityList.get(i).getItem_id()).getSingleResult();
            itemEntityList.add(itemEntity);
        }
        return itemEntityList;
    }
    catch (NoResultException nre){return null;}
}
}
