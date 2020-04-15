package com.lj.o2o.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lj.o2o.entity.ProductCategory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductCategoryDaoTest {

	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Test
	public void testBQueryProductCategoryList() {
		Long shopId = 1L;
		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
		System.out.println("店铺类别的分类数目为："+productCategoryList.size());
	}
	
	@Test
	public void testABatchInsetProductCategory() {
		List<ProductCategory> pList = new ArrayList<>();
		ProductCategory pc1 = new ProductCategory();
		pc1.setProductCategoryName("商品列表1");
		pc1.setPriority(1);
		pc1.setCreateTime(new Date());
		pc1.setShopId(1L);
		ProductCategory pc2 = new ProductCategory();
		pc2.setProductCategoryName("商品列表2");
		pc2.setPriority(2);
		pc2.setCreateTime(new Date());
		pc2.setShopId(1L);
		pList.add(pc1);
		pList.add(pc2);
		int number = productCategoryDao.batchInsetProductCategory(pList);
		assertEquals(2, number);
	}

	@Test
	public void testCdeleteProductCategory() {
		long shopId=1L;
		List<ProductCategory> productCategories = productCategoryDao.queryProductCategoryList(shopId);
		for (ProductCategory pc : productCategories) {
			if(("商品列表1".equals(pc.getProductCategoryName())) || ("商品列表2".equals(pc.getProductCategoryName()))) {
				int effectedNum = productCategoryDao.deleteProductCategory(pc.getProductCategoryId(), shopId);
				assertEquals(1, effectedNum);
			}
		}
	}
	
	
	
}
