package com.lj.o2o.entity;

import java.util.Date;

/**
 * 微信账号类
 *
 * @author 贾成杰
 */
public class WeChatAuth {

    //微信Id
    private Integer wechatAuthId;

    private String openId;

    private Date createTime;

    private PersonInfo personInfo;

    public Integer getWechatAuthId() {
        return wechatAuthId;
    }

    public void setWechatAuthId(Integer wechatAuthId) {
        this.wechatAuthId = wechatAuthId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }


}
