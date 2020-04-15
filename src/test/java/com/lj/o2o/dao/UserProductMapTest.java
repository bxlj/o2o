package com.lj.o2o.dao;

import com.lj.o2o.entity.PersonInfo;
import com.lj.o2o.entity.Product;
import com.lj.o2o.entity.Shop;
import com.lj.o2o.entity.UserProductMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserProductMapTest {

    @Autowired
    private UserProductMapDao userProductMapDao;

    @Test
    public void testAInsertUserProductMap(){
        // 创建用户商品映射信息1
        UserProductMap userProductMap = new UserProductMap();
        PersonInfo customer  = new PersonInfo();
        customer.setUserId(1L);
        userProductMap.setUser(customer);
        userProductMap.setOperator(customer);
        Product product = new Product();
        product.setProductId(62L);
        userProductMap.setProduct(product);
        Shop shop= new Shop();
        shop.setShopId(19L);
        userProductMap.setShop(shop);
        userProductMap.setCreateTime(new Date());
        int effectedNum = userProductMapDao.insertUserProductMap(userProductMap);
        assertEquals(1,effectedNum);

        // 创建用户商品映射信息2
        UserProductMap userProductMap2 = new UserProductMap();
        PersonInfo customer2  = new PersonInfo();
        customer2.setUserId(1L);
        userProductMap2.setUser(customer2);
        userProductMap2.setOperator(customer2);
        Product product2 = new Product();
        product2.setProductId(63L);
        userProductMap2.setProduct(product2);
        Shop shop2= new Shop();
        shop2.setShopId(19L);
        userProductMap2.setShop(shop2);
        userProductMap2.setCreateTime(new Date());
        int effectedNum2 = userProductMapDao.insertUserProductMap(userProductMap2);
        assertEquals(1,effectedNum2);

        // 创建用户商品映射信息2
        UserProductMap userProductMap3 = new UserProductMap();
        PersonInfo customer3  = new PersonInfo();
        customer3.setUserId(1L);
        userProductMap3.setUser(customer2);
        userProductMap3.setOperator(customer2);
        Product product3 = new Product();
        product3.setProductId(64L);
        userProductMap3.setProduct(product3);
        Shop shop3= new Shop();
        shop3.setShopId(19L);
        userProductMap3.setShop(shop3);
        userProductMap3.setCreateTime(new Date());
        int effectedNum3 = userProductMapDao.insertUserProductMap(userProductMap3);
        assertEquals(1,effectedNum3);
    }

    @Test
    public void testBQueryUserProductMap(){

        UserProductMap userProductMap = new UserProductMap();
        PersonInfo customer  = new PersonInfo();
        customer.setName("李");
        userProductMap.setUser(customer);
        List<UserProductMap> userProductMaps = userProductMapDao.queryUserProductMapList(userProductMap, 0, 2);
        assertEquals(2,userProductMaps.size());


    }

}
