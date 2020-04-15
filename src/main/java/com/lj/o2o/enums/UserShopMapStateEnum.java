package com.lj.o2o.enums;

public enum UserShopMapStateEnum {
    SUCCESS(1, "操作成功"), INNER_ERROR(-1001, "操作失败"), NULL_USERSHOP_ID(-1002, "UserShopId为空"), NULL_USERSHOP_INFO(-1003,
            "传入了空的信息");

    private int state;

    private String stateInfo;

    private UserShopMapStateEnum(int state, String stateInfo) {
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

    public static UserShopMapStateEnum stateOf(int index) {
        for (UserShopMapStateEnum stateEnum : values()) {
            if (stateEnum.state == index) {
                return stateEnum;
            }
        }
        return null;
    }
}
