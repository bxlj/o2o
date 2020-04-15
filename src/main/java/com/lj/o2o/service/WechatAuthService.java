package com.lj.o2o.service;

import com.lj.o2o.dto.WechatAuthExecution;
import com.lj.o2o.entity.WeChatAuth;
import com.lj.o2o.exception.WechatAuthOperationException;

public interface WechatAuthService {

	/**
	 * 通过openId查找平台对应的微信帐号
	 * @param openId
	 * @return
	 */
	WeChatAuth getWechatAuthByOpenId(String openId);

	/**
	 * 注册本平台的微信帐号
	 * @param weChatAuth
	 * @return
	 * @throws WechatAuthOperationException
	 */
	WechatAuthExecution regist(WeChatAuth weChatAuth) throws WechatAuthOperationException;
	
	
}
