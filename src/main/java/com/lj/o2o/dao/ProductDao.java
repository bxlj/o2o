package com.lj.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lj.o2o.entity.Product;

public interface ProductDao {

	/**
	  * 插入商品
	 * 
	 * @param product
	 * @return
	 */
	int insertProduct(Product product);

	/**
	 * 通过productId查询唯一的商品信息
	 * 
	 * @param productId
	 * @return
	 */
	Product queryProductById(long productId);

	/**
	 * 更新商品信息
	 * 
	 * @param product
	 * @return
	 */
	int updateProduct(Product product);

	/**
	  *  查询商品列表并分页: 商品名(可以模糊查询) ,商品状态,店铺id,商品类别
	 * @param productCondition
	 * @param rowIndex 从数据库的某个位置开始查询
	 * @param pageSize 返回的数据个数
	 * @return
	 */
	List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex,
                                   @Param("pageSize") int pageSize);
	
	/**
	 * 查询对应商品总数
	 * @param productCondition
	 * @return
	 */
	int queryProductCount(@Param("productCondition") Product productCondition);
	
	
	/**
	 * 删除商品类别之前,将商品中的商品类别属性置为空                                          
	 * @param productCategoryId
	 * @return
	 */
	int updateProductCategoryToNull(long productCategoryId);
	
	

}
