package com.orm.commons.utils;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class PageTag extends TagSupport {
	private static final long serialVersionUID = -7605746852222593994L;
	private String url; // 链接地址
	private int currentPage;// 当前页
	private int pagesize; // 页大小
	private int totalLines; // 总记录条数
	private String formId;

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getTotalLines() {
		return totalLines;
	}

	public void setTotalLines(int totalLines) {
		this.totalLines = totalLines;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		// 计算总页数
		int pageCount = totalLines % pagesize == 0 ? totalLines / pagesize : totalLines / pagesize + 1;
		// 拼写要输出到页面的HTML文本
		StringBuffer sb = new StringBuffer();
		sb.append("<style type=\"text/css\">");
		sb.append(".pagination {padding: 5px;text-align:center;font-size:12px;}");
		sb.append(".pagination a, .pagination a:link, .pagination a:visited {padding:2px 5px;margin:2px;border:1px solid #aaaadd;text-decoration:none;color:#006699;}");
		sb.append(".pagination a:hover, .pagination a:active {border: 1px solid #ff0000;color: #000;text-decoration: none;}");
		sb.append(".pagination span.current {padding: 2px 5px;margin: 2px;border: 1px solid #ff0000;font-weight: bold;background-color: #ff0000;color: #FFF;}");
		sb.append(".pagination span.disabled {padding: 2px 5px;margin: 2px;border: 1px solid #eee; color: #ddd;}");
		sb.append(".pagination { margin: -5px 0;}");
		sb.append("</style>\r\n");
		sb.append("<div class=\"pagination\">\r\n");
		if (totalLines == 0) {
			sb.append("<strong>没有可显示的项目</strong>\r\n");
		} else {
			// 页号越界处理
			if (currentPage > pageCount) {
				currentPage = pageCount;
			}
			if (currentPage < 1) {
				currentPage = 1;
			}

			sb.append("<form method=\"post\" action=\"").append(this.url).append("\" name=\"qPagerForm\">\r\n");

			// 获取请求中的所有参数
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			Enumeration<String> enumeration = request.getParameterNames();
			String name = null; // 参数名
			String value = null; // 参数值
			// 把请求中的所有参数当作隐藏表单域
			while (enumeration.hasMoreElements()) {
				name = enumeration.nextElement();
				value = request.getParameter(name);
				// 去除页号
				if (name.equals("currentPage")) {
					if (null != value && !"".equals(value)) {
						currentPage = Integer.parseInt(value);
					}
					continue;
				}
				sb.append("<input type=\"hidden\" name=\"").append(name).append("\" value=\"").append(value).append("\"/>\r\n");
			}

			// 把当前页号设置成请求参数
			sb.append("<input type=\"hidden\" name=\"").append("currentPage").append("\" value=\"").append(currentPage).append("\"/>\r\n");

			// 输出统计数据
			sb.append("&nbsp;第<strong>").append(currentPage).append("</strong>页").append("/共<strong>" + pageCount)
					.append("</strong>页&nbsp;&nbsp;共<strong>").append(totalLines).append("</strong>条记录").append(":&nbsp;\r\n");

			// 上一页处理
			if (currentPage == 1) {
				sb.append("<span class=\"disabled\">&laquo;&nbsp;上一页").append("</span>\r\n");
			} else {
				sb.append("<a href=\"javascript:turnOverPage(").append((currentPage - 1)).append(")\">&laquo;&nbsp;上一页</a>\r\n");
			}

			// 如果前面页数过多,显示"..."
			int start = 1;
			if (this.currentPage > 4) {
				start = this.currentPage - 1;
				sb.append("<a href=\"javascript:turnOverPage(1)\">1</a>\r\n");
				sb.append("<a href=\"javascript:turnOverPage(2)\">2</a>\r\n");
				sb.append("&hellip;\r\n");
			}
			// 显示当前页附近的页
			int end = this.currentPage + 1;
			if (end > pageCount) {
				end = pageCount;
			}
			for (int i = start; i <= end; i++) {
				if (currentPage == i) { // 当前页号不需要超链接
					sb.append("<span class=\"current\">").append(i).append("</span>\r\n");
				} else {
					sb.append("<a href=\"javascript:turnOverPage(").append(i).append(")\">").append(i).append("</a>\r\n");
				}
			}
			// 如果后面页数过多,显示"..."
			if (end < pageCount - 2) {
				sb.append("&hellip;\r\n");
			}
			if (end < pageCount - 1) {
				sb.append("<a href=\"javascript:turnOverPage(").append(pageCount - 1).append(")\">").append(pageCount - 1).append("</a>\r\n");
			}
			if (end < pageCount) {
				sb.append("<a href=\"javascript:turnOverPage(").append(pageCount).append(")\">").append(pageCount).append("</a>\r\n");
			}

			// 下一页处理
			if (currentPage == pageCount) {
				sb.append("<span class=\"disabled\">下一页&nbsp;&raquo;").append("</span>\r\n");
			} else {
				sb.append("<a href=\"javascript:turnOverPage(").append((currentPage + 1)).append(")\">下一页&nbsp;&raquo;</a>\r\n");
			}
			sb.append("</form>\r\n");

			// 生成提交表单的JS
			sb.append("<script language=\"javascript\">\r\n");
			sb.append("  function turnOverPage(no){\r\n");
			sb.append("    if(no>").append(pageCount).append("){");
			sb.append("      no=").append(pageCount).append(";}\r\n");
			sb.append("    if(no<1){no=1;}\r\n");
			sb.append("    document.qPagerForm.currentPage.value=no;\r\n");
			sb.append("    document.qPagerForm.submit();\r\n");
			sb.append("  }\r\n");
			sb.append("</script>\r\n");
		}
		sb.append("</div>\r\n");

		// 把生成的HTML输出到响应中
		try {
			pageContext.getOut().println(sb.toString());
		} catch (IOException e) {
			throw new JspException(e);
		}
		return SKIP_BODY;
	}
}
