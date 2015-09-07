package com.orm.shiro;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.cako.basic.platform.role.entity.Role;
import com.cako.basic.platform.user.entity.User;
import com.cako.basic.platform.user.service.IUserService;

public class RolesAuthorizationFilter extends AuthorizationFilter {

	@Autowired
	private IUserService userService;
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		Subject subject = getSubject(request, response);
		String[] rolesArray = (String[]) mappedValue;
		String loginName = (String)subject.getPrincipal();
		User user = userService.findUserByLoginName(loginName);
		if (user != null) {
			List<Role> roleList = user.getRoles();
			for (int i = 0; i < roleList.size(); i++) {
				Role role = roleList.get(i);
				rolesArray[i] = role.getName();
			}
		}
		if (rolesArray == null || rolesArray.length == 0) {
			return true;
		}

		for (int i = 0; i < rolesArray.length; i++) {
			if (subject.hasRole(rolesArray[i])) {
				return true;
			}
		}
		return false;
	}

}
