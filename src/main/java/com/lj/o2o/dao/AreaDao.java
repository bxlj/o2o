package com.lj.o2o.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lj.o2o.entity.Area;

@Repository
public interface AreaDao {
	
	/**
	  * 列出区域列表
	 * @return queryArea
	 */
	List<Area> queryArea();

}
