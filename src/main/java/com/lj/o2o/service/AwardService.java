package com.lj.o2o.service;

import com.lj.o2o.dto.AwardExecution;
import com.lj.o2o.dto.ImageHolder;
import com.lj.o2o.entity.Award;

public interface AwardService {

    /**
     * 根据传入的查询条件分页获取，奖品列表
     *
     * @param awardCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    AwardExecution getAwardList(Award awardCondition, Integer pageIndex, Integer pageSize);

    /**
     * 修改奖品信息
     * @param award
     * @param thumbnail
     * @return
     */
    AwardExecution modifyAward(Award award, ImageHolder thumbnail);
}
