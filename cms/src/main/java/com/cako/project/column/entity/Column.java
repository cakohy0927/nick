package com.cako.project.column.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cako.basic.util.SysContent;
import com.orm.commons.utils.IdEntity;

/**
 * 栏目实体
 * @author nick
 *
 */
@Entity
@Table(name = "pro_column")
public class Column extends IdEntity {

	/**
	 * 栏目名称
	 */
	private String name;
	
	/**
	 * 是否是子节点
	 */
	private Boolean flag = false;

	/**
	 * 上级栏目
	 */
	private Column column;

	/**
	 * 是否可用
	 */

	private String isDelete = SysContent.IsDisable.NODISABLE;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "p_id")
	public Column getColumn() {
		return column;
	}

	public Boolean getFlag() {
		return flag;
	}
	
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	
	public void setColumn(Column column) {
		this.column = column;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
}
