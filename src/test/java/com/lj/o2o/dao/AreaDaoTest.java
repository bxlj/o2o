package com.lj.o2o.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lj.o2o.entity.Area;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class AreaDaoTest {
	@Autowired
	private AreaDao areaDao;
	
	@Test
	public void testQueryArea() {
		List<Area> queryArea = areaDao.queryArea();
		assertEquals(2, queryArea.size());
	}

}
