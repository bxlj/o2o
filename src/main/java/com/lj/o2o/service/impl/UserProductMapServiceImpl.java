package com.lj.o2o.service.impl;

import com.lj.o2o.dao.UserProductMapDao;
import com.lj.o2o.dto.UserProductMapExecution;
import com.lj.o2o.entity.UserProductMap;
import com.lj.o2o.service.UserProductMapService;
import com.lj.o2o.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProductMapServiceImpl implements UserProductMapService {


    @Autowired
    private UserProductMapDao userProductMapDao;

    @Override
    public UserProductMapExecution listUserProductMap(UserProductMap userProductMap, Integer beginIndex, Integer pageSize) {

        //空值判断
        if (userProductMap != null && beginIndex != null && pageSize != null) {
            int rowIndex = PageCalculator.calculateRowIndex(beginIndex, pageSize);
            List<UserProductMap> productMapList =
                    userProductMapDao.queryUserProductMapList(userProductMap, rowIndex, pageSize);
            int count = userProductMapDao.queryUserProductMapCount(userProductMap);
            UserProductMapExecution se = new UserProductMapExecution();
            se.setProductMapList(productMapList);
            se.setCount(count);

            return se;
        }else {
            return null;
        }
    }
}
