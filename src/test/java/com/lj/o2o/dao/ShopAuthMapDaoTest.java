package com.lj.o2o.dao;

import com.lj.o2o.entity.PersonInfo;
import com.lj.o2o.entity.Shop;
import com.lj.o2o.entity.ShopAuthMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ShopAuthMapDaoTest {

    @Autowired
    private ShopAuthMapDao shopAuthMapDao;

    @Test
    public void testInsertShopAuthMap(){
        // 创建店铺授权信息1
        ShopAuthMap shopAuthMap1 = new ShopAuthMap();
        PersonInfo employee = new PersonInfo();
        employee.setUserId(1L);
        Shop shop = new Shop();
        shop.setShopId(17L);
        shopAuthMap1.setShop(shop);
        shopAuthMap1.setEmployee(employee);
        shopAuthMap1.setCreateTime(new Date());
        shopAuthMap1.setEnableStatus(0 );
        shopAuthMap1.setTitle("打工仔");
        shopAuthMap1.setTitleFlag(2);
        int effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap1);
        assertEquals(1,effectedNum);
    }

    /**
     * 测试查询功能
     */
    @Test
    public void testQueryShopAuth() {
        // 测试queryShopAuthMapListByShopId
        List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(19, 0, 2);
        assertEquals(1, shopAuthMapList.size());
        // 测试queryShopAuthMapById
        ShopAuthMap shopAuth = shopAuthMapDao.queryShopAuthMapById(shopAuthMapList.get(0).getShopAuthId());
        assertEquals("店家", shopAuth.getTitle());
        System.out.println("employee's name is :" + shopAuth.getEmployee().getName());
        System.out.println("shop name is :" + shopAuth.getShop().getShopName());
        //测试queryShopAuthCountByShopId
        int count = shopAuthMapDao.queryShopAuthCountByShopId(shopAuthMapList.get(0).getShop().getShopId());
        assertEquals(1,count);
    }

    /**
     * 测试更新功能
     */
    @Test
    public void testUpdateShopAuthMap(){
        List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(19, 0, 2);
        shopAuthMapList.get(0).setTitle("CEO");

        shopAuthMapList.get(0).setTitleFlag(2);
        int effectedNum = shopAuthMapDao.updateShopAuthMap(shopAuthMapList.get(0));
        assertEquals(1, effectedNum);
    }

    /**
     * 测试删除功能
     *
     * @throws Exception
     */
    @Test
    public void testDeleteShopAuthMap() throws Exception {
        List<ShopAuthMap> shopAuthMapList1 = shopAuthMapDao.queryShopAuthMapListByShopId(19, 0, 1);
        List<ShopAuthMap> shopAuthMapList2 = shopAuthMapDao.queryShopAuthMapListByShopId(17, 0, 1);
        int effectedNum = shopAuthMapDao.deleteShopAuthMap(shopAuthMapList1.get(0).getShopAuthId());
        assertEquals(1, effectedNum);
        effectedNum = shopAuthMapDao.deleteShopAuthMap(shopAuthMapList2.get(0).getShopAuthId());
        assertEquals(1, effectedNum);
    }

}
