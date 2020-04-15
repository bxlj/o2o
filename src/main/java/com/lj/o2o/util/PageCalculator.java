package com.lj.o2o.util;

public class PageCalculator {
	
	 //将页面传递过来的页码转换为数据库行码
	public static int calculateRowIndex(int pageIndex,int pageSize) {
		return (pageIndex > 0)?(pageIndex - 1)*pageSize:0;
	}

}
