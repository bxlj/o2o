package com.lj.o2o.dao;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lj.o2o.entity.Product;
import com.lj.o2o.entity.ProductCategory;
import com.lj.o2o.entity.ProductImg;
import com.lj.o2o.entity.Shop;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductDaoTest {

	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProductImgDao productImgDao;
	

	@Test
	@Ignore
	public void testABatchInsertProduct() {
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory pc1 = new ProductCategory();
        pc1.setProductCategoryId(1L);
        Product product1 = new Product();
        product1.setProductName("测试商品1");
        product1.setProductDesc("测试商品1");
        product1.setImgAddr("test1");
        product1.setPriority(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setEnableStatus(0);
        product1.setProductCategory(pc1);
        product1.setShop(shop);
        Product product2 = new Product();
        product2.setProductName("测试商品2");
        product2.setProductDesc("测试商品2");
        product1.setImgAddr("test2");
        product2.setPriority(2);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setEnableStatus(0);
        product2.setProductCategory(pc1);
        product2.setShop(shop);
        Product product3 = new Product();
        product3.setProductName("商品3");
        product3.setProductDesc("商品3");
        product1.setImgAddr("test3");
        product3.setPriority(3);
        product3.setCreateTime(new Date());
        product3.setLastEditTime(new Date());
        product3.setEnableStatus(0);
        product3.setProductCategory(pc1);
        product3.setShop(shop);
        
        int num1 = productDao.insertProduct(product1);
        assertEquals(1, num1);
        int num2 = productDao.insertProduct(product2);
        assertEquals(1, num2);
        int num3 = productDao.insertProduct(product3);
        assertEquals(1, num3);
	}
	
	@Test
	@Ignore
	public void testCqueryProductById() {
		long productId = 2L;
		//初始化两个商品详情图实例作为productId=1下的商品详情图
		//批量插入商品详情图
		ProductImg productImg1 = new ProductImg();
		productImg1.setCreateTime(new Date());
		productImg1.setImgAddr("图片1");
		productImg1.setImgDesc("测试商品1");
		productImg1.setProductId(productId);
		productImg1.setPriority(1);
		ProductImg productImg2 = new ProductImg();
		productImg2.setCreateTime(new Date());
		productImg2.setImgAddr("图片2");
		productImg2.setImgDesc("测试商品2");
		productImg2.setProductId(productId);
		productImg2.setPriority(2);
		List<ProductImg> productImgList = new ArrayList<>();
		productImgList.add(productImg1);
		productImgList.add(productImg2);
		int effectedNum = productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2, effectedNum);
		//通过查询productId=1商品信息返回并校验
		Product product = productDao.queryProductById(productId);
		System.out.println(product.getProductImgList().size());
		assertEquals(2, product.getProductImgList().size());
		//删除两个商品详情图
		effectedNum = productImgDao.deleteProductImgByProductId(productId);
		assertEquals(2, effectedNum);
		
	}
	
	@Test
	
	public void testDupdateProduct() {
		Product product = new Product();
		ProductCategory pc = new ProductCategory();
		Shop shop = new Shop();
		shop.setShopId(19L);
		pc.setProductCategoryId(20L);
		product.setProductId(62L);
		product.setProductName("巧克力珍珠奶茶");
		product.setPoint(3);
		product.setShop(shop);
		product.setProductCategory(pc);
		//修改productId=1的商品名称
		int effectedNum = productDao.updateProduct(product);
		assertEquals(1, effectedNum);
	}
	
	@Test
	@Ignore
	public void testBQueryProductList() {
		Product productCondition = new Product();
		//分页查询返回预期的结果
		List<Product> products  = productDao.queryProductList(productCondition, 0, 3);
		assertEquals(3, products.size());
		//查询商品数量
		int count = productDao.queryProductCount(productCondition);
		assertEquals(9, count);
		//查询商品名中还有测试的商品
		productCondition.setProductName("测试");
		List<Product> products2 = productDao.queryProductList(productCondition, 0, 3);
		assertEquals(2, products2.size());
		count = productDao.queryProductCount(productCondition);
		assertEquals(2, count);
	}
	
	@Test
	@Ignore
	public void testEUpdateProductCategoryToNull() {
		//将productCategoryId为2的商品类别下的商品的商品类别置为空
		int effectedNum = productDao.updateProductCategoryToNull(2L);
		assertEquals(1, effectedNum);
	}
	
	
}
