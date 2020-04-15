package com.lj.o2o.web.shopadmin;

import com.lj.o2o.dto.EchartSeries;
import com.lj.o2o.dto.EchartXAxis;
import com.lj.o2o.dto.UserProductMapExecution;
import com.lj.o2o.entity.Product;
import com.lj.o2o.entity.ProductSellDaily;
import com.lj.o2o.entity.Shop;
import com.lj.o2o.entity.UserProductMap;
import com.lj.o2o.service.ProductSellDailyService;
import com.lj.o2o.service.UserProductMapService;
import com.lj.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/shopadmin")
public class UserProductManagementController {

    @Autowired
    private UserProductMapService userProductMapService;
    @Autowired
    private ProductSellDailyService productSellDailyService;

    @RequestMapping(value = "/listuserproductmapbyshop",method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listUserProductMapByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int beginIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        //获取当前店铺信息
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        //空值校验
        if (pageSize > -1 && beginIndex > -1 && currentShop != null && currentShop.getShopId() != null) {
            UserProductMap userProductMap = new UserProductMap();
            userProductMap.setShop(currentShop);
            String productName = HttpServletRequestUtil.getString(request, "productName");
            if (productName != null) {
                //前端若按商品名称模糊查询，传入productName
                Product product = new Product();
                product.setProductName(productName);
                userProductMap.setProduct(product);
            }
            //根据传入的条件获取店铺的销量情况
            UserProductMapExecution ue = userProductMapService.listUserProductMap(userProductMap, beginIndex, pageSize);

            modelMap.put("userProductList",ue.getProductMapList());
            modelMap.put("count", ue.getCount());
            modelMap.put("success", true);
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/listproductselldailyinfobyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listProductSellDailyInfoByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (currentShop != null && currentShop.getShopId() != null) {
            //添加查询条件
            ProductSellDaily productSellDailyCondition  = new ProductSellDaily();
            productSellDailyCondition .setShop(currentShop);
            Calendar calendar = Calendar.getInstance();
            //获取昨天日期
            calendar.add(Calendar.DATE, -1);
            Date endTime = calendar.getTime();
            //获取七天前日期
            calendar.add(Calendar.DATE, -6);
            Date beginTime = calendar.getTime();
            List<ProductSellDaily> productSellDailyList =
                    productSellDailyService.listProductSellDaily(productSellDailyCondition , beginTime, endTime);
            //指定日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //商品列表名
            HashSet<String> legendData = new HashSet<>();
            //x轴数据
            HashSet<String> xData = new HashSet<>();
            //定义series
            List<EchartSeries> series = new ArrayList<>();
            //日销量列表
            List<Integer> totalList = new ArrayList<>();
            //当前商品名称默认为空
            String currentProductName = "";
            for (int i = 0; i < productSellDailyList.size(); i++) {
                ProductSellDaily productSellDaily = productSellDailyList.get(i);
                //自动去重
                legendData.add(productSellDaily.getProduct().getProductName());
                xData.add(sdf.format(productSellDaily.getCreateTime()));
                if (!currentProductName.equals(productSellDaily.getProduct().getProductName()) &&
                        !currentProductName.isEmpty()) {
                    // 如果currentProductName不等于获取的商品名，或者已遍历到列表的末尾，且currentProductName不为空，
                    // 则是遍历到下一个商品的日销量信息了, 将前一轮遍历的信息放入series当中，
                    // 包括了商品名以及与商品对应的统计日期以及当日销量
                    EchartSeries es = new EchartSeries();
                    es.setName(currentProductName);
                    es.setData(totalList.subList(0, totalList.size()));
                    series.add(es);

                    totalList = new ArrayList<>();
                    // 变换下currentProductId为当前的productId
                    currentProductName = productSellDaily.getProduct().getProductName();
                    // 继续添加新的值
                    totalList.add(productSellDaily.getTotal());
                } else {
                    totalList.add(productSellDaily.getTotal());
                    currentProductName = productSellDaily.getProduct().getProductName();
                }
                // 队列之末，需要将最后的一个商品销量信息也添加上
                if (i == productSellDailyList.size() - 1) {
                    EchartSeries es = new EchartSeries();
                    es.setName(currentProductName);
                    es.setData(totalList.subList(0, totalList.size()));
                    series.add(es);
                }
            }
            modelMap.put("series", series);
            modelMap.put("legendData", legendData);
            // 拼接出xAxis
            List<EchartXAxis> xAxis = new ArrayList<>();
            EchartXAxis exa = new EchartXAxis();
            exa.setData(xData);
            xAxis.add(exa);
            modelMap.put("xAxis", xAxis);
            modelMap.put("success", true);

        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }

        return modelMap;
    }

}
