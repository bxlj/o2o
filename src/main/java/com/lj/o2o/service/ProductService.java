package com.lj.o2o.service;

import java.util.List;

import com.lj.o2o.dto.ImageHolder;
import com.lj.o2o.dto.ProductExcution;
import com.lj.o2o.entity.Product;
import com.lj.o2o.exception.ProductCategoryOperationException;
import com.lj.o2o.exception.ProductOperationExcetion;

public interface ProductService {

	/**
	 * 添加图片以及商品处理
	 * 
	 * @param product
	 * @param thumbnail
	 * @param productImgList
	 * @return
	 * @throws ProductOperationExcetion
	 */
	ProductExcution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductOperationExcetion;

	/**
	 * 通过productId查询指定商品信息
	 * 
	 * @param productId
	 * @return
	 */
	Product getProductById(Long productId);

	/**
	 * 修改商品信息以及图片处理
	 * 
	 * @param product        商品信息
	 * @param thumnail       缩略图
	 * @param productImgList 详情图
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductExcution modityProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductCategoryOperationException;

	/**
	 * 查询商品并显示分页：根据商品名称模糊查询,店铺id,商品状态,商品分类
	 * 
	 * @param productCondition 商品信息
	 * @param pageIndex        哪一页数据
	 * @param pageSize         每页显示的数量
	 * @return
	 */
	ProductExcution getProductList(Product productCondition, int pageIndex, int pageSize);
	
	
	

}
