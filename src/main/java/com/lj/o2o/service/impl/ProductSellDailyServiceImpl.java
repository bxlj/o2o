package com.lj.o2o.service.impl;

import com.lj.o2o.dao.ProductSellDailyDao;
import com.lj.o2o.entity.ProductSellDaily;
import com.lj.o2o.service.ProductSellDailyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductSellDailyServiceImpl implements ProductSellDailyService {

    private static final Logger log = LoggerFactory.getLogger(ProductSellDailyServiceImpl.class);

    @Autowired
    private ProductSellDailyDao productSellDailyDao;

    @Override
    public void dailyCalculate() {
        log.info("Quartz Running");
        productSellDailyDao.insertProductSellDaily();
        productSellDailyDao.insertDefaultProductSellDaily();
        //System.out.println("Quartz跑起来了");
    }

    @Override
    public List<ProductSellDaily> listProductSellDaily(ProductSellDaily productSellDaily, Date beginTime, Date endTime) {
        return productSellDailyDao.queryProductSellDailyList(productSellDaily,beginTime,endTime);
    }
}
