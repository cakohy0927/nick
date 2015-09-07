package com.cako.basic.platform.role.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.cako.basic.platform.menu.entity.Module;
import com.cako.basic.platform.role.entity.Role;
import com.orm.commons.service.CakoHyJpaRepostiory;

public interface RoleDao extends CakoHyJpaRepostiory<Role, String> {

	
	@Query("select m from Role r join r.modules m where r.id = ?1")
	public List<Module> getModuleList(String roleId);
}
