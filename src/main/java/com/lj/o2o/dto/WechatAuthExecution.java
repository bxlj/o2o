package com.lj.o2o.dto;

import com.lj.o2o.entity.WeChatAuth;
import com.lj.o2o.enums.WechatAuthStateEnum;

import java.util.List;

public class WechatAuthExecution {
	
	//结果状态
	private int state;
	
	//状态标识
	private String stateInfo;
	
	private int count;
	
	private WeChatAuth weChatAuth;
	
	private List<WeChatAuth> wechatAuthList;
	
	//失败的构造器
	public WechatAuthExecution(WechatAuthStateEnum wechatAuthStateEnum) {
		this.state = wechatAuthStateEnum.getState();
		this.stateInfo = wechatAuthStateEnum.getStateInfo();
	}
	
	//成功的构造器
	public WechatAuthExecution(WechatAuthStateEnum stateEnum,WeChatAuth weChatAuth) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.weChatAuth = weChatAuth;
	}
	
	//成功的构造器
	public WechatAuthExecution(WechatAuthStateEnum stateEnum,List<WeChatAuth> wechatAuthList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.wechatAuthList = wechatAuthList;
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

	public WeChatAuth getWechatAuth() {
		return weChatAuth;
	}

	public void setWechatAuth(WeChatAuth wechatAuth) {
		this.weChatAuth = wechatAuth;
	}

	public List<WeChatAuth> getWechatAuthList() {
		return wechatAuthList;
	}

	public void setWechatAuthList(List<WeChatAuth> wechatAuthList) {
		this.wechatAuthList = wechatAuthList;
	}
}
