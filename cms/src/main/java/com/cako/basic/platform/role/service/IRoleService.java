package com.cako.basic.platform.role.service;

import java.util.List;

import com.cako.basic.platform.menu.entity.Module;
import com.cako.basic.platform.role.entity.Role;
import com.orm.commons.service.BaseService;

public interface IRoleService extends BaseService<Role, String> {
	
	public List<Module> getModuleList(String roleId);
}
