package com.cako.project.column.service;

import java.util.List;

import com.cako.basic.version.entity.Version;
import com.cako.project.column.entity.Column;
import com.cako.project.column.entity.News;
import com.orm.commons.service.BaseService;

public interface NewsService extends BaseService<News, String> {
	/**
	 * 根据新闻id查询栏目
	 * @param id
	 * @return
	 */
	public Column getColumnByNewsId(String id);
	
	/**
	 * 根据新闻id查询图片集合
	 * @param id
	 * @return
	 */
	public List<Version> getVersionList(String id);
}
