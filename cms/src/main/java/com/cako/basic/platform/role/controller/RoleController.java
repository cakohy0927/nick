package com.cako.basic.platform.role.controller;

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

import com.cako.basic.platform.menu.entity.Module;
import com.cako.basic.platform.menu.service.ModuleService;
import com.cako.basic.platform.menu.tree.ModuleTree;
import com.cako.basic.platform.role.entity.Role;
import com.cako.basic.platform.role.service.IRoleService;
import com.cako.basic.platform.role.tree.RoleTree;
import com.cako.basic.util.MessageObject;
import com.orm.commons.exception.LoginException;
import com.orm.commons.exception.ServiceException;
import com.orm.commons.utils.JsonMapper;
import com.orm.commons.utils.PageSupport;
import com.orm.commons.utils.Pager;

@Controller
@RequestMapping("role")
public class RoleController {

	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private ModuleService moduleService;
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(HttpServletRequest request){
		
		return "role/create";
	}
	
	@RequestMapping(value = "getModuleIds",method = RequestMethod.GET)
	public @ResponseBody String getModuleIds(HttpServletRequest request){
		List<String> moduleIds = new ArrayList<String>();
		try {
			Role role = roleService.get(request.getParameter("id"));
			for (Module module : role.getModules()) {
				moduleIds.add(module.getId());
			}
		} catch (ServiceException e) {
		} 
		return new JsonMapper().toJson(moduleIds);
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request,Model model){
		try {
			List<Module> modules = moduleService.findAll();
			List<ModuleTree> moduleTrees = new ArrayList<ModuleTree>();
			for (Module module : modules) {
				moduleTrees.add(new ModuleTree(module, null));
			}
			model.addAttribute("moduleJson", new JsonMapper().toJson(moduleTrees));
		} catch (ServiceException e) {
		}
		
		return "role/list";
	}
	
	@RequestMapping(value = "/getListJson.json", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getListJson(HttpServletRequest request) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String currentPage = request.getParameter("page");
			String rows = request.getParameter("rows");
			String username = request.getParameter("username");
			paramMap.put("username_li", username);
			long totlaRecord = roleService.queryByMap(paramMap).size();
			Pager page = new Pager(totlaRecord, currentPage);
			if(StringUtils.isEmpty(rows)) {
				rows = String.valueOf(page.getPageSize());
			}
			Pageable pageable = new PageRequest(page.getCurrentPage() - 1, page.getPageSize());
			PageSupport pageSupport = new PageSupport(pageable, new Sort(Sort.Direction.DESC, "createTime"));
			Page<Role> pageInfo = roleService.queryPageByMap(paramMap, pageSupport);
			List<RoleTree> lists = new ArrayList<RoleTree>();
			for (Role role : pageInfo.getContent()) {
				lists.add(new RoleTree(role));
			}
			String data = "{\"total\":" + totlaRecord + ",\"rows\":" + new JsonMapper().toJson(lists) + "}";
			return data;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 保存角色
	 * @param request
	 * @param role
	 * @return
	 * @throws LoginException
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public String save(HttpServletRequest request, Role role) throws LoginException {
		MessageObject message = new MessageObject();
		try {
			if (role != null) {
				role = roleService.save(role);
				if (StringUtils.isNotEmpty(role.getId())) {
					message.setResposeCode(MessageObject.ResposeCode.code_200);
					message.setResult("角色信息操作成功");
				}else {
					message.setResposeCode(MessageObject.ResposeCode.code_404);
					message.setResult("角色信息操作失败");
				}
			}
		} catch (ServiceException e) {
			message.setResposeCode(MessageObject.ResposeCode.code_404);
			message.setResult("角色信息操作失败");
			throw new LoginException("角色信息操作失败...", new Throwable());
		}
		return new JsonMapper().toJson(message);
	}
	
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	@ResponseBody
	public String edit(HttpServletRequest request) throws LoginException {
		MessageObject message = new MessageObject();
		try {
			String id = request.getParameter("id");
			Role role = roleService.get(id);
			if (StringUtils.isNotEmpty(role.getId())) {
				message.setResposeCode(MessageObject.ResposeCode.code_200);
				message.setResult("查询角色信息成功");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", role.getId());
				map.put("name", role.getName());
				message.setObject(map);
			}else {
				message.setResposeCode(MessageObject.ResposeCode.code_404);
				message.setResult("查询角色信息失败");
			} 
		} catch (ServiceException e) {
			message.setResposeCode(MessageObject.ResposeCode.code_404);
			message.setResult("查询角色信息失败");
			throw new LoginException("查询角色失败...", new Throwable());
		}
		return new JsonMapper().toJson(message);
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	@ResponseBody
	public void delete(HttpServletRequest request,HttpServletResponse response){
		String idsString = request.getParameter("ids");
		MessageObject message = new MessageObject();
		try {
			if (StringUtils.isNotEmpty(idsString)) {
				String[] strs = idsString.split(",");
				for (String id : strs) {
					roleService.delete(id);
				}
				message.setResposeCode(MessageObject.ResposeCode.code_200);
				message.setResult("删除角色信息成功");
			}else {
				message.setResposeCode(MessageObject.ResposeCode.code_404);
				message.setResult("删除角色信息失败");
			}
		} catch (ServiceException e) {
			message.setResposeCode(MessageObject.ResposeCode.code_404);
			message.setResult("删除角色信息失败");
		}finally {
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
	
	@RequestMapping(value = "updateModule.json", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public void updateModule(HttpServletRequest request,HttpServletResponse response){
		MessageObject message = new MessageObject();
		try {
			String roleIds = request.getParameter("roleIds");
			String moduleIds = request.getParameter("moduleIds");
			if (StringUtils.isNotEmpty(roleIds)) {
				String[] roleStrIds = roleIds.split(",");
				String[] moduleIdsStrs = null;
				if (StringUtils.isNotEmpty(moduleIds)) {
					moduleIdsStrs = moduleIds.split(",");
				}
				for (String roleId : roleStrIds) {
					Role role = roleService.get(roleId);
					List<Module> modules = new ArrayList<Module>();
					if (moduleIdsStrs != null) {
						for (String moduleId : moduleIdsStrs) {
							Module module = moduleService.get(moduleId);
							if (module != null) {
								modules.add(module);
							}
						}
					}
					role.setModules(modules);
					roleService.save(role);
				}
				message.setResposeCode(MessageObject.ResposeCode.code_200);
				message.setResult("赋权限成功");
			}
		} catch (ServiceException e) {
			message.setResposeCode(MessageObject.ResposeCode.code_404);
			message.setResult("赋权限失败");
		}finally {
			try {
				response.setHeader("content-type", "text/html;charset=UTF-8");
				response.getWriter().write(new JsonMapper().toJson(message));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
