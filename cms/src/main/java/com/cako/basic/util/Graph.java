package com.cako.basic.util;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.apache.commons.io.FileUtils;

public class Graph {
	@SuppressWarnings("deprecation")
	public static String viewImage(String deploymentId, HttpServletRequest request) throws Exception {
		String fullPath = "";
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		// 创建仓库服务对对象
		RepositoryService repositoryService = processEngine.getRepositoryService();
		// 从仓库中找需要展示的文件
		List<String> names = repositoryService.getDeploymentResourceNames(deploymentId);
		String imageName = null;
		for (String name : names) {
			if (name.indexOf(".png") >= 0) {
				imageName = name;
			}
		}
		String path = request.getRealPath("/picture/image");
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		if (imageName != null) {
			File f = new File(path + File.separator + imageName);
			// 通过部署ID和文件名称得到文件的输入流
			InputStream in = repositoryService.getResourceAsStream(deploymentId, imageName);
			FileUtils.copyInputStreamToFile(in, f);
			fullPath = "picture/image" + File.separator + imageName;
		}
		return fullPath;
	}
}
