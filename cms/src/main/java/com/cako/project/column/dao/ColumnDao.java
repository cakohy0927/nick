package com.cako.project.column.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.cako.project.column.entity.Column;
import com.orm.commons.service.CakoHyJpaRepostiory;

public interface ColumnDao extends CakoHyJpaRepostiory<Column, String>{

	@Query("select c from Column c where c.flag = 0")
	public List<Column> getColumnList();
	
	
	@Query("select c from Column c where c.column.id = ?1")
	public List<Column> getColumnList(String pid);
	
	@Query("select col from Column c join c.column col where c.id = ?1")
	public Column getColumn(String id);
}
