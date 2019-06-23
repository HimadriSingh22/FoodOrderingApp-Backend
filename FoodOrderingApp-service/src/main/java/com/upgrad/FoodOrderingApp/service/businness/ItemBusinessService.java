package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.ItemDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderItemDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.ItemNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ItemBusinessService {
    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private OrderItemDao orderItemDao;
    @Transactional(propagation = Propagation.REQUIRED)
    public List<ItemEntity> getItemsByPopularity(String restaurant_uuid)throws RestaurantNotFoundException
    {
      RestaurantEntity restaurantEntity = restaurantDao.getRestaurantByUuid(restaurant_uuid);
      if(restaurantEntity==null)
      {
          throw new RestaurantNotFoundException("RNF-001","No restaurant by this id");
      }

      List<ItemEntity> itemEntityList = orderDao.getOrdersByRestaurant(restaurantEntity);

        // count all with map
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (ItemEntity itemEntity : itemEntityList) {
            Integer count = map.get(itemEntity.getItem_uuid());
            map.put(itemEntity.getItem_uuid(), (count == null) ? 1 : count + 1);
        }

        // sort map
        Map<String, Integer> treeMap = new TreeMap<String, Integer>(map);
        List<ItemEntity> sortedItemEntityList = new ArrayList<ItemEntity>();
        for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
            sortedItemEntityList.add(itemDao.getItemByUUID(entry.getKey()));
        }
        Collections.reverse(sortedItemEntityList);

        return sortedItemEntityList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<OrderItemEntity> getItemsByOrder(OrderEntity orderEntity)
    {
        return orderItemDao.getItemsByOrder(orderEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ItemEntity getItemByUUID(String item_uuid)throws ItemNotFoundException
    {
        ItemEntity itemEntity = itemDao.getItemByUUID(item_uuid);
        if(itemEntity==null)
        {
            throw new ItemNotFoundException("INF-003","No item by this id exist");
        }
        return itemEntity;
    }

}
