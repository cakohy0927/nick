package com.orm.commons.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlSplit {

	private String htmlStr;
	private Integer length = 100;
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
	private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
	private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

	public HtmlSplit(String htmlStr, Integer length) {
		this.htmlStr = htmlStr;
		if (length != null) {
			this.length = length;
		}
	}
	
	public String doStartTag() {
		StringBuffer sb = new StringBuffer();
		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签
		if (htmlStr.length() >= 1) {
			sb.append(htmlStr.trim());
		} else {
			sb.append(htmlStr);
		}
		String str = sb.toString();
		if (length != null && length > 0) {
			if (str.length() > length) {
				str = str.replaceAll("&nbsp;", "");
				str = str.substring(0, length);
				str = str + "...";
			}
		}
		return str;
	}

}
