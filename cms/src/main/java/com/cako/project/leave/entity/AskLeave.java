package com.cako.project.leave.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.orm.commons.utils.IdEntity;

@Entity
@Table(name = "ask_leave")
public class AskLeave extends IdEntity {

	private String resean;
	private String username;
	private String userId;
	private Integer days;
	
	private Integer state = AskLeave.stateClass.INI;

	/**
	 * 备注
	 */
	private String comment;
	public String getResean() {
		return resean;
	}

	public void setResean(String resean) {
		this.resean = resean;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public interface stateClass{
		/**
		 * 初始录入
		 */
		public static final Integer INI = 0;
		
		/**
		 * 审核中
		 */
		public static final Integer APPLING = 1;
		
		/**
		 * 审核完成
		 */
		public static final Integer FINISHED = 0;
		
	}
}
