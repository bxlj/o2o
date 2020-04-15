package com.lj.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lj.o2o.dao.ProductDao;
import com.lj.o2o.dao.ProductImgDao;
import com.lj.o2o.dto.ImageHolder;
import com.lj.o2o.dto.ProductExcution;
import com.lj.o2o.entity.Product;
import com.lj.o2o.entity.ProductImg;
import com.lj.o2o.enums.ProductStateEnum;
import com.lj.o2o.exception.ProductCategoryOperationException;
import com.lj.o2o.exception.ProductOperationExcetion;
import com.lj.o2o.service.ProductService;
import com.lj.o2o.util.ImageUtil;
import com.lj.o2o.util.PageCalculator;
import com.lj.o2o.util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductImgDao productImgDao;
	@Autowired
	private ProductDao productDao;

	@Override
	@Transactional
	// 1.处理缩略图,获取缩略图的相对路径并赋值给product
	// 2.给tb_product中写入图片信息,获取productId
	// 3.结合productId批量处理商品详情图
	// 4.将商品详情图列表批量插入tb_product_img
	public ProductExcution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
			throws ProductOperationExcetion {
		// 空值判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// 设置商品默认属性
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			// 默认商品上架
			product.setEnableStatus(1);
			// 商品缩略图不为空则添加
			if (thumbnail != null) {
				addThumbnail(product, thumbnail);
			}
			try {
				// 创建商品信息
				int effectedNum = productDao.insertProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationExcetion("创建商品失败");
				}
			} catch (Exception e) {
				throw new ProductOperationExcetion("创建商品失败信息" + e.toString());
			}
			// 商品详情图不为空则进行添加
			if (productImgHolderList != null && productImgHolderList.size() > 0) {
				addProductImgList(product, productImgHolderList);
			}
			return new ProductExcution(ProductStateEnum.SUCCESS, product);
		} else {
			// 传入参数为空返回错误信息
			return new ProductExcution(ProductStateEnum.EMPTY);
		}
	}

	/**
	 * 添加缩略图
	 * 
	 * @param product
	 * @param thumbnail
	 */
	private static void addThumbnail(Product product, ImageHolder thumbnail) {
		// 获取项目图片子路径
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		System.out.println("ProductServiceImpl dest:" + dest);
		// 返回新生成图片的相对路径值
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		product.setImgAddr(thumbnailAddr);
	}

	/**
	 * 批量添加商品详情图片
	 * 
	 * @param product
	 * @param productImgHolderList
	 */
	private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
		// 获取图片的存储路径，存放在相应店铺文件夹下
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		for (ImageHolder productImgHolder : productImgHolderList) {
			String imgAddr = ImageUtil.generateNormalImg(productImgHolder, dest);
			ProductImg productImg = new ProductImg();
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
		}
		// 判断是否有详情图需要添加，进行批量添加
		if (productImgList.size() > 0) {
			try {
				int effectedNum = productImgDao.batchInsertProductImg(productImgList);
				if (effectedNum <= 0) {
					throw new ProductOperationExcetion("创建商品详情图失败");
				}
			} catch (Exception e) {
				throw new ProductOperationExcetion("创建商品详情图失败:" + e.getMessage());
			}
		}
	}

	@Override
	// 通过Id查询商品信息
	public Product getProductById(Long productId) {
		return productDao.queryProductById(productId);
	}

	@Override
	@Transactional
	// 1.若缩略图参数有值,先处理缩略图
	// 若原先存在缩略图则先删除在添加缩略图,之后获取到缩略图的相对路径并赋值给product
	// 2.若详情图参数有值,则对商品详情图进行同样的操作
	// 3.将tb_product_img下的该商品详情图全部删除
	// 4.更新tb_product_img,tb_procduct表
	public ProductExcution modityProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductCategoryOperationException {
		// 控制判断
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			// 设置商品默认属性值
			product.setLastEditTime(new Date());
			// 判断图片缩略图是否为空
			if (thumbnail != null) {
				// 获取商品原有信息,商品原有信息中有图片相对路径
				Product tempProduct = productDao.queryProductById(product.getProductId());
				if (tempProduct.getImgAddr() != null) {
					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				addThumbnail(product, thumbnail);
			}
			// 如果有新存入的商品详情图,则将原商品详情图删除,并添加新图片
			if (productImgList != null && productImgList.size() > 0) {
				// 删除指定商品详情图
				deleteProductImgList(product.getProductId());
				// 添加商品详情图
				addProductImgList(product, productImgList);
			}
			try {
				// 更新商品信息
				int effectedNum = productDao.updateProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperationExcetion("更新商品信息失败");
				}
				return new ProductExcution(ProductStateEnum.SUCCESS, product);
			} catch (Exception e) {
				throw new ProductOperationExcetion("更新商品信息失败:" + e.toString());
			}

		} else {
			return new ProductExcution(ProductStateEnum.EMPTY);
		}

	}

	/**
	 * 删除某个商品下的详情图
	 * 
	 * @param productId
	 */
	private void deleteProductImgList(Long productId) {
		// 根据productId获取指定商品详情图
		List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
		// 干掉原来的图片
		for (ProductImg productImg : productImgList) {
			ImageUtil.deleteFileOrPath(productImg.getImgAddr());
		}
		// 删除数据库中的图片
		productImgDao.deleteProductImgByProductId(productId);
	}

	@Override
	public ProductExcution getProductList(Product productCondition, int pageIndex, int pageSize) {
		// 将页面传递过来的页码转换为数据库行码
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		// 基于同样的商品信息,查询商品数量
		int count = productDao.queryProductCount(productCondition);
		ProductExcution pe = new ProductExcution();
		pe.setProductList(productList);
		pe.setCount(count);
		return pe;
	}

}
