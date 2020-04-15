package com.lj.o2o.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lj.o2o.entity.Product;
import com.lj.o2o.entity.ProductImg;
import com.lj.o2o.service.ProductService;
import com.lj.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ProductDetailController {

	@Autowired
	private ProductService productService;
	
	/**
	 * 根据id获取商品详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/listproductdetailpageinfo",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> listProductDetailPageInfo(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<>();
		//获取从前台传递过来的productId
		long productId = HttpServletRequestUtil.getLong(request, "productId");
		Product product = null;
		if(productId != -1) {
			product = productService.getProductById(productId);
			List<ProductImg> list = product.getProductImgList();
			for (ProductImg productImg : list) {
				System.out.println(productImg.getImgAddr());
			}
			modelMap.put("product", product);
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty productId");
		}
		return modelMap;
		
	}
	
}
