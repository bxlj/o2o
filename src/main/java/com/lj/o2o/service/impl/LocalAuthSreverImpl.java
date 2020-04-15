package com.lj.o2o.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lj.o2o.dao.LocalAuthDao;
import com.lj.o2o.dto.LocalAuthExecution;
import com.lj.o2o.entity.LocalAuth;
import com.lj.o2o.enums.LocalAuthStateEnum;
import com.lj.o2o.exception.LocalAuthOperationException;
import com.lj.o2o.service.LocalAuthService;
import com.lj.o2o.util.MD5;

@Service
public class LocalAuthSreverImpl implements LocalAuthService {

	@Autowired
	private LocalAuthDao localAuthDao;

	@Override
	public LocalAuth getLocalByUserNameAndPwd(String username, String password) {
		return localAuthDao.queryLocalByUserNameAndPwd(username, MD5.getMd5(password));
	}

	@Override
	public LocalAuth getLocalUserId(Long userId) {
		return localAuthDao.queryLocalUserId(userId);
	}

	@Override
	@Transactional
	public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException {
		// 空值判断,传入的账号密码不能为空，尤其userId不能为空
		if (localAuth == null || localAuth.getUsername() == null || localAuth.getPassword() == null
				|| localAuth.getPersonInfo() == null || localAuth.getPersonInfo().getUserId() == null) {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
		// 查询用户是否已经绑定平台
		LocalAuth tempAuth = localAuthDao.queryLocalUserId(localAuth.getPersonInfo().getUserId());
		if (tempAuth != null) {
			// 若已经存在则直接退出
			return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
		}

		try {
			// 如果之前没有绑定则创建一个平台账号与其绑定
			localAuth.setCreateTime(new Date());
			localAuth.setLastEditTime(new Date());
			// MD5对密码加密
			localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
			int effectedNum = localAuthDao.insertLocalAuth(localAuth);
			if (effectedNum <= 0) {
				throw new LocalAuthOperationException("账号绑定失败");
			} else {
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
			}
		} catch (Exception e) {
			throw new LocalAuthOperationException("insertLocalAuth error:" + e.getMessage());
		}
	}

	@Override
	@Transactional
	public LocalAuthExecution modifyLocalAuth(Long userId, String username, String password, String newPassword)
			throws LocalAuthOperationException {
		//非空判断,判断传入的参数是否为空,新旧密码是否相同
		if(userId != null && username != null && password != null && newPassword != null &&
				!password.equals(newPassword)) {
			try {
				//更新密码,进行加密
				int effectedNum = localAuthDao.updateLocalAuth(userId, username, MD5.getMd5(password), MD5.getMd5(newPassword), new Date());
			    if(effectedNum <= 0) {
			    	throw new LocalAuthOperationException("更新密码失败");
			    }
			    return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
				
			} catch (Exception e) {
				throw new LocalAuthOperationException("更新密码失败" + e.getMessage());
			}
			
		}else {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
	}

}
