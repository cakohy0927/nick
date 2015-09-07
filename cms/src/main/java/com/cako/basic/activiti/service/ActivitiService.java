package com.cako.basic.activiti.service;

import java.io.InputStream;
import java.util.Map;

public interface ActivitiService {
	
	/**
	 * 启动流程
	 * 
	 * @param params
	 * @param processDefinitionId
	 */
	public void startProcess(Map<String, Object> params, String processDefinitionId);
	
	public void startProcess(Map<String, Object> params,String businessKey, String processDefinitionId);
	/**
	 * 完成任务
	 * 
	 * @param taskId
	 */
	public void completeTask(String taskId);
	
	/**
	 * 删除流程
	 * @param deploymentId
	 * @throws Exception
	 */
	public void delDeployment(String deploymentId) throws Exception;


	public InputStream findImageInputStream(String deploymentId, String imageName);
}
