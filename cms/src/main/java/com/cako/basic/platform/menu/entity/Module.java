package com.cako.basic.platform.menu.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.orm.commons.utils.IdEntity;

/**
 * 系统菜单
 * 
 * @author huangyuan
 *
 */
@Entity
@Table(name = "system_module")
public class Module extends IdEntity {
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 菜单路径
	 */
	private String path;

	/**
	 * 菜单图标
	 */
	private String icon;

	/**
	 * 排序号
	 */
	private Integer sort = 1;

	/**
	 * 上级菜单
	 */
	private Module module;

	/**
	 * 别名
	 */
	private String permission;
	
	private Boolean isDisplay = Boolean.FALSE;

	/**
	 * 菜单是否可用
	 */
	private Integer isDisable = 1;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "p_id")
	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public void setIsDisable(Integer isDisable) {
		this.isDisable = isDisable;
	}

	public Integer getIsDisable() {
		return isDisable;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	public Boolean getIsDisplay() {
		return isDisplay;
	}
	
	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}
}
