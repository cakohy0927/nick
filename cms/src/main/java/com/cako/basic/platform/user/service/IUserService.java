package com.cako.basic.platform.user.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cako.basic.platform.role.entity.Role;
import com.cako.basic.platform.user.entity.User;
import com.orm.commons.exception.ServiceException;

public interface IUserService {
	/**
	 * 根据用户ID查询角色信息
	 * 
	 * @param userId
	 * @return
	 */
	public abstract List<Role> finRolesByUserId(String userId);

	public abstract User save(User user);

	public abstract List<User> findAll();

	public abstract User findUserByUsernameAndPassword(String username, String password) throws ServiceException;

	public abstract Page<User> findByPage(Pageable pageable);

	public abstract Page<User> findByPage(Map<String, Object> paramMap, Pageable pageable);

	public abstract List<User> findByEntityList(Map<String, Object> map);

	public abstract void delete(String id);

	public abstract User get(String id);

	public abstract User findUserByUsername(String loginName) throws ServiceException;

	public abstract User getUserByRequest(HttpServletRequest request);

	public abstract User findUserByUsernameAndPassword(HttpServletRequest request) throws ServiceException;

	public abstract User findUserByLoginName(String paramString);
	
	public Set<String> findPermissionsByLoginName(String loginName);
	
	public Set<String> findRolesByLoginName(String loginName);
	
	public boolean isRootUser(String loginName);
	
	
}