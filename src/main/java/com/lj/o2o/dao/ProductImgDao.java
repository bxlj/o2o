package com.lj.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lj.o2o.entity.ProductImg;

public interface ProductImgDao {

	/**
     *   批量添加商品详情图片
	 * @param productImg
	 * @return
	 */
	int batchInsertProductImg(@Param("list") List<ProductImg> productImg);
	
	/**
	 *   删除指定商品下的所有详情图
	 * @param productId
	 * @return
	 */
	int deleteProductImgByProductId(long productId);
	
	/**
	 *   查找指定商品下的商品详情图
	 * @param productId
	 * @return
	 */
	List<ProductImg> queryProductImgList(Long productId);
}
