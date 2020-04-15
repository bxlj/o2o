package com.lj.o2o.dto;

import com.lj.o2o.entity.Award;
import com.lj.o2o.enums.AwardStateEnum;
import com.lj.o2o.service.AwardService;

import java.util.List;

public class AwardExecution {

    private int state;
    private String stateInfo;
    private int count;
    private Award award;
    private List<Award> awardList;

    public AwardExecution() {
    }

    //失败的构造器
    public AwardExecution(AwardStateEnum awardStateEnum) {
        this.state = awardStateEnum.getState();
        this.stateInfo = awardStateEnum.getStateInfo();
    }

    //成功的构造器
    public AwardExecution(AwardStateEnum awardStateEnum, Award award) {
        this.state = awardStateEnum.getState();
        this.stateInfo = awardStateEnum.getStateInfo();
        this.award = award;
    }

    //成功的构造器
    public AwardExecution(AwardStateEnum awardStateEnum, List<Award> awardList) {
        this.state = awardStateEnum.getState();
        this.stateInfo = awardStateEnum.getStateInfo();
        this.awardList = awardList;
    }
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
    }

    public List<Award> getAwardList() {
        return awardList;
    }

    public void setAwardList(List<Award> awardList) {
        this.awardList = awardList;
    }
}
