package com.lj.o2o.service.impl;

import com.lj.o2o.dao.UserAwardMapDao;
import com.lj.o2o.dto.UserAwardMapExecution;
import com.lj.o2o.entity.UserAwardMap;
import com.lj.o2o.service.UserAwardMapService;
import com.lj.o2o.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAwardMapServiceImpl implements UserAwardMapService {

    @Autowired
    private UserAwardMapDao userAwardMapDao;

    @Override
    public UserAwardMapExecution listUserAwardMap(UserAwardMap userAwardMapCondition, Integer pageIndex, Integer pageSize) {
        //判空
        if (userAwardMapCondition != null && pageIndex > -1 && pageSize > -1) {
            //页转行
            int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
            List<UserAwardMap> userAwardMaps =
                    userAwardMapDao.queryReceivedUserAwardMapList(userAwardMapCondition, rowIndex, pageSize);

            int count = userAwardMapDao.queryUserAwardMapCount(userAwardMapCondition);
            UserAwardMapExecution ue = new UserAwardMapExecution();
            ue.setCount(count);
            ue.setUserAwardMapList(userAwardMaps);
            return ue;
        }else {
            return null;
        }

    }
}
