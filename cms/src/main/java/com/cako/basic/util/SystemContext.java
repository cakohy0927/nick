package com.cako.basic.util;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cako.basic.platform.user.entity.User;

public class SystemContext {
	/**
	 * 
	 */
	public static final String GLOBLE_USER_SESSION = "globle_user";
	private static HttpSession session;
	
	static {
		session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}
//	public static void setUser(User user) {
//		if (user != null) {
//			session.setAttribute(GLOBLE_USER_SESSION, user);
//		}
//	}

	public static User get() {
		return (User) session.getAttribute(GLOBLE_USER_SESSION);
	}

}
