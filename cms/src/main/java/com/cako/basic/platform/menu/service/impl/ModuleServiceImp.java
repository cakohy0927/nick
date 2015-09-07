package com.cako.basic.platform.menu.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cako.basic.platform.menu.dao.ModuleDao;
import com.cako.basic.platform.menu.entity.Module;
import com.cako.basic.platform.menu.service.ModuleService;
import com.cako.basic.platform.menu.tree.MenuTable;
import com.orm.commons.exception.ServiceException;
import com.orm.commons.service.impl.DefaulfAbstractService;

@Component
@Transactional(readOnly = false)
public class ModuleServiceImp extends DefaulfAbstractService<Module, String> implements ModuleService{

	@Autowired
	private ModuleDao moduleDao;

	@Override
	public List<Module> getModuleCount(Map<String, Object> paramMap) throws ServiceException{
		Criteria criteria = moduleDao.createCriteria(paramMap);
		return moduleDao.queryByCriteria(criteria);
	}

	@Override
	public Integer getMaxSort() throws ServiceException{
		return moduleDao.getMaxSort();
	}

	@Override
	public String getModuleHtml(List<MenuTable> menuTables) throws ServiceException {
//		if (menuTables != null && menuTables.size() > 0) {
//			String html = "<ul>";
//			for (MenuTable menuTable : menuTables) {
//			}
//			html += "</ul>";
//		}
		return null;
	}

	@Override
	public Integer getModuleMaxSort(Map<String, Object> paramMap)  throws ServiceException{
		List<Module> menus = getModuleCount(paramMap);
		if (menus != null && menus.size() > 0) {
			Integer sort = getMaxSort();
			return sort;
		}
		return null;
	}

	@Override
	public List<Module> getModuleList() throws ServiceException{
		return moduleDao.getModuleList();
	}

	@Override
	public List<Module> findModuleByName(String name,String moduleName) throws ServiceException{
		if (StringUtils.isEmpty(moduleName)) {
			moduleName = "%%";
		}else {
			moduleName = "%" + moduleName + "%";
		}
		return moduleDao.findModuleByName(name,moduleName);
	}

	@Override
	public Page<Module> findPageModuleByName(String name, Pageable pageable,String moduleName)  throws ServiceException{
		if (StringUtils.isEmpty(moduleName)) {
			moduleName = "%%";
		}else {
			moduleName = "%" + moduleName + "%";
		}
		return moduleDao.findPageModuleByName(name, pageable, moduleName);
	}
}
