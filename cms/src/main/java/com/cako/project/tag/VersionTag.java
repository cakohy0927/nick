package com.cako.project.tag;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Created by cako on 2014/5/5.
 */
public class VersionTag extends BodyTagSupport {
	private static final long serialVersionUID = 7698404386651001014L;
	private Map<String, Object> model = new HashMap<String, Object>();
	private String versionIds;

	public String getVersionIds() {
		return versionIds;
	}

	public void setVersionIds(String versionIds) {
		this.versionIds = versionIds;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

	@Override
	public int doStartTag() throws JspException {
		return super.doStartTag();
	}

	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String ctx = request.getContextPath();
		this.model.put("ctx", ctx);
		String[] ids = versionIds.split(",");
		List<String> verIds = new ArrayList<String>();
		for (String id : ids) {
			verIds.add(id);
		}
		// VersionService versionService =
		// SpringApplicationContext.getBean(VersionService.class);
		// List<Version> versionList = versionService.findVersions(verIds);
		// this.model.put("versions",versionList);
		try {
			Configuration configuration = new Configuration();
			configuration.setClassForTemplateLoading(com.cako.project.tag.VersionTag.class, "template");
			Template template = configuration.getTemplate("version.ftl", "UTF-8");
			StringWriter writer = new StringWriter();
			template.process(this.model, writer);
			this.pageContext.getOut().write(writer.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	@Override
	public int doAfterBody() throws JspException {
		return super.doAfterBody();
	}
}
