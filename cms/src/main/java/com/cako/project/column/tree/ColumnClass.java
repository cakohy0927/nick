package com.cako.project.column.tree;

import com.cako.project.column.entity.Column;

public class ColumnClass {

	private String id;
	private String name;
	private String text;
	private String pid;
	private String state;
	private String pName;
	
	public ColumnClass(Column column,String state) {
		this.id = column.getId();
		this.name = column.getName();
		this.text = column.getName();
		if (column.getColumn() != null) {
			this.pid = column.getColumn().getId();
			this.pName = column.getColumn().getName();
		}
		this.state = state;
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

}
