package com.lj.o2o.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lj.o2o.entity.PersonInfo;
import com.lj.o2o.entity.WeChatAuth;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WechatAuthDaoTest {
	
	@Autowired
	private WechatAuthDao wechatAuthDao;
	
	@Test
	public void testAInsertWeChatAuth() throws Exception {
		//新增一条微信账号
		WeChatAuth weChatAuth = new WeChatAuth();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(1L);
		//给微信绑定用户
		weChatAuth.setPersonInfo(personInfo);
		weChatAuth.setOpenId("jiachengjiezhaodaogz");
		weChatAuth.setCreateTime(new Date());
		int effectNum = wechatAuthDao.insertWechatAuth(weChatAuth);
		assertEquals(1, effectNum);
	}
	
	@Test
	public void testBQueryqueryWechatInfoByOpenId() throws Exception {
		WeChatAuth weChatAuth = wechatAuthDao.queryWechatInfoByOpenId("jiachengjiezhaodaogz");
		assertEquals("李明", weChatAuth.getPersonInfo().getName());
	}
	

}
