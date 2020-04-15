package com.lj.o2o.dto;

import com.lj.o2o.entity.ShopAuthMap;
import com.lj.o2o.enums.ShopAuthMapEnum;

import java.util.List;

public class ShopAuthMapExecution {

    //结果状态
    private int state;

    //状态标识
    private String stateInfo;

    //授权数
    private int count;

    private ShopAuthMap shopAuthMap;

    //授权列表
    private List<ShopAuthMap> shopAuthMapList;

    public ShopAuthMapExecution() {
    }

    // 失败的构造器
    public ShopAuthMapExecution(ShopAuthMapEnum shopAuthMapEnum) {
        this.state = shopAuthMapEnum.getState();
        this.stateInfo = shopAuthMapEnum.getStateInfo();
    }

    //成功的构造器
    public ShopAuthMapExecution(ShopAuthMapEnum shopAuthMapEnum, ShopAuthMap shopAuthMap) {
        this.state = shopAuthMapEnum.getState();
        this.stateInfo = shopAuthMapEnum.getStateInfo();
        this.shopAuthMap = shopAuthMap;
    }

    //成功的构造器
    public ShopAuthMapExecution(ShopAuthMapEnum shopAuthMapEnum, List<ShopAuthMap> shopAuthMapList) {
        this.state = shopAuthMapEnum.getState();
        this.stateInfo = shopAuthMapEnum.getStateInfo();
        this.shopAuthMapList = shopAuthMapList;
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

    public ShopAuthMap getShopAuthMap() {
        return shopAuthMap;
    }

    public void setShopAuthMap(ShopAuthMap shopAuthMap) {
        this.shopAuthMap = shopAuthMap;
    }

    public List<ShopAuthMap> getShopAuthMapList() {
        return shopAuthMapList;
    }

    public void setShopAuthMapList(List<ShopAuthMap> shopAuthMapList) {
        this.shopAuthMapList = shopAuthMapList;
    }
}
