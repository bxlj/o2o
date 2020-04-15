package com.lj.o2o.dto;

import com.lj.o2o.entity.LocalAuth;
import com.lj.o2o.enums.LocalAuthStateEnum;

import java.util.List;

public class LocalAuthExecution {

	// 结果状态
	private int state;

	// 状态标识
	private String stateInfo;

	private int count;

	private LocalAuth localAuth;

	private List<LocalAuth> localAuthList;

	public LocalAuthExecution() {

	}

	// 失败的构造器
	public LocalAuthExecution(LocalAuthStateEnum authStateEnum) {
		this.state = authStateEnum.getState();
		this.stateInfo = authStateEnum.getStateInfo();
	}
	
	//成功的构造器
	public LocalAuthExecution(LocalAuthStateEnum authStateEnum, LocalAuth localAuth) {
		this.state = authStateEnum.getState();
		this.stateInfo = authStateEnum.getStateInfo();
		this.localAuth = localAuth;
	}
	
	//成功的构造器
	public LocalAuthExecution(LocalAuthStateEnum authStateEnum, List<LocalAuth> localAuthList) {
		this.state = authStateEnum.getState();
		this.stateInfo = authStateEnum.getStateInfo();
		this.localAuthList = localAuthList;
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

	public LocalAuth getLocalAuth() {
		return localAuth;
	}

	public void setLocalAuth(LocalAuth localAuth) {
		this.localAuth = localAuth;
	}

	public List<LocalAuth> getLocalAuthList() {
		return localAuthList;
	}

	public void setLocalAuthList(List<LocalAuth> localAuthList) {
		this.localAuthList = localAuthList;
	}

}
