package com.cako.basic.platform.role.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cako.basic.platform.menu.entity.Module;
import com.cako.basic.platform.role.dao.RoleDao;
import com.cako.basic.platform.role.entity.Role;
import com.cako.basic.platform.role.service.IRoleService;
import com.orm.commons.service.impl.DefaulfAbstractService;

@Component
@Transactional(readOnly = false)
public class RoleServiceImpl extends DefaulfAbstractService<Role, String> implements IRoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Override
	public List<Module> getModuleList(String roleId) {
		return roleDao.getModuleList(roleId);
	}

}
