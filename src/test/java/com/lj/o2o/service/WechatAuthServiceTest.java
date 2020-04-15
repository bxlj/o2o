package com.lj.o2o.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lj.o2o.dto.WechatAuthExecution;
import com.lj.o2o.entity.PersonInfo;
import com.lj.o2o.entity.WeChatAuth;
import com.lj.o2o.enums.WechatAuthStateEnum;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WechatAuthServiceTest {

	@Autowired
	private WechatAuthService wechatAuthService;

	@Test
	public void registTest() {

		// 新增一条微信帐号
		WeChatAuth wechatAuth = new WeChatAuth();
		PersonInfo personInfo = new PersonInfo();
		String openId = "dafahizhfdhaih";
		// 给微信帐号设置上用户信息，但不设置上用户Id
		// 希望创建微信帐号的时候自动创建用户信息
		personInfo.setCreateTime(new Date());
		personInfo.setName("测试一下");
		personInfo.setUserType(1);
		wechatAuth.setPersonInfo(personInfo);
		wechatAuth.setOpenId(openId);
		wechatAuth.setCreateTime(new Date());
		WechatAuthExecution wae = wechatAuthService.regist(wechatAuth);
		assertEquals(WechatAuthStateEnum.SUCCESS.getState(), wae.getState());
		// 通过openId找到新增的wechatAuth
		wechatAuth = wechatAuthService.getWechatAuthByOpenId(openId);
		// 打印用户名字看看跟预期是否相符
		System.out.println(wechatAuth.getPersonInfo().getName());

	}
}
