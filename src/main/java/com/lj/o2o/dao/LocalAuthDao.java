package com.lj.o2o.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.lj.o2o.entity.LocalAuth;

public interface LocalAuthDao {

	/**
	  * 根据用户及密码查询用户信息,登录时使用
	 * @param username
	 * @param password
	 * @return
	 */
	LocalAuth queryLocalByUserNameAndPwd(@Param("username") String username, @Param("password") String password);
	
	
	/**
	  * 通过用户Id查询对应localauth
	 * @param userId
	 * @return
	 */
	LocalAuth queryLocalUserId(@Param("userId") Long userId);
	
	/**
	 * 添加平台账号
	 * @param localAuth
	 * @return
	 */
	int insertLocalAuth(LocalAuth localAuth);
	
	/**
	 * 修改密码
	 * @param userId
	 * @param username
	 * @param password
	 * @param newPassword
	 * @param lastEditTime
	 * @return
	 */
	int updateLocalAuth(@Param("userId") Long userId, @Param("username") String username,
                        @Param("password") String password, @Param("newPassword") String newPassword,
                        @Param("lastEditTime") Date lastEditTime);
	
	
}
