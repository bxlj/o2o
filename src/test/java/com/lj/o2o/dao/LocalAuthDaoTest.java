package com.lj.o2o.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lj.o2o.entity.LocalAuth;
import com.lj.o2o.entity.PersonInfo;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocalAuthDaoTest {

	@Autowired
	private LocalAuthDao localAuthDao;

	private static final String USERNAME = "testUsername";
	private static final String PASSWORD = "testPassword";
	
	@Test
	public void testAInsertLocalAuth() {
		//新增一条平台信息
		LocalAuth localAuth = new LocalAuth();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(6L);
		//给平台绑定用户信息
		localAuth.setPersonInfo(personInfo);
		//设置用户名与密码
		localAuth.setPassword(PASSWORD);
		localAuth.setUsername(USERNAME);
		localAuth.setCreateTime(new Date());
		int effectedNum = localAuthDao.insertLocalAuth(localAuth);
		assertEquals(1, effectedNum);
	}
	
	@Test
	public void testBQueryLocalByUserNameAndPwd() {
		//根据用户账号和密码查询用户
		LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPwd(USERNAME, PASSWORD);
		assertEquals("测试一下", localAuth.getPersonInfo().getName());
	}
	
	@Test
	public void testCQueryLocalUserId() {
		//通过用户Id查询对应localauth
		LocalAuth localAuth = localAuthDao.queryLocalUserId(6L);
		assertEquals("测试一下", localAuth.getPersonInfo().getName());
	}
	
	@Test
	public void testDInsertLocalAuth() {
		//修改密码
		int effectedNum = localAuthDao.updateLocalAuth(6L, USERNAME, PASSWORD, "LAOJIA", new Date());
		assertEquals(1, effectedNum);
		//查询账号的最新信息
		LocalAuth localAuth = localAuthDao.queryLocalUserId(6L);
		System.out.println(localAuth.getPassword());
	}
	
	
	
	
}
