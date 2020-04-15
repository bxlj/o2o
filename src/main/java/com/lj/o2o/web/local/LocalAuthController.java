package com.lj.o2o.web.local;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lj.o2o.dto.LocalAuthExecution;
import com.lj.o2o.entity.LocalAuth;
import com.lj.o2o.entity.PersonInfo;
import com.lj.o2o.enums.LocalAuthStateEnum;
import com.lj.o2o.service.LocalAuthService;
import com.lj.o2o.util.CodeUtil;
import com.lj.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping(value = "local", method = { RequestMethod.GET, RequestMethod.POST })
public class LocalAuthController {

	@Autowired
	private LocalAuthService localAuthService;

	@RequestMapping(value = "/bindlocalauth", method = RequestMethod.POST)
	@ResponseBody
	/**
	 * 将用户信息与平台帐号绑定
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, Object> bindLocalAuth(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 验证码判断
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;	
		}

		// 获取输入的帐号
		String userName = HttpServletRequestUtil.getString(request, "userName");
		// 获取输入的密码
		String password = HttpServletRequestUtil.getString(request, "password");
		// 从session中获取当前用户信息(用户一旦通过微信登录之后，便能获取到用户的信息)
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");

		// 非空判断，要求帐号密码以及当前的用户session非空
		if (userName != null && password != null && user != null && user.getUserId() != null) {
			// 创建localAuth对象	
			LocalAuth localAuth = new LocalAuth();
			localAuth.setPassword(password);
			localAuth.setUsername(userName);
			localAuth.setPersonInfo(user);
			// 绑定账号
			LocalAuthExecution lae = localAuthService.bindLocalAuth(localAuth);
			if (lae.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名和密码均不能为空");
			}
		}
		return modelMap;
	}

	@RequestMapping(value = "/changelocalpwd", method = RequestMethod.POST)
	@ResponseBody
	/**
	 * 修改密码
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, Object> changLocalPwd(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 验证码校验
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码输入错误");
			return modelMap;
		}

		// 获取输入的帐号
		String userName = HttpServletRequestUtil.getString(request, "userName");
		// 获取输入的密码
		String password = HttpServletRequestUtil.getString(request, "password");
		// 获取新密码
		String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
		// 从session中获取当前用户信息(用户一旦通过微信登录之后，便能获取到用户的信息)
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		// 非空判断，要求帐号密码以及当前的用户session非空
		if (userName != null && password != null && newPassword != null && user != null && user.getUserId() != null
				&& !password.equals(newPassword)) {
			try {
				// 查看原先帐号，看看与输入的帐号是否一致，不一致则认为是非法操作
				LocalAuth localAuth = localAuthService.getLocalUserId(user.getUserId());
				if (localAuth == null || !localAuth.getUsername().equals(userName)) {
					// 不一致退出
					modelMap.put("success", false);
					modelMap.put("errMsg", "输入的帐号非本次登录的帐号");
					return modelMap;
				}

				// 修改平台帐号的用户密码
				LocalAuthExecution lae = localAuthService.modifyLocalAuth(user.getUserId(), userName, password,
						newPassword);
				if (lae.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", lae.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入密码");
		}
		return modelMap;
	}
	

	@RequestMapping(value = "/logincheck", method = RequestMethod.POST)
	@ResponseBody
	/**
	 * 登录
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, Object> logincheck(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 获取是否需要进行验证码校验的标识符(输错三次)
		boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
		if (needVerify && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}

		// 获取输入的帐号
		String userName = HttpServletRequestUtil.getString(request, "userName");
		// 获取输入的密码
		String password = HttpServletRequestUtil.getString(request, "password");
		if (userName != null && password != null) {

			LocalAuth localAuth = localAuthService.getLocalByUserNameAndPwd(userName, password);
			if (localAuth != null) {
				modelMap.put("success", true);
				request.getSession().setAttribute("user", localAuth.getPersonInfo());
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码错误");
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码均不能为空");
		}
		return modelMap;

	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	/**
	 * 当用户点击退出按钮的时候注销session
	 * @param request
	 * @return
	 */
	private Map<String,Object> logout(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<>();
		request.getSession().setAttribute("user", null);
		modelMap.put("success", true);
		return modelMap;
	}

}
