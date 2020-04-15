package com.lj.o2o.dto;

import java.util.List;

/**
 * echart里的series项
 */
public class EchartSeries {
    //商品名称
    private String name;
    //柱状图类型
    private String type = "bar";
    //日销量
    private List<Integer> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }
}
