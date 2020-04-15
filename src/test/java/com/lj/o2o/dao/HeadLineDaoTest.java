package com.lj.o2o.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lj.o2o.entity.HeadLine;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HeadLineDaoTest {

	@Autowired
	private HeadLineDao headLineDao;
	
	@Test
	public void testQueryHeadLine() {
		List<HeadLine> headLineList =  headLineDao.queryHeadLine(new HeadLine());
		assertEquals(2, headLineList.size());
	}
}
