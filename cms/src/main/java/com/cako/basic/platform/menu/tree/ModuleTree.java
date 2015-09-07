package com.cako.basic.platform.menu.tree;

import java.util.List;

import com.cako.basic.platform.menu.entity.Module;

public class ModuleTree {
	private String id;
	private String text;
	private String state;
	private String target = "main_frame";
	private List<ModuleTree> children;
	private boolean checked;
	private Integer isDisplay;
	private String uri;
	
	private String name;
	
	private String pId;
	
	private Boolean open = Boolean.TRUE;
	
	
	/**
	 * 别名
	 */
	private String permission;

	/**
	 * 菜单是否可用
	 */
	private Integer isDisable;

	public ModuleTree(Module module, String state) {
		super();
		this.id = module.getId();
		this.name = module.getName();
		if (module.getModule() != null) {
			this.pId = module.getModule().getId();
		}
		
		this.text = module.getName();
		this.state = state;
		this.uri = module.getPath();
		System.err.println("ModuleTree.ModuleTree()：" + module.getIsDisplay());
		this.isDisplay = module.getIsDisplay() == true ? 1 : 0;
		this.permission = module.getPermission();
		this.isDisable = module.getIsDisable();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public List<ModuleTree> getChildren() {
		return children;
	}

	public void setChildren(List<ModuleTree> children) {
		this.children = children;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Integer isDisplay() {
		return isDisplay;
	}

	public void setDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
}
