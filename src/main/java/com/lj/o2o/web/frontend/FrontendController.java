package com.lj.o2o.web.frontend;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("frontend")
public class FrontendController {

	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(HttpServletRequest request) {
		return "frontend/index";
	}
	
	
	@RequestMapping(value="/shoplist",method=RequestMethod.GET)
	public String showShopList() {
		return "frontend/shoplist";
	}
	
	/**
	 * 店铺详情页路由
	 * @return
	 */
	@RequestMapping(value="/shopdetail",method=RequestMethod.GET)
	public String showShopDetail() {
		return "frontend/shopdetail";
	}
	
	/**
	 * 商品详情页路由
	 * @return
	 */
	@RequestMapping(value="/productdetail",method=RequestMethod.GET)
	public String showProductDetail() {
		return "frontend/productdetail";
	}
	
	
}
