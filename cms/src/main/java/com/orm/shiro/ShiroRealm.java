package com.orm.shiro;

import java.util.Set;
import java.util.logging.Logger;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;

import com.cako.basic.platform.user.entity.User;
import com.cako.basic.platform.user.service.IUserService;
import com.orm.commons.exception.ServiceException;

public class ShiroRealm extends AuthorizingRealm {

	private static Logger logger = Logger.getLogger("ShiroRealm");

	@Autowired
	private IUserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		try {
			String username = (String) principals.getPrimaryPrincipal();
			logger.info("用户名称：" + username);
			SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
			authorizationInfo.setRoles(userService.findRolesByLoginName(username));
			Set<String> set = userService.findPermissionsByLoginName(username);
			for (String string : set) {
				System.out.println(string);
			}
			authorizationInfo.setStringPermissions(userService.findPermissionsByLoginName(username));
			return authorizationInfo;
		} catch (BeansException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		try {
			String loginName = (String) authcToken.getPrincipal();
			User user = userService.findUserByUsername(loginName);
			if (userService.isRootUser(loginName)) {
				logger.info("超级管理员");
				return new SimpleAuthenticationInfo("root", "aeeaa849d945f2e80a5ba468672edc54", ByteSource.Util.bytes("root21a5cb0e348522d2b9e77bf5e32688a2"), getName());
			}
			if (user == null) {
				throw new AuthorizationException();
			}
			if (user.getIsDelete().booleanValue()) {
				throw new UnknownAccountException();
			}
			if (user.getStatus() == User.Status.LOCKED) {
				throw new LockedAccountException();
			}
			SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
			authorizationInfo.setRoles(userService.findRolesByLoginName(loginName));
			Set<String> set = userService.findPermissionsByLoginName(loginName);
			for (String string : set) {
				System.out.println(string);
			}
			SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(loginName, user.getPassword(), getName());
			authorizationInfo.setStringPermissions(userService.findPermissionsByLoginName(loginName));
			return authenticationInfo;
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

}
