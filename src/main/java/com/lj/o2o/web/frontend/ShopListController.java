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

import com.lj.o2o.dto.ShopExecution;
import com.lj.o2o.entity.Area;
import com.lj.o2o.entity.Shop;
import com.lj.o2o.entity.ShopCategory;
import com.lj.o2o.service.AreaService;
import com.lj.o2o.service.ShopCategoryService;
import com.lj.o2o.service.ShopService;
import com.lj.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopListController {

	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private AreaService areaService;
	
	/**
	 * 返回商品列表页里面的shopCategory列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="listshopspageinfo",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> listShopsPageInfo(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		//从前端请求中获取parentId
		long parentId = HttpServletRequestUtil.getLong(request, "parentId");
		List<ShopCategory> shopCategoryList = null;
		if(parentId != -1) {
			//如果parentId存在则取出一级列表下的二级列表
			try {
				ShopCategory shopCategoryCondition = new ShopCategory();
				ShopCategory parent = new ShopCategory();
				parent.setShopCategoryId(parentId);
				shopCategoryCondition.setParent(parent);
			    shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}else {
			try {
				//parentId不存在,则取出所有一级ShopCategory(选择的是全部商店列表)
				shopCategoryList = shopCategoryService.getShopCategoryList(null);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}
		modelMap.put("shopCategoryList", shopCategoryList);
		List<Area> areaList = null;
		try {
			//获取区域列表信息
			areaList = areaService.getAreaList();
			modelMap.put("success" , true);
			modelMap.put("areaList", areaList);
			return modelMap;
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	
	
	/**
	 * 获取指定查询条件下的店铺列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/listshops")
	@ResponseBody
	private Map<String,Object> listShops(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<>();
		//获取页码
		int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
		//获取一页需要显示的数据条数
		int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
		//非空判断
		if((pageIndex > -1) && (pageSize > -1)) {
			//获取一级类别的Id
			long parentId = HttpServletRequestUtil.getLong(request, "parentId");
			//获取特定二级类别id
			long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
			//获取区域Id
			int areaId = HttpServletRequestUtil.getInt(request, "areaId");
			//获取模糊查询名字
			String shopName = HttpServletRequestUtil.getString(request, "shopName");
			//获取组合之后的查询名字
			Shop shopCondition = compactShopCondition4Search(parentId,shopCategoryId,areaId,shopName);
			//根据查询条件和分页信息获取店铺列表,返回总数
			ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
			modelMap.put("shopList", se.getShopList());
			modelMap.put("count", se.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex");
		}
		return modelMap;
	}


	/**
	 * 组合查询条件,并将条件封装到ShopCondition对象里返回
	 * @param parentId
	 * @param shopCategoryId
	 * @param areaId
	 * @param shopName
	 * @return
	 */
	private Shop compactShopCondition4Search(long parentId, long shopCategoryId, int areaId, String shopName) {
		Shop shopCondition = new Shop();
		if(parentId != -1) {
			//查询某个一级ShopCategory下面的所有二级ShopCategory里面的店铺列表
			ShopCategory parentCategory = new ShopCategory();
			ShopCategory childCategory = new ShopCategory();
			parentCategory.setShopCategoryId(parentId);
			childCategory.setParent(parentCategory);
			shopCondition.setShopCategroy(childCategory);
		}
		if(shopCategoryId != -1) {
			//查询某个二级ShopCategory下面的店铺列表
			ShopCategory shopCategory = new ShopCategory();
			shopCategory.setShopCategoryId(shopCategoryId);
			shopCondition.setShopCategroy(shopCategory);
		}
		if(areaId != -1) {
			//查询位于某个区域下的店铺列表
			Area area = new Area();
			area.setAreaId(areaId);
			shopCondition.setArea(area);
		}
		if(shopName != null) {
			//查询名字里面包含shopName的店铺列表
			shopCondition.setShopName(shopName);
		}
		//前端展示的店铺都是审核成功的店铺
		shopCondition.setEnableStatus(1);
		return shopCondition;
	}
}
