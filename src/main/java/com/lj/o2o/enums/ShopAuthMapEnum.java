package com.lj.o2o.enums;

public enum ShopAuthMapEnum {
    SUCCESS(1,"操作成功"),INNER_ERROR(-1001,"操作失败"),NULL_SHOPAUTH_ID(-1002,
            "ShopAuthId为空"), NULL_SHOPAUTH_INFO(-1003, "传入空的信息");

    private int state;

    private String stateInfo;

    ShopAuthMapEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
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
}
