package com.cako.project.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.cako.project.create.Article;
import com.cako.project.create.GeneratorHtml;
import com.cako.project.create.ReturnBase;
import com.orm.commons.bean.MessageBean;
import com.orm.commons.spring.SpringApplicationContext;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
@RequestMapping("/freeMarker")
public class HelloWorldController {
	@Autowired
	private GeneratorHtml generatorHtml;
	
	@RequestMapping("/jsp")
	public String jspRequest(ModelMap map, HttpServletRequest request) {
		String templateFileName = "hello.ftl";
		ModelMap contextMap = new ModelMap();
		MessageBean messageBean = (MessageBean) SpringApplicationContext.getBean("messagebean");
		contextMap.put("name", " 黄园");
		messageBean.show();
		String htmlFilePath = "c:\\html";
		if (!new File(htmlFilePath).exists()) {
			new File(htmlFilePath).mkdirs();
		}
		generatorHtml.geneHtmlFile(templateFileName, contextMap, htmlFilePath, "test.html");
		return "temp";
	}

	@RequestMapping(value = "/create")
	@ResponseBody
	public ReturnBase all(HttpServletRequest request, HttpServletResponse response) {
		ReturnBase returnStatus = new ReturnBase();
		String msg = request.getParameter("msg");
		try {
			all(msg);
			returnStatus.setSuccess(true);
			returnStatus.setMsg("成功!");
		} catch (Exception e) {
			e.printStackTrace();
			returnStatus.setSuccess(false);
			returnStatus.setMsg(e.getMessage());
		}
		return returnStatus;

	}

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	public void all(String msg) throws Exception {
		Map<String, Article> rootMap = new HashMap<String, Article>();
		process("file.ftl", rootMap);
	}

	public void process(String templateName, Map<String, Article> rootMap) throws Exception {
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		Template template = configuration.getTemplate(templateName);
		File file = new File("d:/file.html");
		Article article = new Article();
		article.setTitle("关于小网客");
		article.setContent("解决方案咨询<br>大数据处理<br>系统架构<br>企业信息化咨询<br>Email:smallnetvisitor@qq.com<br>来自北京");
		rootMap.put("article", article);
		Writer out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
		template.process(rootMap, out);
		IOUtils.closeQuietly(out);

	}
}
