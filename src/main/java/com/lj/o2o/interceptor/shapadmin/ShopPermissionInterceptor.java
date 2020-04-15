package com.lj.o2o.interceptor.shapadmin;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lj.o2o.entity.Shop;
/**
 * 店家管理系统操作验证拦截器
 * @author 老贾
 *
 */
public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException {
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		@SuppressWarnings("unchecked")
		List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
		//非空判断
		if(currentShop != null && shopList != null) {
			//遍历可以操作的店铺
			for(Shop shop:shopList) {
				if(shop.getShopId() == currentShop.getShopId()) {
					return true;
				}
			}
		}
		return false;
	}

}
