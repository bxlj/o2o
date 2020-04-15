package com.lj.o2o.service;


import com.lj.o2o.dto.ImageHolder;
import com.lj.o2o.dto.ShopExecution;
import com.lj.o2o.entity.Shop;
import com.lj.o2o.exception.ShopOperationException;

public interface ShopService {
	
	/**
	  *  分页查询店铺
	 * @param shopCondition
	 * @param pageIndex  页数
	 * @param pageSize   每页展示的数据个数
	 * @return
	 */
	ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
	
	/**
	  *   通过Id获取店铺信息
	 * @param shopId
	 * @return
	 */
	Shop getByShopId(Long shopId);
	
	/**
	  *   更新店铺信息，包括图片信息
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 */
	ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
	
	/**
	  *   注册店铺信息，包括图片处理
	 * @param shop
	 * @param shopImgInputStream
	 * @param file
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException ;

}
