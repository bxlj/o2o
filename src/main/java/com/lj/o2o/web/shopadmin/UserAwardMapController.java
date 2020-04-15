package com.lj.o2o.web.shopadmin;

import com.lj.o2o.dto.UserAwardMapExecution;
import com.lj.o2o.entity.Award;
import com.lj.o2o.entity.Shop;
import com.lj.o2o.entity.UserAwardMap;
import com.lj.o2o.service.UserAwardMapService;
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
public class UserAwardMapController {

    @Autowired
    private UserAwardMapService userAwardMapService;

    /**
     * 列出某个店铺的用户奖品领取情况列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listuserawardmapsbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listUserAwardMapsByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //获取分页信息
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        // 从session里获取店铺信息
        Shop currentShop =
                (Shop) request.getSession().getAttribute("currentShop");
        //空值判断
        if (currentShop != null && currentShop.getShopId() != null && pageIndex > -1 && pageSize > -1) {
            UserAwardMap userAwardMap = new UserAwardMap();
            //根据查询条件查询
            userAwardMap.setShop(currentShop);
            String awardName = HttpServletRequestUtil.getString(request, "awardName");
            if (awardName != null) {
                Award award = new Award();
                award.setAwardName(awardName);
                userAwardMap.setAward(award);
            }
            UserAwardMapExecution ue = userAwardMapService.listUserAwardMap(userAwardMap, pageIndex, pageSize);
            modelMap.put("userAwardMapList", ue.getUserAwardMapList());
            modelMap.put("count", ue.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg","empty pageIndex or pageSize or shopId");
        }
        return modelMap;
    }

}
