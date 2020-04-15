package com.lj.o2o.service;

import com.lj.o2o.dto.UserShopMapExecution;
import com.lj.o2o.entity.UserShopMap;

public interface UserShopMapService {

    /**
     * 根据传入的信息分页显示用户积分列表
     * @param userShopMap
     * @param beginIndex
     * @param pageIndex
     * @return
     */
    UserShopMapExecution listUserShopMap(UserShopMap userShopMap, Integer beginIndex,
                                         Integer pageIndex);
}
