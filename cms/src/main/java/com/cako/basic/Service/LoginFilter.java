package com.cako.basic.Service;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cako.basic.platform.user.entity.User;

public class LoginFilter implements Filter {
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;// 类型转换，方便后面调用getSession等方法
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();// 取当前请求对应的会话
//		String uri = request.getRequestURI();
		User user = session.getAttribute("user") == null ? null : (User) session.getAttribute("user");
		if (user == null) {
			servletRequest.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(servletRequest, servletResponse);
			return;
		}
		chain.doFilter(request, response);// 放行
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
}
