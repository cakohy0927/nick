package com.cako.basic.platform.user.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.cako.basic.platform.role.entity.Role;
import com.cako.basic.platform.user.entity.User;
import com.orm.commons.service.CakoHyJpaRepostiory;

public interface UserDao extends CakoHyJpaRepostiory<User, String> {

	@Query("select user from User user where user.loginName = ?1 and user.password = ?2")
	public User findUserByUsernameAndPassword(String username,String password);
	
	@Query("select user from User user")
	public Page<User>  findByPage(Pageable pageable);

	@Query("select user from User user where user.loginName = ?1")
	public User findUserByUsername(String loginName);
	
	/**
	 * 根据用户的id查询角色
	 * @param userId
	 * @return
	 */
	@Query("select r from User u join u.roles r where u.id = ?1")
	public List<Role> finRolesByUserId(String userId);
	
	
}
