package com.cako.basic.platform.login;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cako.basic.platform.user.entity.User;
import com.cako.basic.platform.user.service.IUserService;

@Controller
@RequestMapping(value = "test")
public class TestController {

	@Autowired
	private IUserService userService;

	@RequestMapping("/login")
	public ModelAndView login(User user) {
		ModelAndView mv = new ModelAndView();
		Subject currentUser = SecurityUtils.getSubject();
		// 前台传过来时应该用JS加密一次
		String minwen = new Md5Hash(user.getPassword()).toHex();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(), minwen);
		// 记住我功能不是记住密码而是在整个会话过程记住会话ID,对未登录用户时用购物车有点用
//		if (rememberMe != null) {
//			if (rememberMe) {
//				token.setRememberMe(true);
//			}
//		}

		try {
			currentUser.login(token);
		} catch (UnknownAccountException uae) {
			mv.addObject("info", "用户名不存在系统！");
			mv.setViewName("/erro");
		} catch (IncorrectCredentialsException ice) {
			mv.addObject("info", "密码错误！");
			mv.setViewName("/erro");
		} catch (LockedAccountException lae) {
			mv.addObject("info", "用户已经被锁定不能登录，请与管理员联系！");
			mv.setViewName("/erro");
		} catch (ExcessiveAttemptsException eae) {
			mv.addObject("info", "错误次数过多！");
			mv.setViewName("/erro");
		} catch (AuthenticationException ae) {
			mv.addObject("info", "其他的登录错误！");
			mv.setViewName("/erro");
		}
		// 验证是否成功登录的方法
		if (currentUser.isAuthenticated()) {
			// 在session生命周期内有效
			mv.setViewName("/main");
		}
		return mv;

	}

	@RequestMapping("/do")
	public ModelAndView do_() {
		ModelAndView mv = new ModelAndView();

		Subject currentUser = SecurityUtils.getSubject();

		if (currentUser.hasRole("超级管理员")) {
			mv.addObject("info", "没有这个角色！");
			mv.setViewName("/lognTest");
		} else {
			if (currentUser.isPermitted("/user/do")) {
				mv.addObject("info", "do！");
				mv.setViewName("/success");
			} else {
				mv.addObject("info", "do！");
				mv.setViewName("/lognTest");
			}
		}
		return mv;
	}

	@RequestMapping("/out")
	public ModelAndView out() {
		ModelAndView mv = new ModelAndView();
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.hasRole("超级管理员")) {
			mv.addObject("info", "没有这个角色！");
			mv.setViewName("/lognTest");
		} else {
			if (currentUser.isPermitted("/user/out")) {
				mv.addObject("info", "do！");
				mv.setViewName("/success");
			} else {
				mv.addObject("info", "out！");
				mv.setViewName("/lognTest");
			}
		}
		return mv;
	}

	@RequestMapping("/test")
	public ModelAndView test() {
		ModelAndView mv = new ModelAndView();
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser.hasRole("超级管理员")) {
			mv.addObject("info", "没有这个角色！");
			mv.setViewName("/lognTest");
		} else {
			if (currentUser.isPermitted("/user/test")) {
				mv.addObject("info", "test！");
				mv.setViewName("/success");
			} else {
				mv.addObject("info", "test！");
				mv.setViewName("/lognTest");
			}
		}

		return mv;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
}
