package com.orm.config;

import com.orm.commons.spring.SpringApplicationContext;

public class Configuration {

	public static PageConfig getPageConfig() {
		return (PageConfig) SpringApplicationContext.getBean("pageConfig");
	}

}
