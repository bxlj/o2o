package com.lj.o2o.dao;

import com.lj.o2o.entity.WeChatAuth;

public interface WechatAuthDao {
	
	/**
	 * 根据orderId查询对应本平台的微信账号
	 * @param openId
	 * @return
	 */
	WeChatAuth queryWechatInfoByOpenId(String openId);
	
	
	/**
	 * 添加对应本平台的微信账号
	 * @param weChatAuth
	 * @return
	 */
	int insertWechatAuth(WeChatAuth weChatAuth);
}
