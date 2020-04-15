package com.lj.o2o.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lj.o2o.dao.PersonInfoDao;
import com.lj.o2o.dao.WechatAuthDao;
import com.lj.o2o.dto.WechatAuthExecution;
import com.lj.o2o.entity.PersonInfo;
import com.lj.o2o.entity.WeChatAuth;
import com.lj.o2o.enums.WechatAuthStateEnum;
import com.lj.o2o.exception.WechatAuthOperationException;
import com.lj.o2o.service.WechatAuthService;

@Service
public class WechatAuthServiceImpl implements WechatAuthService {

	private static Logger log = LoggerFactory.getLogger(WechatAuthServiceImpl.class);
	
	@Autowired
	private WechatAuthDao wechatAuthDao;
	@Autowired
	private PersonInfoDao personInfoDao;

	@Override
	public WeChatAuth getWechatAuthByOpenId(String openId) {
		return wechatAuthDao.queryWechatInfoByOpenId(openId);
	}

	/**
	 * 注册本平台的微信帐号
	 */
	@Override
	@Transactional
	public WechatAuthExecution regist(WeChatAuth weChatAuth) throws WechatAuthOperationException {
		// 空值判断
		if (weChatAuth == null || weChatAuth.getOpenId() == null) {
			return new WechatAuthExecution(WechatAuthStateEnum.NULL_AUTH_INFO);
		}

		try {
			// 设置创建时间
			weChatAuth.setCreateTime(new Date());
			// 如果微信帐号里夹带着用户信息并且用户Id为空，则认为该用户第一次使用平台
			// (且通过微信登录),则自动创建用户信息

			try {
				if (weChatAuth.getPersonInfo() == null || weChatAuth.getPersonInfo().getUserId() == null) {
					weChatAuth.getPersonInfo().setCreateTime(new Date());
					weChatAuth.getPersonInfo().setEnableStatus(1);
					PersonInfo personInfo = weChatAuth.getPersonInfo();
					int effectedNum = personInfoDao.insertPersonInfo(personInfo);
					if (effectedNum <= 0) {
						throw new WechatAuthOperationException("添加用户失败");
					}
				}
			} catch (Exception e) {
				log.error("insertPersonInfo error:" + e.toString());
				throw new WechatAuthOperationException("insertPersonInfo error: " + e.getMessage());
			}
			//创建专属于本平台的微信帐号
			int effectedNum = wechatAuthDao.insertWechatAuth(weChatAuth);
			if(effectedNum <= 0) {
				throw new WechatAuthOperationException("账号创建失败");
				
			}else {
				return new WechatAuthExecution(WechatAuthStateEnum.SUCCESS);
			}

		} catch (Exception e) {
			log.error("insertWechatAuth error:" + e.toString());
			throw new WechatAuthOperationException("insertWechatAuth error: " + e.getMessage());
		}
	}

}
