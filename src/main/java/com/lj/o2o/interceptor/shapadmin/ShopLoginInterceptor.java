package com.lj.o2o.interceptor.shapadmin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lj.o2o.entity.PersonInfo;

/**
 * 店家管理系统登录验证拦截器
 * 
 * @author 老贾
 *
 */
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {

	/**
	 * 主要做事前拦截,用户发生操作前,进行拦截
	 * 
	 * @throws IOException
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException {
		// 从session中取出用户信息
		Object userObj = request.getSession().getAttribute("user");
		if (userObj != null) {
			PersonInfo user = (PersonInfo) userObj;
			// 空值判断,并且确保user不为空并且该账号的可用状态为1
			if (user != null && user.getUserId() != null && user.getEnableStatus() == 1 && user.getUserId() > 0) {
				// 若通过则返回true,拦截返回ture,用户接下来的操作可以正常进行
				return true;
			}
		}
		// 若不满足,则跳转到登录页面
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<script>");
		out.println("window.open ('" + request.getContextPath() + "/local/login?usertype=2','_self')");
		out.println("</script>");
		out.println("</html>");
		return false;
	}

}
