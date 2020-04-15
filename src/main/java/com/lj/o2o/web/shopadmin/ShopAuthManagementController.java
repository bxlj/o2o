package com.lj.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.lj.o2o.dto.ShopAuthMapExecution;
import com.lj.o2o.dto.UserAccessToken;
import com.lj.o2o.dto.WechatInfo;
import com.lj.o2o.entity.PersonInfo;
import com.lj.o2o.entity.Shop;
import com.lj.o2o.entity.ShopAuthMap;
import com.lj.o2o.entity.WeChatAuth;
import com.lj.o2o.enums.ShopAuthMapEnum;
import com.lj.o2o.service.PersonInfoService;
import com.lj.o2o.service.ShopAuthMapService;
import com.lj.o2o.service.WechatAuthService;
import com.lj.o2o.util.CodeUtil;
import com.lj.o2o.util.HttpServletRequestUtil;
import com.lj.o2o.util.ShortNetAddressUtil;
import com.lj.o2o.util.wechat.WechatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/shopadmin")
public class ShopAuthManagementController {

    @Autowired
    private ShopAuthMapService shopAuthMapService;

    @Autowired
    private WechatAuthService wechatAuthService;

    @Autowired
    private PersonInfoService personInfoService;

    @RequestMapping(value = "/listshopauthmapsbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopAuthMapsByShop(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");

        if (currentShop != null && currentShop.getShopId() != null && pageIndex > -1 &&
                pageSize > -1) {
            //// 分页取出该店铺下面的授权信息列表
            ShopAuthMapExecution se = shopAuthMapService.listShopAuthMapByShopId(currentShop.getShopId(), pageIndex, pageSize);

            map.put("shopAuthMapList", se.getShopAuthMapList());
            map.put("count", se.getCount());
            map.put("success", true);
        } else {
            map.put("success", false);
            map.put("errMsg", "empty pageSize or pageIndex or shopId");
        }

        return map;
    }

    @RequestMapping(value = "/getshopauthmapbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopAuthMapById(@RequestParam Long shopAuthId) {
        Map<String, Object> map = new HashMap<>();

        if (shopAuthId != null && shopAuthId > -1) {
            ShopAuthMap shopAuthMap = shopAuthMapService.getShopAuthMapById(shopAuthId);
            map.put("shopAuthMap", shopAuthMap);
            map.put("success", true);
        } else {
            map.put("success", false);
            map.put("errMsg", "shopAuthId is empty");
        }
        return map;
    }

    @RequestMapping(value = "/modifyshopauthmap", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShopAuthMap(String shopAuthMapStr, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 是授权编辑时候调用还是删除/恢复授权操作的时候调用
        // 若为前者则进行验证码判断，后者则跳过验证码判断
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码输入错误");
            return modelMap;
        }

        ObjectMapper mapper = new ObjectMapper();
        ShopAuthMap shopAuthMap = null;
        try {
            // 将前台传入的字符串json转换成shopAuthMap实例
            shopAuthMap = mapper.readValue(shopAuthMapStr, ShopAuthMap.class);

        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        //空值判断
        if (shopAuthMap != null && shopAuthMap.getShopAuthId() != null) {
            try {
                if (!checkPermission(shopAuthMap.getShopAuthId())){
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "无法对店家本身权限做操作(已是店铺的最高权限)");
                    return modelMap;
                }
                ShopAuthMapExecution se = shopAuthMapService.modifyShopAuthMap(shopAuthMap);
                if (se.getState() == ShopAuthMapEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);

                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }

            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入要修改的授权信息");
        }
        return modelMap;
    }

    /**
     * 检查被操作的对象是否可修改
     * @param shopAuthId
     * @return
     */
    private boolean checkPermission(Long shopAuthId) {
        ShopAuthMap shopAuthMap = shopAuthMapService.getShopAuthMapById(shopAuthId);
        if (shopAuthMap.getTitleFlag() == 0) {
            return false;
        }else {
            return true;
        }
    }


    /**
     * 二维码的创建
     */
    // 微信获取用户信息的api前缀
    private static String urlPrefix;
    // 微信获取用户信息的api中间
    private static String urlMiddle;
    // 微信获取用户信息的api后缀
    private static String urlSuffix;
    // 微信回传给的响应添加授权信息的url
    private static String authUrl;

    @Value("${wechat.prefix}")
    public void setUrlPrefix(String urlPrefix) {
        ShopAuthManagementController.urlPrefix = urlPrefix;
    }

    @Value("${wechat.middle}")
    public void setUrlMiddle(String urlMiddle) {
        ShopAuthManagementController.urlMiddle = urlMiddle;
    }

    @Value("${wechat.suffix}")
    public void setUrlSuffix(String urlSuffix) {
        ShopAuthManagementController.urlSuffix = urlSuffix;
    }

    @Value("${wechat.auth.url}")
    public void setAuthUrl(String authUrl) {
        ShopAuthManagementController.authUrl = authUrl;
    }

    /**
     * 生成带有URL的二维码，微信扫一扫就能链接到对应的URL里面
     * @param request
     * @param response
     */
    @RequestMapping(value = "/generateqrcode4shopauth", method = RequestMethod.GET)
    @ResponseBody
    private void generateQRCode4ShopAuth(HttpServletRequest request, HttpServletResponse response){
        // 从session里获取当前shop的信息
        Shop shop = (Shop) request.getSession().getAttribute("currentShop");
        if (shop != null && shop.getShopId() != null) {
            // 获取当前时间戳，以保证二维码的时间有效性，精确到毫秒
            long timeStamp = System.currentTimeMillis();
            // 将店铺id和timestamp传入content，赋值到state中，这样微信获取到这些信息后会回传到授权信息的添加方法里
            // 加上aaa是为了一会的在添加信息的方法里替换这些信息使用
            String content = "{aaashopIdaaa:" + shop.getShopId() + ",aaacreateTimeaaa:" + timeStamp + "}";
            try {
                // 将content的信息先进行base64编码以避免特殊字符造成的干扰，之后拼接目标URL
                String mmm = URLEncoder.encode(content, "UTF-8");
                System.out.println(mmm+"你好");
                String longUrl = urlPrefix + authUrl + urlMiddle + URLEncoder.encode(content, "UTF-8") + urlSuffix;
                System.out.println(longUrl);
                // 将目标URL转换成短的URL
                String shortURL = ShortNetAddressUtil.getShortURL(longUrl);
                // 调用二维码生成的工具类方法，传入短的URL，生成二维码
                BitMatrix bitMatrix = CodeUtil.generateQRCodeStream(shortURL, response);
                // 将二维码以图片流的形式输出到前端
                MatrixToImageWriter.writeToStream(bitMatrix,"png",response.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据微信回传回来的参数添加店铺的授权信息
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/addshopauthmap", method = RequestMethod.GET)
    private String addShopAuthMap(HttpServletRequest request,HttpServletResponse response) throws IOException{
        // 从request里面获取微信用户的信息
        WeChatAuth auth = getEmployeeInfo(request);
        if (auth != null) {
            // 根据userId获取用户信息
            PersonInfo user = personInfoService.getPersonInfoById(auth.getPersonInfo().getUserId());
            // 将用户信息添加进user里
            request.getSession().setAttribute("user", user);
            // 解析微信回传过来的自定义参数state,由于之前进行了编码，这里需要解码一下
            String qrCodeinfo = new String(
                    URLDecoder.decode(HttpServletRequestUtil.getString(request, "state"), "UTF-8"));
            ObjectMapper mapper = new ObjectMapper();
            WechatInfo wechatInfo = null;
            try {
                // 将解码后的内容用aaa去替换掉之前生成二维码的时候加入的aaa前缀，转换成WechatInfo实体类
                wechatInfo = mapper.readValue(qrCodeinfo.replace("aaa", "\""), WechatInfo.class);
            } catch (Exception e) {
                return "shop/operationfail";
            }
            // 校验二维码是否已经过期
            if (!checkQRCodeInfo(wechatInfo)) {
                return "shop/operationfail";
            }

            // 去重校验
            // 获取该店铺下所有的授权信息
            ShopAuthMapExecution allMapList = shopAuthMapService.listShopAuthMapByShopId(wechatInfo.getShopId(), 1, 999);
            List<ShopAuthMap> shopAuthList = allMapList.getShopAuthMapList();
            for (ShopAuthMap sm : shopAuthList) {
                if (sm.getEmployee().getUserId() == user.getUserId())
                    return "shop/operationfail";
            }

            try {
                // 根据获取到的内容，添加微信授权信息
                ShopAuthMap shopAuthMap = new ShopAuthMap();
                Shop shop = new Shop();
                shop.setShopId(wechatInfo.getShopId());
                shopAuthMap.setShop(shop);
                shopAuthMap.setEmployee(user);
                shopAuthMap.setTitle("员工");
                shopAuthMap.setTitleFlag(1);
                ShopAuthMapExecution se = shopAuthMapService.addShopAuthMap(shopAuthMap);
                if (se.getState() == ShopAuthMapEnum.SUCCESS.getState()) {
                    return "shop/operationsuccess";
                } else {
                    return "shop/operationfail";
                }
            } catch (RuntimeException e) {
                return "shop/operationfail";
            }
        }
        return "shop/operationfail";
    }

    /**
     * 根据二维码携带的createTime判断其是否超过了10分钟，超过十分钟则认为过期
     *
     * @param wechatInfo
     * @return
     */
    private boolean checkQRCodeInfo(WechatInfo wechatInfo) {
        //空值判断
        if (wechatInfo != null && wechatInfo.getShopId() != null && wechatInfo.getCreateTime() != null) {
            long nowTime = System.currentTimeMillis();
            if ((nowTime - wechatInfo.getCreateTime()) <= 600000) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 根据微信回传的code获取用户信息
     * @param request
     * @return
     */
    private WeChatAuth getEmployeeInfo(HttpServletRequest request) {
        String code = request.getParameter("code");
        WeChatAuth auth = null;
        if (code != null) {
            UserAccessToken token;
            try {
                token = WechatUtil.getUserAccessToken(code);
                String openId = token.getOpenId();
                request.getSession().setAttribute("openId",openId);
                auth = wechatAuthService.getWechatAuthByOpenId(openId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return auth;
    }

}
