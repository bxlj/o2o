package com.lj.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {

	public static int getInt(HttpServletRequest request, String key) {
		try {
			/**
			 * 想要获得Integer:
            * String 为十进制. 采用valueof(String)合适. 非十进制,采用decode(String)
                          *   想要获得int
            * String 为十进制. 采用parseInt(String )合适. 非十进制,采用parseInt(String ,int)
			*/
			return Integer.decode(request.getParameter(key));
		} catch (Exception e) {
			return -1;
		}
	}

	public static long getLong(HttpServletRequest request, String key) {
		try {
			return Long.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return -1;
		}
	}

	public static Double getDouble(HttpServletRequest request, String key) {
		try {
			return Double.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return -1d;
		}
	}

	public static boolean getBoolean(HttpServletRequest request, String key) {
		try {
			return Boolean.valueOf(request.getParameter(key));
		} catch (Exception e) {
			return false;
		}
	}

	public static String getString(HttpServletRequest request, String key) {
		try {
			String result = request.getParameter(key);
			if (result != null) {
				// 去掉字符串两侧的空格
				result = result.trim();
			}
			if ("".equals(result)) {
				return null;
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}

}
