package com.lj.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lj.o2o.entity.ProductCategory;

public interface ProductCategoryDao {

	/**
	 *   查询指定某个店铺下所有商品的类别信息
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(Long shopId);
	
	/**
	 * 批量新增商品类别 
	 * @param productCategorieList
	 * @return
	 */
	int batchInsetProductCategory(List<ProductCategory> productCategoryList);
	
	/**
	 * 删除指定商品类别
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 */
	int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
	
}
