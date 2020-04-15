package com.lj.o2o.service;

import java.io.IOException;
import java.util.List;


import com.lj.o2o.entity.HeadLine;

public interface HeadLineService {
	
	public static final String HLLISTKEY = "headlinelist";
	
	/**
	 * 根据传入的查询条件(头条名查询头条)
	 * @param headLineCondition
	 * @return
	 */
	List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;

}
