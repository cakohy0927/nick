package com.cako.basic.platform.department.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cako.basic.platform.department.dao.DepartmentDao;
import com.cako.basic.platform.department.entity.Department;
import com.cako.basic.platform.department.service.IDepartmentService;
import com.orm.commons.service.impl.DefaulfAbstractService;

@Component
@Transactional(readOnly = false)
public class DepartmentServiceImpl extends DefaulfAbstractService<Department, String> implements IDepartmentService {
	
	@Autowired
	private DepartmentDao departmentDao;
	
	
}
