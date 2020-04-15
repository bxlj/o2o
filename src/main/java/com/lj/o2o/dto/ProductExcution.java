package com.lj.o2o.dto;

import com.lj.o2o.entity.Product;
import com.lj.o2o.enums.ProductStateEnum;

import java.util.List;

public class ProductExcution {
	
	//结果状态
	private int state;
	//状态标识
	private String stateInfo;
	//商品数量
	private int count;
	//操作product(增删改商品时使用)
	private Product product;
	//操作product列表(查询商品时使用)
	private List<Product> productList;
	
	public ProductExcution() {
		
	}
	
	//操作失败时使用的构造器
	public ProductExcution(ProductStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	//操作成功(增删改)时使用的构造器
	public ProductExcution(ProductStateEnum stateEnum,Product product) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.product = product;
	}
	
	//操作成功(查询)时使用的构造器
	public ProductExcution(ProductStateEnum stateEnum,List<Product> productList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productList = productList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
	
	
	
}
