package com.lj.o2o.web.shopadmin;

import com.lj.o2o.dto.UserShopMapExecution;
import com.lj.o2o.entity.PersonInfo;
import com.lj.o2o.entity.Shop;
import com.lj.o2o.entity.UserShopMap;
import com.lj.o2o.service.UserShopMapService;
import com.lj.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class UserShopMapController {

    @Autowired
    private UserShopMapService userShopMapService;

    @RequestMapping(value = "/listusershopmapbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listUserShopMapByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        Shop currentShop =
                (Shop) request.getSession().getAttribute("currentShop");
        if (currentShop != null && currentShop.getShopId() != null &&
                pageIndex > -1 && pageSize > -1) {
            //传入查询条件
            UserShopMap userShopMap = new UserShopMap();
            userShopMap.setShop(currentShop);
            //根据顾客名字模糊查询
            String userName = HttpServletRequestUtil.getString(request, "userName");
            if (userName != null) {
                PersonInfo personInfo = new PersonInfo();
                personInfo.setName(userName);
                userShopMap.setUser(personInfo);
            }
            UserShopMapExecution ue = userShopMapService.listUserShopMap(userShopMap, pageIndex, pageSize);

            modelMap.put("userShopMapList", ue.getUserShopMapList());
            modelMap.put("count", ue.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg","empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

}
