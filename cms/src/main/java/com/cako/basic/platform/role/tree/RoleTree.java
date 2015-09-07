package com.cako.basic.platform.role.tree;

import com.cako.basic.platform.role.entity.Role;

public class RoleTree {
	/**
	 * 是否可用
	 */
	private String isDisable = "0";

	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 角色类型
	 */
	private String type;

	private String id;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RoleTree(Role role) {
		this.id = role.getId();
		this.name = role.getName();
		this.type = role.getType();
		this.isDisable = role.getIsDisable();
	}

	public RoleTree(){
		
	}
}
