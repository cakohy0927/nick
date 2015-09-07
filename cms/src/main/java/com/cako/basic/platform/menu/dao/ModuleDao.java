package com.cako.basic.platform.menu.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.cako.basic.platform.menu.entity.Module;
import com.orm.commons.service.CakoHyJpaRepostiory;

public interface ModuleDao extends CakoHyJpaRepostiory<Module, String> {

	
	@Query("select count(m) from Module m")
	public Integer getMenuCount();
	
	@Query("select max(m.sort) from Module m")
	public Integer getMaxSort();
	
	@Query("select m from Module m where m.module.id is null")
	public List<Module> getModuleList();
	
	/**
	 * 查询大小
	 * @param name
	 * @return
	 */
	@Query("select m FROM Module m join m.module mm where mm.name like ?1 and m.name like ?2")
	public List<Module> findModuleByName(String name,String moduleName);
	
	/**
	 * 查询分页
	 * @param name
	 * @param pageable
	 * @return
	 */
	@Query("select m FROM Module m join m.module mm where mm.name like ?1 and m.name like ?2")
	public Page<Module> findPageModuleByName(String name,Pageable pageable,String moduleName);
}
