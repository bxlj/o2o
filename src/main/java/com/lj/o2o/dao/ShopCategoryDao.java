package com.lj.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lj.o2o.entity.ShopCategory;

public interface ShopCategoryDao {

	/**
	 * 根据传入的查询条件获取ShopCategory列表
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}
