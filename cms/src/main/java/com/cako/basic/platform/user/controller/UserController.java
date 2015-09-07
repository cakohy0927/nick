package com.cako.basic.platform.user.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cako.basic.platform.department.entity.Department;
import com.cako.basic.platform.department.service.IDepartmentService;
import com.cako.basic.platform.role.entity.Role;
import com.cako.basic.platform.role.service.IRoleService;
import com.cako.basic.platform.user.dto.UserTree;
import com.cako.basic.platform.user.entity.User;
import com.cako.basic.platform.user.service.IUserService;
import com.cako.basic.util.ListTools;
import com.cako.basic.util.MessageObject;
import com.orm.commons.exception.LoginException;
import com.orm.commons.exception.ServiceException;
import com.orm.commons.utils.JsonMapper;
import com.orm.commons.utils.PageSupport;
import com.orm.commons.utils.Pager;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IDepartmentService departmentService;

	private MessageObject message = new MessageObject();

	@RequestMapping(value = "/create", method = { RequestMethod.GET, RequestMethod.POST })
	public String add() {
		return "user/create";
	}

	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody void save(Model model, HttpServletRequest request, HttpServletResponse response) {
		MessageObject message = new MessageObject();
		try {
			User user = userService.findUserByLoginName(request.getParameter("loginName"));
			if (user == null) {
				user = userService.getUserByRequest(request);
				userService.save(user);
				message.setResposeCode(MessageObject.ResposeCode.code_200);
				message.setResult("操作成功");
			} else {
				message.setResposeCode(MessageObject.ResposeCode.code_100);
				message.setResult("用户已经存在");
			}
		} catch (Exception e) {
			message.setResposeCode(MessageObject.ResposeCode.code_200);
			message.setResult("操作失败");
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

	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(HttpServletRequest request, Model model) {
		return "user/list";
	}

	@RequestMapping(value = "/getListJson.json", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getListJson(HttpServletRequest request) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String currentPage = request.getParameter("page");
		Integer pageSize = Integer.parseInt(request.getParameter("rows"));
		String username = request.getParameter("username");
		paramMap.put("username_li", username);
		long totlaRecord = userService.findByEntityList(paramMap).size();
		Pager page = new Pager(totlaRecord, currentPage);
		Pageable pageable = new PageRequest(page.getCurrentPage() - 1, pageSize);
		PageSupport pageSupport = new PageSupport(pageable, new Sort(Sort.Direction.DESC, "createTime"));
		Page<User> pageInfo = userService.findByPage(paramMap, pageSupport);
		List<UserTree> lists = new ArrayList<UserTree>();
		for (User user : pageInfo) {
			lists.add(new UserTree(user));
		}
		String data = "{\"total\":" + totlaRecord + ",\"rows\":" + new JsonMapper().toJson(lists) + "}";
		return data;
	}

	@RequestMapping(value = "/androidLogin", method = { RequestMethod.POST, RequestMethod.GET })
	public void androidLogin(HttpServletRequest request, HttpServletResponse response) throws LoginException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			User user = userService.findUserByUsernameAndPassword(username, password);
			String path = "";
			if (user != null) {
				path = "http://192.168.1.101:8080/webapp/userInfo/index";
			} else {
				path = "http://192.168.1.101:8080/webapp/userInfo/login";
			}
			response.getOutputStream().write(new JsonMapper().toJson(path).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			throw new LoginException("查询失败", new Throwable());
		}
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.GET })
	public @ResponseBody void delete(HttpServletRequest request, HttpServletResponse response) {
		String ids = request.getParameter("ids");
		try {
			if (StringUtils.isNotEmpty(ids)) {
				String[] strings = ids.split(",");
				for (String id : strings) {
					User user = userService.get(id);
					if (user != null) {
						userService.delete(id);
					}
				}
				message.setResposeCode(MessageObject.ResposeCode.code_200);
				message.setResult("添加权限成功");
			}
		} catch (Exception e) {
			message.setResposeCode(MessageObject.ResposeCode.code_404);
			message.setResult("删除用户失败");
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
	 * 跟人员添加角色
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "addRole", method = RequestMethod.POST)
	public @ResponseBody void addRole(HttpServletResponse response, HttpServletRequest request) {
		try {
			String roleIds = request.getParameter("roleIds");
			String userIds = request.getParameter("userIds");
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("id_in", ListTools.toArrayList(roleIds));
			List<Role> roles = roleService.queryByMap(paramsMap);
			String[] ids = userIds.split(",");
			for (String id : ids) {
				User user = userService.get(id);
				if (user != null) {
					user.setRoles(roles);
					userService.save(user);
				}
				message.setResposeCode(MessageObject.ResposeCode.code_200);
				message.setResult("添加权限成功");
			}
		} catch (ServiceException e) {
			message.setResposeCode(MessageObject.ResposeCode.code_404);
			message.setResult("添加权限失败");
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
	 * 给人分配到部门
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "updateUser", method = RequestMethod.POST)
	public @ResponseBody void updateUser(HttpServletResponse response, HttpServletRequest request) {
		try {
			String deptIds = request.getParameter("deptIds");
			String userIds = request.getParameter("userIds");
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("id_in", ListTools.toArrayList(deptIds));
			List<Department> departments = departmentService.queryByMap(paramsMap);
			String[] ids = userIds.split(",");
			for (String id : ids) {
				User user = userService.get(id);
				if (user != null) {
					user.setDepartments(departments);
					userService.save(user);
				}
				message.setResposeCode(MessageObject.ResposeCode.code_200);
				message.setResult("添加人员成功");
			}
		} catch (ServiceException e) {
			message.setResposeCode(MessageObject.ResposeCode.code_404);
			message.setResult("添加人员失败");
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
