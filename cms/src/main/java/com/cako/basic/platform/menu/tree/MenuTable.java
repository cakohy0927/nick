package com.cako.basic.platform.menu.tree;

import com.cako.basic.platform.menu.entity.Module;


public class MenuTable {

	private String id;
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 菜单路径
	 */
	private String uri;
	
	private String text;
	
	private String state;

	/**
	 * 菜单图标
	 */
	private String icon;

	/**
	 * 排序号
	 */
	private Integer sort;
	
	/**
	 * 别名
	 */
	private String permission;

	/**
	 * 菜单是否可用
	 */
	private Integer isDisable;

	/**
	 * 上级菜单
	 */

	public MenuTable (Module menu,String state){
		this.id = menu.getId();
		this.name = menu.getName();
		this.state = state;
		this.text = menu.getName();
		this.icon = menu.getIcon();
		this.uri = menu.getPath();
		this.permission = menu.getPermission();
		this.isDisable = menu.getIsDisable();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public Integer getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(Integer isDisable) {
		this.isDisable = isDisable;
	}
}
