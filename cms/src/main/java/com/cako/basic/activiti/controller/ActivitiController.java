package com.cako.basic.activiti.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cako.basic.activiti.service.ActivitiService;
import com.cako.basic.util.FileTools;
import com.cako.basic.util.Graph;
import com.cako.basic.util.MessageObject;
import com.cako.basic.util.SystemContext;
import com.cako.basic.version.entity.Version;
import com.orm.commons.utils.JsonMapper;

@Controller
@RequestMapping("/activiti")
public class ActivitiController {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private ActivitiService activitiService;

	/**
	 * 获得部署流程的列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<Deployment> dpList = repositoryService.createDeploymentQuery().orderByDeploymenTime().asc().listPage(0, 20);
		model.addAttribute("dpList", dpList);
		List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().list();
		model.addAttribute("processDefinitionList", processDefinitionList);
		return "activiti/list";
	}

	/**
	 * 开始流程
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/startTask", method = RequestMethod.GET)
	public String startTask(HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("numberOfDays", "3");
		params.put("startDate", new Date());
		String processDefinitionId = request.getParameter("id");
		activitiService.startProcess(params, processDefinitionId);
		return "redirect:getProcessInstanceList";
	}

	/**
	 * 查看流程图
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getImage", method = RequestMethod.GET)
	public ModelAndView getImage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("activiti/imageInfo");
		String deploymentId = request.getParameter("deploymentId");
		try {
			String fullPath = Graph.viewImage(deploymentId, request);
			fullPath = fullPath.replace("\\", "/");
			mav.addObject("fullPath", fullPath);
			return mav;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 发布流程
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/deployed", method = RequestMethod.POST)
	public @ResponseBody void deployed(HttpServletRequest request, HttpServletResponse response) {
		String realPath = request.getSession().getServletContext().getRealPath("/upload");
		if (!new File(realPath).exists()) {
			new File(realPath).mkdirs();
		}
		List<Version> versions = new ArrayList<Version>();
		String[] values = request.getParameterValues("myfiles");
		MessageObject message = new MessageObject();
		int flag = 0;
		if (values == null) {
			message.setResposeCode(MessageObject.ResposeCode.code_404);
			message.setResult("请先上传，在进行发布");
			response.setHeader("content-type", "text/html;charset=UTF-8");
			try {
				response.getWriter().write(new JsonMapper().toJson(message));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if (values.length > 0) {
				for (String filePath : values) {
					File file = new File(filePath);
					try {
						FileInputStream fin = new FileInputStream(file);
						ZipInputStream inputStream = new ZipInputStream(fin);
						DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
						deploymentBuilder = deploymentBuilder.name(file.getName());
						deploymentBuilder = deploymentBuilder.addZipInputStream(inputStream);
						deploymentBuilder.deploy();
						flag = 1;
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						flag = 0;
					} catch (Exception e) {
						e.printStackTrace();
						flag = 0;
					} finally {
						try {
							message.setObject(versions);
							if (flag == 0) {
								message.setResposeCode(MessageObject.ResposeCode.code_404);
								message.setResult("发布失败");
							} else {
								message.setResposeCode(MessageObject.ResposeCode.code_200);
								message.setResult("发布成功");
							}

							response.setHeader("content-type", "text/html;charset=UTF-8");
							FileTools.delFolder(new File(realPath + File.separatorChar + "activiti").getAbsolutePath());
							response.getWriter().write(new JsonMapper().toJson(message));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * 删除部署流程
	 * 
	 * @param id
	 * @param response
	 */
	@RequestMapping(value = "/deleteDeploy", method = RequestMethod.GET)
	public @ResponseBody void delete(String id, HttpServletResponse response) {
		MessageObject message = new MessageObject();
		try {
			activitiService.delDeployment(id);
			message.setResposeCode(MessageObject.ResposeCode.code_200);
			message.setResult("删除流程成功");
		} catch (Exception e) {
			message.setResposeCode(MessageObject.ResposeCode.code_404);
			message.setResult("删除流程失败");
		} finally {
			try {
				response.setHeader("content-type", "text/html;charset=UTF-8");
				response.getWriter().write(new JsonMapper().toJson(message));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/viewImage", method = RequestMethod.GET)
	public void viewImage(HttpServletRequest request, HttpServletResponse response) {
		String deploymentId = request.getParameter("deploymentId");
		String imageName = request.getParameter("imageName");
		InputStream inputStream = activitiService.findImageInputStream(deploymentId, imageName);
		try {
			OutputStream outputStream = response.getOutputStream();
			for (int i = -1; (i = inputStream.read()) != -2;) {
				outputStream.write(i);
			}
			outputStream.close();
		} catch (IOException e) {
		}
	}

	@RequestMapping(value = "/myTask", method = { RequestMethod.GET, RequestMethod.POST })
	public String myTask(HttpServletRequest request,Model model) {
		List<Task> list = taskService.createTaskQuery().taskAssignee(SystemContext.get().getId()).orderByTaskCreateTime().desc().list();
		model.addAttribute("list", list);
		return "activiti/myTask";
	}
}
