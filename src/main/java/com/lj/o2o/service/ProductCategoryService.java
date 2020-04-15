package com.lj.o2o.service;

import java.util.List;

import com.lj.o2o.dto.ProductCategoryExecution;
import com.lj.o2o.entity.ProductCategory;
import com.lj.o2o.exception.ProductCategoryOperationException;

public interface ProductCategoryService {

	/**
	 * 查询指定某个店铺下所有商品的类别信息
	 * 
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> getProductCategotyList(Long shopId);

	/**
	 * 批量新增商品类别
	 * 
	 * @param productCategoryList
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException;

	/**
	 * 将此类别下的商品里的类别id置为空,在删除掉该商品类别
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException;
	

}
