package com.cako.basic.version.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.orm.commons.utils.IdEntity;

/**
 * 附件的大小
 * 
 * @author HUANGYUAN
 *
 */
@Entity
@Table(name = "system_version")
public class Version extends IdEntity {

	/**
	 * 附件的名称
	 */
	private String name;

	/**
	 * 附件上传的路径
	 */
	private String path;
	/**
	 * 附件的类型
	 */
	private String type;

	/**
	 * 附件的图标
	 */
	private String icon;

	/**
	 * 附件的大小
	 */
	private String size;
	
	/**
	 * 文件后缀名
	 */
	private String suffix;
	
	public Version(){
		
	}
	
	public Version(String name, String path, String type, String size,String suffix) {
		super();
		this.name = name;
		this.path = path;
		this.type = type;
		this.size = size;
		this.suffix = suffix;
	}
	public Version(String name, String path, String type, String size) {
		super();
		this.name = name;
		this.path = path;
		this.type = type;
		this.size = size;
	}
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	
}
