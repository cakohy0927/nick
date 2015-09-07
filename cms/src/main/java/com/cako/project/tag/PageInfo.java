package com.cako.project.tag;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.cako.basic.platform.user.entity.User;
import com.cako.basic.platform.user.service.IUserService;
import com.orm.commons.spring.SpringApplicationContext;
import com.orm.commons.utils.Pager;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class PageInfo extends TagSupport {

	private static final long serialVersionUID = 4137153296227702245L;
	private int currentPage;// 当前页
	private String url;
	private Map<String, Object> model = new HashMap<String, Object>();

	public Map<String, Object> getModel() {
		return model;
	}

	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int doAfterBody() throws JspException {
		return super.doAfterBody();
	}

	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String ctx = request.getContextPath();
		this.model.put("ctx", ctx);
		this.model.put("url", url);

		// 进行分页
		// 创建输出流对象
		JspWriter out = pageContext.getOut();
		StringWriter writer = new StringWriter();
		String currentPage = request.getParameter("currentPage");
		IUserService userService = SpringApplicationContext.getBean(IUserService.class);
		Pager pageInfo = new Pager((long)userService.findAll().size(),currentPage );
		Pageable pageable = new PageRequest(pageInfo.getCurrentPage() - 1, pageInfo.getPageSize());
		Page<User> list = userService.findByPage(pageable);
		this.model.put("pageInfo", pageInfo);
		this.model.put("list", list);
		try {
			Configuration configuration = new Configuration();
			configuration.setClassForTemplateLoading(com.cako.project.tag.PageInfo.class, "template");
			Template template = configuration.getTemplate("page.ftl", "UTF-8");
			template.process(this.model, writer);
			out.write(writer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		return super.doStartTag();
	}

}
