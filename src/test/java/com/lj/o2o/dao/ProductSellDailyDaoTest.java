package com.lj.o2o.dao;

import com.lj.o2o.entity.ProductSellDaily;
import com.lj.o2o.entity.Shop;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductSellDailyDaoTest {

    @Autowired
    private ProductSellDailyDao productSellDailyDao;

    @Test
    public void testInsertProductSellDaily(){
        int effectedNum = productSellDailyDao.insertProductSellDaily();
        assertEquals(3,effectedNum);
    }

    @Test
    public void testInsertDefaultProductSellDaily(){
        int effectedNum = productSellDailyDao.insertDefaultProductSellDaily();
        assertEquals(1,effectedNum);
    }


    @Test
    @Ignore
    public void testQueryProductSellDailyList(){
        ProductSellDaily productSellDaily = new ProductSellDaily();

        Shop shop = new Shop();
        shop.setShopId(19L);
        productSellDaily.setShop(shop);
        List<ProductSellDaily> dailyList =
                productSellDailyDao.queryProductSellDailyList(productSellDaily, null,null);

        System.out.println(dailyList.size());
    }
}
