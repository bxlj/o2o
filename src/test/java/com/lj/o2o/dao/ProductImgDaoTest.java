package com.lj.o2o.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lj.o2o.entity.ProductImg;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductImgDaoTest {

	@Autowired
	private ProductImgDao productImgDao;
	
	@Test
	public void testAProductImg() {
		ProductImg pi1 = new ProductImg();
		pi1.setImgAddr("图片3");
		pi1.setImgDesc("测试图片3");
		pi1.setPriority(3);
		pi1.setCreateTime(new Date());
		pi1.setProductId(1L);
		
		ProductImg pi2 = new ProductImg();
		pi2.setImgAddr("图片4");
		pi2.setImgDesc("测试图片4");
		pi2.setPriority(4);
		pi2.setCreateTime(new Date());
		pi2.setProductId(1L);
		
		List<ProductImg> pImgList = new ArrayList<>();
		pImgList.add(pi1);
		pImgList.add(pi2);
		int num = productImgDao.batchInsertProductImg(pImgList);
		assertEquals(2, num);
	}
	
	@Test
	public void testCDeleteProductImgByProductId() {
		//删除新增两条商品详情图
		Long productId = 1L;
		int effectedNum = productImgDao.deleteProductImgByProductId(productId);
		assertEquals(2, effectedNum);
		
	}
}
