package com.lj.o2o.service;

import com.lj.o2o.dto.LocalAuthExecution;
import com.lj.o2o.entity.LocalAuth;
import com.lj.o2o.exception.LocalAuthOperationException;

public interface LocalAuthService {

	/**
	 * 通过账号和密码获取平台信息
	 * @param usernsme
	 * @param password
	 * @return
	 */
	LocalAuth getLocalByUserNameAndPwd(String username, String password);
	
	/**
	 * 通过userId获取平台账号信息
	 * @param userId
	 * @return
	 */
	LocalAuth getLocalUserId(Long userId);
	
	/**
	 * 绑定微信,生成平台专属账号
	 * @param localAuth
	 * @return
	 */
	LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException;
	
	/**
	 * 修改密码
	 * @param userId
	 * @param username
	 * @param password
	 * @param newPassword
	 * @return
	 * @throws LocalAuthOperationException
	 */
	LocalAuthExecution modifyLocalAuth(Long userId, String username, String password,
                                       String newPassword) throws LocalAuthOperationException;
	
}
