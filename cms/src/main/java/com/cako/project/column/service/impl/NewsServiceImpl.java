package com.cako.project.column.service.impl;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cako.basic.version.entity.Version;
import com.cako.project.column.dao.NewsDao;
import com.cako.project.column.entity.Column;
import com.cako.project.column.entity.News;
import com.cako.project.column.service.NewsService;
import com.orm.commons.service.impl.DefaulfAbstractService;

@Service
@Transactional(readOnly = true)
public class NewsServiceImpl extends DefaulfAbstractService<News, String> implements NewsService{

	@Autowired
	private NewsDao newsDao;

	@Override
	public Column getColumnByNewsId(String id) {
		return newsDao.getColumnByNewsId(id);
	}

	@Override
	public List<Version> getVersionList(String id) {
		return newsDao.getVersionList(id);
	}
}
