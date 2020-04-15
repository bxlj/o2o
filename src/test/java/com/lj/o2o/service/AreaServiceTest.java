package com.lj.o2o.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lj.o2o.entity.Area;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AreaServiceTest {

	@Autowired
	private AreaService areaService;
	@Autowired
	private CacheService cacheService;
	 
	@Test
	public void testGetAreaList() {
		List<Area> areaList = areaService.getAreaList();
		for (Area area : areaList) {
			System.out.println(area);
		}
		assertEquals("西苑", areaList.get(0).getAreaName());
		cacheService.removeFromCache(AreaService.AREALISTKEY);
		areaList = areaService.getAreaList();
	}
}
