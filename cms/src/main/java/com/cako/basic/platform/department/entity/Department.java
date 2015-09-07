package com.cako.basic.platform.department.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cako.basic.util.SysContent;
import com.orm.commons.utils.IdEntity;

/**
 * 部门信息
 * @author HUANGYUAN
 *
 */
@Entity
@Table(name = "system_department")
public class Department extends IdEntity {

	/**
	 * 部门ID
	 */
	private String name;

	/**
	 * 状态
	 */
	private String status = SysContent.IsDisable.NODISABLE;

	/**
	 * 上级节点
	 */
	private Department department;
	
	private Boolean isChildern = Boolean.FALSE;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@ManyToOne
	@JoinColumn(name = "p_id")
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public void setIsChildern(Boolean isChildern) {
		this.isChildern = isChildern;
	}
	
	public Boolean getIsChildern() {
		return isChildern;
	}
}
