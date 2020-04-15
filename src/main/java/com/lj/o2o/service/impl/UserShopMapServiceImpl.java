package com.lj.o2o.service.impl;

import com.lj.o2o.dao.UserShopMapDao;
import com.lj.o2o.dto.UserShopMapExecution;
import com.lj.o2o.entity.UserShopMap;
import com.lj.o2o.service.UserShopMapService;
import com.lj.o2o.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserShopMapServiceImpl implements UserShopMapService {

    @Autowired
    private UserShopMapDao userShopMapDao;


    @Override
    public UserShopMapExecution listUserShopMap(UserShopMap userShopMap,
                                                Integer beginIndex, Integer pageIndex) {
        int rowIndex = PageCalculator.calculateRowIndex(beginIndex, pageIndex);
        if (userShopMap != null && rowIndex > -1 && pageIndex > -1) {
            List<UserShopMap> userShopMaps =
                    userShopMapDao.queryUserShopMapList(userShopMap, rowIndex, pageIndex);

            int count = userShopMapDao.queryUserShopMapCount(userShopMap);
            UserShopMapExecution ue = new UserShopMapExecution();
            ue.setCount(count);
            ue.setUserShopMapList(userShopMaps);
            return ue;
        } else {
            return null;
        }

    }
}
