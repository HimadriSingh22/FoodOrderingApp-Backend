package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryBusinessService {
    @Autowired
    CategoryDao categoryDao;
  public List<CategoryEntity> getAllCategories()
  {
        List<CategoryEntity> categoryEntityList = categoryDao.getAllCategories();
        return categoryEntityList;
  }

  public CategoryEntity getCategoryByUuid(String category_uuid)throws CategoryNotFoundException {
      if (category_uuid == null) {
          throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
      }
      CategoryEntity categoryEntity = categoryDao.getCategoryByUuid(category_uuid);
      if (categoryEntity == null) {
          throw new CategoryNotFoundException("CNF-002", "No category by this id");
      }
      return categoryEntity;
  }

  public List getAllItems(String category_uuid )
  {
      return  categoryDao.getAllItems(category_uuid);
  }




  }

