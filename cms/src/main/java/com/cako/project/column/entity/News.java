package com.cako.project.column.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cako.basic.platform.user.entity.User;
import com.cako.basic.util.SysContent;
import com.cako.basic.version.entity.Version;
import com.orm.commons.utils.IdEntity;

/**
 * 新闻实体
 * @author nick
 *
 */
@Entity
@Table(name = "pro_news")
public class News extends IdEntity{

	private Boolean deployStatus = SysContent.DeployStatus.NODEPLOY;
	/**
	 * 新闻标题
	 */
	private String title;
	/**
	 * 新闻内容
	 */
	private String content;
	
	/**
	 * 所属栏目
	 */
	private Column column;
	
	/**
	 * 录入人
	 */
	private User user;
	
	/**
	 * 文件
	 */
	private List<Version> versions;
	
	private String isDelete = SysContent.IsDisable.NODISABLE;

	public Boolean getDeployStatus() {
		return deployStatus;
	}
	
	public void setDeployStatus(Boolean deployStatus) {
		this.deployStatus = deployStatus;
	}
	
	@Lob
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "column_id")
	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="version_id")
	public List<Version> getVersions() {
		return versions;
	}

	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
