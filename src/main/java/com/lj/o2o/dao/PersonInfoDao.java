package com.lj.o2o.dao;

import com.lj.o2o.entity.PersonInfo;

public interface PersonInfoDao {
	
	/**
	  * 通过用户id查找用户
	 * @param userId
	 * @return
	 */
	PersonInfo queryPersonInfoById(Long userId);
	
	
	/**
	  * 添加用户信息
	 * @param personInfo
	 * @return
	 */
	int insertPersonInfo(PersonInfo personInfo);
	

}
