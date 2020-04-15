package com.lj.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lj.o2o.dto.AwardExecution;
import com.lj.o2o.dto.ImageHolder;
import com.lj.o2o.entity.Award;
import com.lj.o2o.entity.Shop;
import com.lj.o2o.enums.AwardStateEnum;
import com.lj.o2o.service.AwardService;
import com.lj.o2o.util.CodeUtil;
import com.lj.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class AwardController {

    @Autowired
    private AwardService awardService;

    /**
     * 获取店铺下奖品列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listawardsbyshop", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listAwardsByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //获取店铺信息
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //空值判断
        if (currentShop != null && currentShop.getShopId() != null && pageIndex > -1 && pageSize > -1) {
            String awardName = HttpServletRequestUtil.getString(request, "awardName");
            Award award = compactAwardCondition4Search(currentShop.getShopId(), awardName);
            AwardExecution ae = awardService.getAwardList(award, pageIndex, pageSize);
            modelMap.put("awardList", ae.getAwardList());
            modelMap.put("count", ae.getCount());
            modelMap.put("success", true);
        }else {
            modelMap.put("errMsg","empty pageSize or pageIndex or shopId");
            modelMap.put("success", false);
        }
        return modelMap;
    }

    /**
     * 修改奖品信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyaward", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyAward(HttpServletRequest request) {
//        Map<String, Object> modelMap = new HashMap<>();
//        // 是商品编辑时调用还是商品上下架时调用
//        // 若为前者则需要进行验证码判断
//        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
//        // 根据传入的状态值决定是否跳过验证码校验
//        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
//            modelMap.put("success", false);
//            modelMap.put("errMsg", "输入了错误的验证码");
//            return modelMap;
//        }
//
//        //接收前端参数的变量的初始化，包括商品，缩略图
//        ObjectMapper mapper = new ObjectMapper();
//        Award award = null;
//        ImageHolder thumbnail = null;
//        CommonsMultipartResolver commonsMultipartResolver =
//                new CommonsMultipartResolver(request.getSession().getServletContext());
//        try {
//            if (commonsMultipartResolver.isMultipart(request)) {
//                thumbnail = handleImage(request, thumbnail);
//            }
//        } catch (Exception e) {
//            modelMap.put("success", false);
//            modelMap.put("errMsg", e.toString());
//            return modelMap;
//        }
//
//        String awardStr = HttpServletRequestUtil.getString(request, "awardStr");
//        try {
//            award = mapper.readValue(awardStr, Award.class);
//        } catch (IOException e) {
//            modelMap.put("success", false);
//            modelMap.put("errMsg", e.toString());
//            return modelMap;
//        }
//
//        if (award != null) {
//            try {
//                // 从session中获取当前店铺的Id并赋值给award，减少对前端数据的依赖
//                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
//                award.setShopId(currentShop.getShopId());
//                AwardExecution pe = awardService.modifyAward(award, thumbnail);
//                if (pe.getState() == AwardStateEnum.SUCCESS.getState()) {
//                    modelMap.put("success", true);
//                } else {
//                    modelMap.put("success", false);
//                    modelMap.put("errMsg", pe.getStateInfo());
//                }
//            } catch (Exception e) {
//                modelMap.put("success", false);
//                modelMap.put("errMsg", e.toString());
//                return modelMap;
//            }
//
//        } else {
//            modelMap.put("success", false);
//            modelMap.put("errMsg", "请输入商品信息");
//        }
//        return modelMap;

        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 根据传入的状态值决定是否跳过验证码校验
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        // 接收前端参数的变量的初始化，包括商品，缩略图
        ObjectMapper mapper = new ObjectMapper();
        Award award = null;
        ImageHolder thumbnail = null;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 咱们的请求中都带有multi字样，因此没法过滤，只是用来拦截外部非图片流的处理，
        // 里边有缩略图的空值判断，请放心使用
        try {
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        try {
            String awardStr = HttpServletRequestUtil.getString(request, "awardStr");
            // 尝试获取前端传过来的表单string流并将其转换成Product实体类
            award = mapper.readValue(awardStr, Award.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        // 空值判断
        if (award != null) {
            try {
                // 从session中获取当前店铺的Id并赋值给award，减少对前端数据的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                award.setShopId(currentShop.getShopId());
                AwardExecution pe = awardService.modifyAward(award, thumbnail);
                if (pe.getState() == AwardStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    /**
     * 图片预处理
     * @param request
     * @param thumbnail
     * @return
     * @throws IOException
     */
    private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail) throws IOException {
        MultipartHttpServletRequest thumbnailRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) thumbnailRequest.getFile("thumbnail");
        if (thumbnailFile != null) {
            thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
        }
        return thumbnail;
    }

    /**
     * 封装商品查询条件到award实例中
     * @param shopId
     * @param awardName
     * @return
     */
    private static Award compactAwardCondition4Search(Long shopId, String awardName) {
        Award award = new Award();
        award.setShopId(shopId);
        if (awardName != null) {
            award.setAwardName(awardName);
        }
        return award;
    }


}
