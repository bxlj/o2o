package com.lj.o2o.service.impl;

import com.lj.o2o.dao.ShopAuthMapDao;
import com.lj.o2o.dto.ShopAuthMapExecution;
import com.lj.o2o.entity.ShopAuthMap;
import com.lj.o2o.enums.ShopAuthMapEnum;
import com.lj.o2o.exception.ShopAuthMapOperationException;
import com.lj.o2o.service.ShopAuthMapService;
import com.lj.o2o.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ShopAuthMapServiceImpl implements ShopAuthMapService {

    @Autowired
    private ShopAuthMapDao shopAuthMapDao;

    @Override
    public ShopAuthMapExecution listShopAuthMapByShopId(Long shopId, Integer pageIndex, Integer pageSize) {
        //空值判断
        if (shopId != null && pageIndex != null && pageSize != null) {
            //页转行
            int beginIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
            List<ShopAuthMap> shopAuthMapList =
                    shopAuthMapDao.queryShopAuthMapListByShopId(shopId, beginIndex, pageSize);

            //返回总数
            int count = shopAuthMapDao.queryShopAuthCountByShopId(shopId);
            ShopAuthMapExecution shopAuthMapExecution = new ShopAuthMapExecution();
            shopAuthMapExecution.setShopAuthMapList(shopAuthMapList);
            shopAuthMapExecution.setCount(count);

            return shopAuthMapExecution;
        }else {
            return null;
        }

    }

    @Override
    public ShopAuthMap getShopAuthMapById(Long shopAuthId) {

        return  shopAuthMapDao.queryShopAuthMapById(shopAuthId);
    }

    @Override
    @Transactional
    public ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException {
        if (shopAuthMap != null && shopAuthMap.getShopAuthId() != null && shopAuthMap.getEmployee() != null &&
                shopAuthMap.getEmployee().getUserId() != null && shopAuthMap.getShop() != null &&
                shopAuthMap.getShop().getShopId() != null) {
            shopAuthMap.setCreateTime(new Date());
            shopAuthMap.setLastEditTime(new Date());
            shopAuthMap.setTitleFlag(0);
            shopAuthMap.setEnableStatus(1);
            try {
                int effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
                if (effectedNum <= 0){
                    throw new ShopAuthMapOperationException("添加授权失败");
                }else
                    return new ShopAuthMapExecution(ShopAuthMapEnum.SUCCESS , shopAuthMap);
            }catch (Exception e){
                throw new ShopAuthMapOperationException("添加授权失败"+e.toString());

            }
        }else {
            return new ShopAuthMapExecution(ShopAuthMapEnum.NULL_SHOPAUTH_INFO,shopAuthMap);
        }
    }

    @Override
    @Transactional
    public ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException {
        if (shopAuthMap == null && shopAuthMap.getShopAuthId() == null) {
            return new ShopAuthMapExecution(ShopAuthMapEnum.NULL_SHOPAUTH_ID);
        }else {
            try {
                int effectedNum = shopAuthMapDao.updateShopAuthMap(shopAuthMap);
                if (effectedNum <= 0){
                    return new ShopAuthMapExecution(ShopAuthMapEnum.INNER_ERROR);
                }else {
                    return new ShopAuthMapExecution(ShopAuthMapEnum.SUCCESS, shopAuthMap);
                }
            }catch (Exception e){
                throw new ShopAuthMapOperationException("modifyShopAuthMap error :" + e.toString());
            }

        }
    }
}
