package com.lj.o2o.dao;

import com.lj.o2o.entity.PersonInfo;
import com.lj.o2o.entity.Shop;
import com.lj.o2o.entity.UserShopMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserShopMapDaoTest {

    @Autowired
    private UserShopMapDao userShopMapDao;

    @Test
    public void testInsertUserShopMapList(){
        //用户积分店铺统计1
        UserShopMap userShopMap = new UserShopMap();
        Shop shop = new Shop();
        shop.setShopId(19L);
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1L);
        userShopMap.setShop(shop);
        userShopMap.setUser(personInfo);
        userShopMap.setCreateTime(new Date());
        userShopMap.setPoint(1);
        userShopMapDao.insertUserShopMap(userShopMap);

    }

    @Test
    public void testQueryUserShopMapList(){
        UserShopMap userShopMap = new UserShopMap();
        List<UserShopMap> userShopMaps = userShopMapDao.queryUserShopMapList(userShopMap, 0, 1);
        assertEquals(1,userShopMaps.size());
        int count = userShopMapDao.queryUserShopMapCount(userShopMap);
        assertEquals(1, count);

        //按店铺查询
        Shop shop = new Shop();
        shop.setShopId(19L);
        userShopMap.setShop(shop);
        userShopMaps = userShopMapDao.queryUserShopMapList(userShopMap, 0, 1);
        assertEquals(1,userShopMaps.size());
        int count1 = userShopMapDao.queryUserShopMapCount(userShopMap);
        assertEquals(1, count1);

        //按用户Id和shopId查询
        userShopMap = userShopMapDao.queryUserShopMap(1L, 19L);
        assertEquals("李明",userShopMap.getUser().getName());

    }

    @Test
    public void testUpdateUserShopMap(){
        UserShopMap userShopMap = new UserShopMap();
        userShopMap = userShopMapDao.queryUserShopMap(1L, 19L);
        assertTrue(1 == userShopMap.getPoint(), "Error,积分不一致");
        userShopMap.setPoint(2);
        int i = userShopMapDao.updateUserShopMapPoint(userShopMap);
        assertEquals(1,i);
    }
}
