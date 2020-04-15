package com.lj.o2o.service;

import com.lj.o2o.entity.ProductSellDaily;

import java.util.Date;
import java.util.List;

public interface ProductSellDailyService {

    /**
     * 每日对店铺商品销售进行统计
     */
    void dailyCalculate();

    /**
     *根据查询条件返回商品日销售统计列表
     * @param productSellDaily
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ProductSellDaily> listProductSellDaily(ProductSellDaily productSellDaily, Date beginTime,
                                                Date endTime);


}
