package com.lj.o2o.dto;

import com.lj.o2o.entity.UserProductMap;
import com.lj.o2o.enums.UserProductMapEnum;

import java.util.List;

public class UserProductMapExecution {

    private int state;

    private String stateInfo;

    private int count;

    private UserProductMap userProductMap;

    private List<UserProductMap> productMapList;

    public UserProductMapExecution() {

    }

    //操作失败
    public UserProductMapExecution(UserProductMapEnum userProductMapEnum) {
        this.state = userProductMapEnum.getState();
        this.stateInfo = userProductMapEnum.getStateInfo();
    }

    //操作成功
    public UserProductMapExecution(UserProductMapEnum userProductMapEnum, UserProductMap userProductMap) {
        this.state = userProductMapEnum.getState();
        this.stateInfo = userProductMapEnum.getStateInfo();
        this.userProductMap = userProductMap;
    }

    //操作成功
    public UserProductMapExecution(UserProductMapEnum userProductMapEnum, List<UserProductMap> userProductMapList) {
        this.state = userProductMapEnum.getState();
        this.stateInfo = userProductMapEnum.getStateInfo();
        this.productMapList = userProductMapList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public UserProductMap getUserProductMap() {
        return userProductMap;
    }

    public void setUserProductMap(UserProductMap userProductMap) {
        this.userProductMap = userProductMap;
    }

    public List<UserProductMap> getProductMapList() {
        return productMapList;
    }

    public void setProductMapList(List<UserProductMap> productMapList) {
        this.productMapList = productMapList;
    }
}
