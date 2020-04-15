package com.lj.o2o.service;

import com.lj.o2o.dto.UserProductMapExecution;
import com.lj.o2o.entity.UserProductMap;

public interface UserProductMapService {

    /**
     * 通过传入的查询条件,列出消费记录列表
     * @param userProductMap
     * @param beginIndex
     * @param pageSize
     * @return
     */
    UserProductMapExecution listUserProductMap(UserProductMap userProductMap,Integer beginIndex,
                                               Integer pageSize);
}
