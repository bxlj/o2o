package com.lj.o2o.enums;

public enum AwardStateEnum {
    OFFLINE(-1, "非法区域"), SUCCESS(0, "操作成功"), INNER_ERROR(-1001, "操作失败"), EMPTY(
            -1002, "区域信息为空");

    private int state;
    private String stateInfo;

    AwardStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static AwardStateEnum stateOf(int index) {
        for (AwardStateEnum stateEnum : values()) {
            if (stateEnum.state == index) {
                return stateEnum;
            }
        }
        return null;
    }
}
