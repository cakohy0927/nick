package com.cako.project.leave.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cako.basic.activiti.service.ActivitiService;
import com.cako.basic.platform.user.entity.User;
import com.cako.basic.util.MessageObject;
import com.cako.basic.util.SystemContext;
import com.cako.basic.util.WebUtils;
import com.cako.project.leave.entity.AskLeave;
import com.cako.project.leave.service.AskLeaveService;
import com.orm.commons.exception.ServiceException;
import com.orm.commons.utils.JsonMapper;
import com.orm.commons.utils.PageSupport;
import com.orm.commons.utils.Pager;

@Controller
@RequestMapping("askLeave")
public class AskLeaveController {

	@Autowired
	private AskLeaveService askLeaveService;
	
	@Autowired
	private ActivitiService activitiService;
	private MessageObject message = new MessageObject();

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody void save(HttpServletRequest request, AskLeave askLeave, HttpServletResponse response) {
		try {
			User user = SystemContext.get();
			askLeave.setUsername(user.getRealName());
			askLeaveService.save(askLeave);
			message.setResposeCode(MessageObject.ResposeCode.code_200);
			message.setResult("申请单保存成功");
		} catch (ServiceException e) {
			message.setResposeCode(MessageObject.ResposeCode.code_404);
			message.setResult("申请单保存失败");
		} finally {
			try {
				response.setHeader("content-type", "text/html;charset=UTF-8");
				response.getWriter().write(new JsonMapper().toJson(message));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value = "list", method = {RequestMethod.POST,RequestMethod.GET})
	public String list(HttpServletRequest request,Model model){
		try {
			String currentPage = request.getParameter("currentPage");
			Map<String, Object> paramMap = WebUtils.getRequestToMap(request);
			User user = (User) request.getSession().getAttribute("user");
			paramMap.put("userId", user.getId());
			long totalRecord = askLeaveService.queryByMap(paramMap).size();
			Pager pager = new Pager(totalRecord, currentPage);
			
			PageRequest pageable = new PageRequest(pager.getCurrentPage() - 1 > 0 ? pager.getCurrentPage() - 1 : 0, pager.getPageSize());
			PageSupport pageSupport = new PageSupport(pageable, new Sort(Sort.Direction.DESC,"createTime"));
			Page<AskLeave> pageInfo = askLeaveService.queryPageByMap(paramMap, pageSupport);
			model.addAttribute("pageInfo", pageInfo.getContent());
		} catch (ServiceException e) {
		}
		return "askLeave/list";
	}
	
	@RequestMapping(value = "apply", method = {RequestMethod.POST,RequestMethod.GET})
	public void apply(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		try {
			AskLeave askLeave = askLeaveService.get(id);
			askLeave.setState(AskLeave.stateClass.APPLING);
			askLeave = askLeaveService.save(askLeave);
			Map<String, Object> params = new HashMap<String, Object>();
			String key = askLeave.getClass().getSimpleName();
			params.put("userId", SystemContext.get().getId());
			String objId = key + ":" + SystemContext.get().getId();
			params.put("objId", objId);
			activitiService.startProcess(params, objId, key);
			message.setResposeCode(MessageObject.ResposeCode.code_200);
			message.setResult("提交申请成功");
		} catch (ServiceException e) {
			message.setResposeCode(MessageObject.ResposeCode.code_404);
			message.setResult("提交申请失败");
		} finally {
			try {
				response.setHeader("content-type", "text/html;charset=UTF-8");
				response.getWriter().write(new JsonMapper().toJson(message));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
