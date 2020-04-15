package com.lj.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lj.o2o.dao.ProductCategoryDao;
import com.lj.o2o.dao.ProductDao;
import com.lj.o2o.dto.ProductCategoryExecution;
import com.lj.o2o.entity.ProductCategory;
import com.lj.o2o.enums.ProductCategoryStateEnum;
import com.lj.o2o.exception.ProductCategoryOperationException;
import com.lj.o2o.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	
	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Autowired
	private ProductDao productDao;
	

	@Override
	public List<ProductCategory> getProductCategotyList(Long shopId) {
		return productCategoryDao.queryProductCategoryList(shopId);
	}


	@Override
	@Transactional
	public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException {
		if(productCategoryList != null && productCategoryList.size() >0) {
			try {
				int effectedNum = productCategoryDao.batchInsetProductCategory(productCategoryList);
				if(effectedNum <= 0) {
					throw new ProductCategoryOperationException("店铺类别创建失败");
				}else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
				}
			} catch (Exception e) {
				throw new ProductCategoryOperationException("addProductCategory error:"+e.getMessage());
			}
		}else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
		
	}


	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException {
		//解除tb_product里面商品与productCategoryId的关联
		//将商品类别下的商品的商品类别置为null
		try {
			int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
			if(effectedNum < 0) {
				throw new ProductCategoryOperationException("商品类别更新失败");
			}
		} catch (Exception e) {
			throw new ProductCategoryOperationException("updateProductCategoryToNull error:"+e.getMessage());
		}
		
		//删除商品类别
		int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
		try {
			if(effectedNum <= 0) {
				throw new ProductCategoryOperationException("商品类别删除失败");
			}else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		} catch (Exception e) {
			throw new ProductCategoryOperationException("deleteProductCategory error:"+e.getMessage());
		}
	}

}
