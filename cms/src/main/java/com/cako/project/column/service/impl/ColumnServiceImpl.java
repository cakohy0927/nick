package com.cako.project.column.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cako.project.column.dao.ColumnDao;
import com.cako.project.column.entity.Column;
import com.cako.project.column.service.ColumnService;
import com.orm.commons.service.impl.DefaulfAbstractService;

@Service
@Transactional(readOnly = true)
public class ColumnServiceImpl extends DefaulfAbstractService<Column, String> implements ColumnService {

	@Autowired
	private ColumnDao columnDao;

	@Override
	public List<Column> getColumnList(String pid) {
		List<Column> columns = new ArrayList<Column>();
		if (StringUtils.isNotEmpty(pid)) {
			columns = columnDao.getColumnList(pid);
		}else {
			columns = columnDao.getColumnList();
		}
		return columns;
	}

	@Override
	public Column getColumn(String id) {
		return columnDao.getColumn(id);
	}
}
