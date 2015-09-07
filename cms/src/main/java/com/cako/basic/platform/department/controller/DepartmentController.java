package com.cako.basic.platform.department.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cako.basic.platform.department.entity.Department;
import com.cako.basic.platform.department.service.IDepartmentService;
import com.cako.basic.platform.department.tree.DepartmentTree;
import com.cako.basic.util.MessageObject;
import com.orm.commons.exception.LoginException;
import com.orm.commons.exception.ServiceException;
import com.orm.commons.utils.JsonMapper;
import com.orm.commons.utils.PageSupport;
import com.orm.commons.utils.Pager;

@Controller
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	private IDepartmentService departmentService;

	private MessageObject message = new MessageObject();

	/**
	 * 增加
	 * 
	 * @param request
	 * @param model
	 * @param department
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public void save(HttpServletRequest request, Department department, HttpServletResponse response) {
		try {
			String parentId = request.getParameter("parentId");
			if (StringUtils.isNotEmpty(parentId)) {
				Department depart = departmentService.get(parentId);
				if (depart != null) {
					department.setDepartment(depart);
					department.setIsChildern(Boolean.TRUE);
				}
			}
			departmentService.save(department);
			message.setResposeCode(MessageObject.ResposeCode.code_200);
			message.setResult("添加组织机构信息成功");
		} catch (ServiceException e) {
			message.setResposeCode(MessageObject.ResposeCode.code_404);
			message.setResult("添加组织机构信息异常，请稍候再试");
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

	/**
	 * 修改
	 * 
	 * @param request
	 * @param model
	 * @param department
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request, Model model, Department department) {

		return "";
	}

	/**
	 * 查询单一的对象
	 * 
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "findOne/{id}", method = RequestMethod.POST)
	public String findOne(@PathVariable("id") String id, HttpServletRequest request, Model model) {

		return "";
	}

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "department/list";
	}

	@RequestMapping(value = "departmentList.json", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String queryPageByMap(HttpServletRequest request,HttpServletResponse response) throws LoginException {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String currentPage = request.getParameter("page");
			String rows = request.getParameter("rows");
			String username = request.getParameter("username");
			paramMap.put("name_li", username);
			String id = request.getParameter("id");
			if (StringUtils.isNotEmpty(id)) {
				paramMap.put("department.id_eq", id);
				paramMap.put("isChildern_eq", Boolean.TRUE);
			}else {
				paramMap.put("isChildern_eq", Boolean.FALSE);
			}
			long totlaRecord = departmentService.queryByMap(paramMap).size();
			Pager page = new Pager(totlaRecord, currentPage);
			if (StringUtils.isEmpty(rows)) {
				rows = String.valueOf(page.getPageSize());
			}
			int current = page.getCurrentPage() - 1;
			if (current < 0) {
				current = 0;
			}
			Pageable pageable = new PageRequest(current, page.getPageSize());
			PageSupport pageSupport = new PageSupport(pageable, new Sort(Sort.Direction.DESC, "createTime"));
			Page<Department> pageInfo = departmentService.queryPageByMap(paramMap, pageSupport);
			List<DepartmentTree> lists = new ArrayList<DepartmentTree>();
			for (Department department : pageInfo.getContent()) {
				paramMap = new HashMap<String, Object>();
				paramMap.put("department.id_eq", department.getId());
				List<Department> list = departmentService.queryByMap(paramMap);
				department.setIsChildern(list != null && list.size() > 0 ? false : true);
				lists.add(new DepartmentTree(department, list != null && list.size() > 0 ? "closed" : ""));
			}
			String data = "{\"total\":" + totlaRecord + ",\"rows\":" + new JsonMapper().toJson(lists) + "}";
			return data;
		} catch (ServiceException exception) {
			throw new LoginException("查询部分分页信息失败", new Throwable());
		}
	}
	
	@RequestMapping(value = "departmentListTree.json", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody void departmentListTree(HttpServletRequest request,HttpServletResponse response) throws LoginException {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String id = request.getParameter("id");
			if (StringUtils.isNotEmpty(id)) {
				paramMap.put("department.id_eq", id);
				paramMap.put("isChildern_eq", Boolean.TRUE);
			}else {
				paramMap.put("isChildern_eq", Boolean.FALSE);
			}
			List<DepartmentTree> lists = new ArrayList<DepartmentTree>();
			List<Department> departments = departmentService.queryByMap(paramMap);
			for (Department department : departments) {
				paramMap = new HashMap<String, Object>();
				paramMap.put("department.id_eq", department.getId());
				List<Department> list = departmentService.queryByMap(paramMap);
				department.setIsChildern(list != null && list.size() > 0 ? false : true);
				lists.add(new DepartmentTree(department, list != null && list.size() > 0 ? "closed" : ""));
			}
			response.setHeader("content-type", "text/html;charset=UTF-8");
			response.getWriter().write(new JsonMapper().toJson(lists));
		} catch (ServiceException exception) {
			throw new LoginException("查询信息失败", new Throwable());
		} catch (IOException e) {
			throw new LoginException("查询信息失败", new Throwable());
		}
	}
}
