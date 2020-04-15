package com.lj.o2o.service;

import com.lj.o2o.dto.UserAwardMapExecution;
import com.lj.o2o.entity.UserAwardMap;

public interface UserAwardMapService {

    /**
     * 根据传入进来的查询条件分页返回用户兑换奖品记录的列表信息
     * @param userAwardMapCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    UserAwardMapExecution listUserAwardMap(UserAwardMap userAwardMapCondition,
                                           Integer pageIndex, Integer pageSize);
}
