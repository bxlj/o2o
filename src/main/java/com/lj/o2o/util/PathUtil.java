package com.lj.o2o.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PathUtil {
	
	//System.getProperty("file.separator")获取文件分隔符
	private static String seperator = System.getProperty("file.separator");

	private static String winPath;

	private static String linuxPath;

	private static String shopPath;

	@Value("${win.base.path}")
	public void setWinPath(String winPath) {
		PathUtil.winPath = winPath;
	}

	@Value("${linux.base.path}")
	public void setLinuxPath(String linuxPath) {
		PathUtil.linuxPath = linuxPath;
	}

	@Value("${shop.relevant.path}")
	public void setShopPath(String shopPath) {
		PathUtil.shopPath = shopPath;
	}

	/**
	 * 返回项目图片的根路径
	 * @return
	 */
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basepath = "";
		if(os.toLowerCase().startsWith("win")) {
			basepath = winPath;
		}else {
			basepath = linuxPath;
		}
		basepath = basepath.replace("/", seperator);
		return basepath;
	}
	
	/**
	 * 返回项目图片的子路径(相对路径)
	 * @param shopId
	 * @return
	 */
	public static String getShopImagePath(long shopId) {
		String imagePath = shopPath + shopId + "/";
		return imagePath.replace("/", seperator);
	}
}
