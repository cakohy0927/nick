package com.cako.basic.activiti;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.orm.commons.utils.IdEntity;

@Entity
@Table(name = "activiti_bean")
public class ActivitiBean extends IdEntity {

	/**
	 * 申请单ID
	 */
	private String applyId;

	/**
	 * 发布ID
	 */
	private String deploymentId;

	/**
	 * 图片名称
	 */
	private String imageName;
	/**
	 * 任务ID
	 */
	private String taskId;
	private Long moeny;
	private String outCome;

	/**
	 * 备注
	 */
	private String comment;

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Long getMoeny() {
		return moeny;
	}

	public void setMoeny(Long moeny) {
		this.moeny = moeny;
	}

	public String getOutCome() {
		return outCome;
	}

	public void setOutCome(String outCome) {
		this.outCome = outCome;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
