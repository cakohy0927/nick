package com.cako.basic.platform.role.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.cako.basic.platform.menu.entity.Module;
import com.cako.basic.util.SysContent;
import com.orm.commons.utils.IdEntity;

/**
 * 系统角色
 * 
 * @author HUANGYUAN
 *
 */
@Entity
@Table(name = "system_role")
public class Role extends IdEntity {

	/**
	 * 是否可用
	 */
	private String isDisable = SysContent.IsDisable.NODISABLE;

	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 角色类型
	 */
	private String type;

	/**
	 * 菜单模块
	 */
	private List<Module> modules = new ArrayList<Module>();

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "system_role_module", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
	public List<Module> getModules() {
		return modules;
	}

	public String getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(String isDisable) {
		this.isDisable = isDisable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
