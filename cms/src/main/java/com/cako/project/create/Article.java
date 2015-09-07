package com.cako.project.create;

import java.io.Serializable;

public class Article implements Serializable {
	private static final long serialVersionUID = 554206256994693476L;
	private String title;
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
