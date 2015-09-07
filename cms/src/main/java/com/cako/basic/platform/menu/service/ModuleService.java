package com.cako.basic.platform.menu.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cako.basic.platform.menu.entity.Module;
import com.cako.basic.platform.menu.tree.MenuTable;
import com.orm.commons.exception.ServiceException;
import com.orm.commons.service.BaseService;

public interface ModuleService extends BaseService<Module, String> {

	/**
	 * 查询总记录数
	 * 
	 * @return Integer
	 */
	public List<Module> getModuleCount(Map<String, Object> map) throws ServiceException;

	/**
	 * 查询最大序号
	 * 
	 * @return Integer
	 */
	public Integer getMaxSort() throws ServiceException;

	/**
	 * 获得最大的序号
	 * 
	 * @param paramMap
	 * @return
	 */
	public Integer getModuleMaxSort(Map<String, Object> paramMap) throws ServiceException;

	
	public String getModuleHtml(List<MenuTable> menuTables) throws ServiceException;

	/**
	 * 生成html代码
	 * @return
	 */
	public List<Module> getModuleList() throws ServiceException;

	/**
	 * 查询大小
	 * 
	 * @param name
	 * @return
	 */
	public List<Module> findModuleByName(String name,String moduleName) throws ServiceException;

	/**
	 * 查询分页
	 * 
	 * @param name
	 * @param pageable
	 * @return
	 */
	public Page<Module> findPageModuleByName(String name, Pageable pageable,String moduleName) throws ServiceException;
}
