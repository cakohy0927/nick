package com.cako.basic.platform.user.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cako.basic.platform.menu.dao.ModuleDao;
import com.cako.basic.platform.menu.entity.Module;
import com.cako.basic.platform.role.dao.RoleDao;
import com.cako.basic.platform.role.entity.Role;
import com.cako.basic.platform.user.dao.UserDao;
import com.cako.basic.platform.user.entity.User;
import com.cako.basic.platform.user.service.IUserService;
import com.google.common.collect.Sets;
import com.orm.commons.encryption.MD5Encryption;
import com.orm.commons.exception.ServiceException;
import com.orm.commons.service.impl.DefaulfAbstractService;

@Component
@Transactional(readOnly = false)
public class UserServiceImpl extends DefaulfAbstractService<User, String> implements IUserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private ModuleDao moduleDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.orm.platform.user.service.impl.UserService#save(com.webapp.userInfo
	 * .entity.User)
	 */
	@Override
	public User save(User user) {
		if (StringUtils.isEmpty(user.getId())) {
			user.setPassword(MD5Encryption.MD5(user.getPassword()));
		}
		return userDao.saveAndFlush(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.orm.platform.user.service.impl.UserService#findAll()
	 */
	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.orm.platform.user.service.impl.UserService#findUserByUsernameAndPassword
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public User findUserByUsernameAndPassword(String username, String password) throws ServiceException {
		User user = userDao.findUserByUsernameAndPassword(username, password);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.orm.platform.user.service.impl.UserService#findByPage(org.springframework
	 * .data.domain.Pageable)
	 */
	@Override
	public Page<User> findByPage(Pageable pageable) {
		return userDao.findByPage(pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.orm.platform.user.service.impl.UserService#findByPage(java.util.Map,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<User> findByPage(Map<String, Object> paramMap, Pageable pageable) {
		return userDao.queryPageByMap(paramMap, pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.orm.platform.user.service.impl.UserService#findByEntityList(java.
	 * util.Map)
	 */
	@Override
	public List<User> findByEntityList(Map<String, Object> paramMap) {
		return userDao.queryByMap(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.orm.platform.user.service.impl.UserService#delete(java.lang.String)
	 */
	@Override
	public void delete(String id) {
		userDao.delete(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.orm.platform.user.service.impl.UserService#get(java.lang.String)
	 */
	@Override
	public User get(String id) {
		return userDao.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.orm.platform.user.service.impl.UserService#findUserByUsername(java
	 * .lang.String)
	 */
	@Override
	public User findUserByUsername(String loginName) throws ServiceException {
		return userDao.findUserByUsername(loginName);
	}

	public User getUserByRequest(HttpServletRequest request) {
		String loginName = request.getParameter("loginName");
		String realName = request.getParameter("realName");
		String password = request.getParameter("password");
		String address = request.getParameter("address");
		String QQ = request.getParameter("QQ");
		String phone = request.getParameter("phone");
		String sex = request.getParameter("sex");
		User user = new User();
		user.setLoginName(loginName);
		user.setRealName(realName);
		user.setPassword(password);
		user.setAddress(address);
		user.setSex(sex);
		user.setPhone(phone);
		user.setQQ(QQ);
		user.setEmail(request.getParameter("email"));
		return user;
	}

	@Override
	public User findUserByUsernameAndPassword(HttpServletRequest request) throws ServiceException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = userDao.findUserByUsername(username);
		if (user != null) {
			if (StringUtils.equals(user.getPassword(), MD5Encryption.MD5(password))) {
				request.getSession().setAttribute("user", user);
				return user;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cako.basic.platform.user.service.IUserService#finRolesByUserId(java
	 * .lang.String)
	 */
	@Override
	public List<Role> finRolesByUserId(String userId) {
		return userDao.finRolesByUserId(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cako.basic.platform.user.service.IUserService#findUserByLoginName
	 * (java.lang.String)
	 */
	public User findUserByLoginName(String loginName) {
		if (isRootUser(loginName)) {
			User user = new User();
			user.setId("root");
			user.setRealName("管理员");
			user.setLoginName("root");
			user.setStatus(User.Status.NORMAL);
			return user;
		}
		return this.userDao.findUserByUsername(loginName);
	}

	public boolean isRootUser(String loginName) {
		return "root".equals(loginName);
	}

	public Set<String> findRolesByLoginName(String loginName) {
		Set<String> set = Sets.newHashSet();
		if (isRootUser(loginName)) {
			List<Role> roles = roleDao.findAll();
			for (Role role : roles)
				set.add(role.getId());
		} else {
			User user = userDao.findUserByUsername(loginName);
			List<Role> roles = userDao.finRolesByUserId(user.getId());
			for (Role role : roles) {
				set.add(role.getId());
			}
		}
		return set;
	}

	public Set<String> findPermissionsByLoginName(String loginName) {
		Set<String> set = Sets.newHashSet();
		if (isRootUser(loginName)) {
			List<Module> modules = this.moduleDao.findAll();
			for (Module module : modules)
				if (StringUtils.isNotEmpty(module.getPermission()))
					set.add(module.getPermission());
		} else {
			User user = this.userDao.findUserByUsername(loginName);
			for (Role role : user.getRoles()) {
				List<Module> modules = roleDao.getModuleList(role.getId());
				for (Module module : modules) {
					if (StringUtils.isNotEmpty(module.getPermission())) {
						set.add(module.getPermission());
					}
				}
			}
		}
		return set;
	}
}
