package com.cako.project.create;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class GeneratorHtml {
	@Autowired
	private FreeMarkerConfigurer freemarkerConfig;

	/**
	 * 如果目录不存在，则自动创建
	 * 
	 * @param path
	 * @return boolean 是否成功
	 */
	private static boolean creatDirs(String path) {
		File aFile = new File(path);
		if (!aFile.exists()) {
			return aFile.mkdirs();
		} else {
			return true;
		}
	}

	/**
	 * 模板生成静态html的方法
	 * 
	 * @param templateFileName (模板文件名)
	 * @param contextMap  (用于处理模板的属性Object映射)
	 * @param htmlFilePath  (指定生成静态html的目录)
	 * @param htmlFileName (生成的静态文件名)
	 */
	public void geneHtmlFile(String templateFileName,Map<String,Object> contextMap, String htmlFilePath, String htmlFileName) {
		try {
			Template template = getFreeMarkerCFG(templateFileName);
			// 如果根路径存在,则递归创建子目录
			creatDirs(htmlFilePath);
			File afile = new File(htmlFilePath + "/" + htmlFileName);
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(afile)));
			template.process(contextMap, out);
			out.flush();
			out.close();
		} catch (TemplateException e) {
		} catch (IOException e) {
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * 获取freemarker的配置，freemarker本身支持classpath,目录或从ServletContext获取.
	 * @return Configuration 返回freemaker的配置属性
	 * @throws Exception
	 */
	private Template getFreeMarkerCFG(String templateName) throws Exception {
		Configuration configuration = freemarkerConfig.getConfiguration();
		return configuration.getTemplate(templateName);
	}
}
