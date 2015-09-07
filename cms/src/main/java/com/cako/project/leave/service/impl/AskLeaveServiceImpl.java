package com.cako.project.leave.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cako.project.leave.entity.AskLeave;
import com.cako.project.leave.service.AskLeaveService;
import com.orm.commons.service.impl.DefaulfAbstractService;

@Service
@Transactional(readOnly = false)
public class AskLeaveServiceImpl extends DefaulfAbstractService<AskLeave, String> implements AskLeaveService{

	
}
