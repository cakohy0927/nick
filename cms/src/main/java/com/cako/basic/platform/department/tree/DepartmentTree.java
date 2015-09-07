package com.cako.basic.platform.department.tree;

import com.cako.basic.platform.department.entity.Department;

public class DepartmentTree {

	private String id;
	private String text;
	private String state;
	private boolean checked;
	private String pid;
	private String pName;
	private Boolean isChildern;
	public DepartmentTree(Department department,String state) {
		this.id = department.getId();
		this.text = department.getName();
		this.state = state;
		if (department.getDepartment() != null) {
			this.pid = department.getDepartment().getId();
			this.pName = department.getDepartment().getName();
		}
		this.isChildern = department.getIsChildern();
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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getPid() {
		return pid;
	}
	
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String getpName() {
		return pName;
	}
	
	public void setpName(String pName) {
		this.pName = pName;
	}
	
	public Boolean getIsChildern() {
		return isChildern;
	}
	
	public void setIsChildern(Boolean isChildern) {
		this.isChildern = isChildern;
	}
}
