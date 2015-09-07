package com.cako.basic.activiti.service.impl;

import java.io.InputStream;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cako.basic.activiti.service.ActivitiService;

@Service
@Transactional(readOnly = false)
public class ActivitiServiceImpl implements ActivitiService {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	/**
	 * 启动流程
	 * 
	 * @param params
	 * @param processDefinitionId
	 */
	public void startProcess(Map<String, Object> params, String processDefinitionId) {
		runtimeService.startProcessInstanceById(processDefinitionId, params);
	}

	/**
	 * 完成任务
	 * 
	 * @param taskId
	 */
	public void completeTask(String taskId) {
		taskService.complete(taskId);
	}

	/**
	 * 级联删除,会删除和当前规则相关的所有信息，包括历史
	 */
	public void delDeployment(String deploymentId) throws Exception {
		// 删除发布信息
		// 普通删除，如果当前规则下有正在执行的流程，则抛异常
		//repositoryService.deleteDeployment(deploymentId);
		// 级联删除,会删除和当前规则相关的所有信息，包括历史
		repositoryService.deleteDeployment(deploymentId, true);
	}

	/**
	 * 使用部署ID和资源图片名称名称 获取图片的输入流
	 */
	@Override
	public InputStream findImageInputStream(String deploymentId, String imageName) {
		InputStream inputStream = repositoryService.getResourceAsStream(deploymentId, imageName);
		return inputStream;
	}

	@Override
	public void startProcess(Map<String, Object> params, String businessKey, String processDefinitionId) {
		runtimeService.startProcessInstanceByKey(processDefinitionId, businessKey, params);
	}
}
