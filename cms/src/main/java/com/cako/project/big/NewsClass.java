package com.cako.project.big;

import java.util.Date;
import java.util.List;

import com.cako.basic.version.entity.Version;
import com.cako.project.column.entity.News;

public class NewsClass {
	private String id;
	/**
	 * 新闻标题
	 */
	private String title;
	
	private Date createTime;
	/**
	 * 新闻内容
	 */
	private String content;
	
	/**
	 * 用户名称
	 */
	private String username;
	
	/**
	 * 栏目名称
	 */
	private String columnName;
	
	private String columnId;
	
	/**
	 * 文件
	 */
	private List<Version> versions;

	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public NewsClass(News news) {
		this.id = news.getId();
		if (news.getColumn() != null) {
			this.columnName = news.getColumn().getName();
			this.columnId = news.getColumn().getId();
		}
		if (news.getUser() != null) {
			this.username = news.getUser().getLoginName();
		}
		this.versions = news.getVersions();
		this.createTime = news.getCreateTime();
		this.title = news.getTitle();
		this.content = news.getContent();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public List<Version> getVersions() {
		return versions;
	}

	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}
	
	public String getColumnId() {
		return columnId;
	}
	
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	
}
