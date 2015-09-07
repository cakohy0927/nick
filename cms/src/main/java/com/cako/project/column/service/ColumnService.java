package com.cako.project.column.service;

import java.util.List;

import com.cako.project.column.entity.Column;
import com.orm.commons.service.BaseService;

public interface ColumnService extends BaseService<Column, String> {

	public List<Column> getColumnList(String pid);
	
	/**
	 * 查询父节点的信息
	 * @param id
	 * @return
	 */
	public Column getColumn(String id);
}
