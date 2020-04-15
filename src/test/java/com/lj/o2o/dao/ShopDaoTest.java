package com.lj.o2o.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lj.o2o.entity.Area;
import com.lj.o2o.entity.PersonInfo;
import com.lj.o2o.entity.Shop;
import com.lj.o2o.entity.ShopCategory;

public class ShopDaoTest {

	@Autowired
	private ShopDao shopDao;
	
	@Test
	public void testQueryShopListAndCount() {
		Shop shopCondition = new Shop();
		ShopCategory childCategory = new ShopCategory();
		ShopCategory parentCategory = new ShopCategory();
		parentCategory.setShopCategoryId(1L);
		childCategory.setParent(parentCategory);
		shopCondition.setShopCategroy(childCategory);
	    List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
	    int count = shopDao.queryShopCount(shopCondition);
	    System.out.println("店铺列表大小："+shopList.size());
	    System.out.println("店铺总数："+count);
	}
	
	@Test
	@Ignore
	public void testQueryByShopId() {
		Long id = 1L;
		Shop shop = shopDao.queryByShopId(id);
		System.out.println(shop.getArea().getAreaId());
		System.out.println(shop.getArea().getAreaName());
	}
	
	@Test
	@Ignore
	public void testInsertShop() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategroy = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategroy.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategroy(shopCategroy);
		shop.setShopName("测试商铺");
		shop.setShopDesc("test");
		shop.setShopAddr("test");
		shop.setPhone("test");
		shop.setShopImg("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		int number = shopDao.insertShop(shop);
		assertEquals(1,number);
	}
	
	@Test
	@Ignore
	public void testUpdateShop() {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopDesc("测试描述");
		shop.setShopAddr("测试地址");
		shop.setLastEditTime(new Date());
		int number = shopDao.updateShop(shop);
		assertEquals(1,number);
	}
	
	
	
}
