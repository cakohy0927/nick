package com.cako.basic.platform.menu.controller;

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
import com.cako.basic.platform.menu.tree.MenuTable;
import com.cako.basic.platform.menu.tree.ModuleTree;
import com.cako.basic.platform.role.service.IRoleService;
import com.cako.basic.util.MessageObject;
import com.orm.commons.exception.LoginException;
import com.orm.commons.exception.ServiceException;
import com.orm.commons.utils.JsonMapper;
import com.orm.commons.utils.PageSupport;
import com.orm.commons.utils.Pager;

@Controller
@RequestMapping("/menu")
public class ModuleController {

	@Autowired
	private ModuleService menuService;
	@Autowired
	private IRoleService roleService;

	@RequestMapping(value = "/getListJson.json", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getListJson(HttpServletRequest request) {
		try {
			String currentPage = request.getParameter("page");
			String moduleName = request.getParameter("username");
			String name = "目录";
			long totlaRecord = menuService.findModuleByName(name, moduleName).size();
			Pager page = new Pager(totlaRecord, currentPage);
			Pageable pageable = new PageRequest(page.getCurrentPage() - 1, page.getPageSize());
			PageSupport pageSupport = new PageSupport(pageable, new Sort(Sort.Direction.DESC, "createTime"));
			Page<Module> pageInfo = menuService.findPageModuleByName(name, pageSupport, moduleName);
			List<MenuTable> lists = new ArrayList<MenuTable>();
			for (Module module : pageInfo.getContent()) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("module.id", module.getId().toString());
				List<Module> menuList = menuService.queryByMap(paramMap);
				String state = menuList != null && menuList.size() > 0 ? "closed" : "";
				lists.add(new MenuTable(module, state));
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

	@RequestMapping(value = "getMenuJson", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void getMenuJson(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String id = request.getParameter("id");
			List<Module> modules = new ArrayList<Module>();
			String flag = request.getParameter("flag");
			if (flag == null ) {
				paramMap.put("isDisplay", true);
			}
			if (StringUtils.isEmpty(id)) {
				List<Module> moduleList = menuService.getModuleList();
				for (Module module : moduleList) {
					paramMap.put("module.id", module.getId());
					List<Module> list = menuService.queryByMap(paramMap);
					for (Module module2 : list) {
						modules.add(module2);
					}
				}
			} else {
				paramMap.put("module.id", id);
				modules = menuService.queryByMap(paramMap);
			}
			List<Module> mList = new ArrayList<Module>();
			List<ModuleTree> list = new ArrayList<ModuleTree>();
			String ids = request.getParameter("ids");
			if (StringUtils.isNotEmpty(ids)) {
				String[] strs = ids.split(",");
				if (strs.length <= 1) {
					mList = roleService.getModuleList(strs[0]);
					System.err.println("数据的个数：" + mList.size());

				}
			}
			for (Module module : modules) {
				paramMap = new HashMap<String, Object>();
				paramMap.put("module.id", module.getId().toString());
				List<Module> menuList = menuService.queryByMap(paramMap);
				ModuleTree moduleTree = new ModuleTree(module, menuList != null && menuList.size() > 0 ? "closed" : "");
				for (Module m : mList) {
					if (StringUtils.equals(module.getId(), m.getId())) {
						moduleTree.setChecked(true);
					}
				}
				String path = module.getPath();
				if (!StringUtils.equals("javascript:void(0)", path)) {
					moduleTree.setUri(request.getContextPath() + path);
				}
				list.add(moduleTree);
			}
			response.getWriter().write(new JsonMapper().toJson(list));
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody void save(HttpServletRequest request, Module module, Model model, HttpServletResponse response) throws LoginException {
		MessageObject message = new MessageObject();
		try {
			String parentId = request.getParameter("parentId");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Integer sort = menuService.getModuleMaxSort(paramMap);
			if (sort == null) {
				sort = 0;
			}
			module.setSort(sort);
			Module m = menuService.get(parentId);
			module.setModule(m);
			module = menuService.save(module);
			message.setResposeCode(MessageObject.ResposeCode.code_200);
			message.setResult("操作成功");
		} catch (ServiceException e) {
			message.setResposeCode(MessageObject.ResposeCode.code_200);
			message.setResult("操作失败");
			throw new LoginException("查询菜单失败", new Throwable());
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

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model) {
		return "system/menu/list";
	}

	@RequestMapping(value = "getListJson", method = RequestMethod.POST)
	@ResponseBody
	public String getListJson(HttpServletRequest request, Model model) {
		MessageObject message = new MessageObject();
		try {
			String pageSize = request.getParameter("pageSize");
			String currentPage = request.getParameter("currentPage");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			List<Module> list = menuService.queryByMap(paramMap);
			int curr = 0;
			if (StringUtils.isNotEmpty(currentPage)) {
				curr = Integer.parseInt(currentPage) - 1;
			}
			PageRequest pageable = new PageRequest(curr, Integer.parseInt(pageSize));
			PageSupport pageSupport = new PageSupport(pageable, new Sort(Sort.Direction.DESC, "createTime"));
			Page<Module> pageInfo = menuService.queryPageByMap(paramMap, pageSupport);
			List<MenuTable> menuTables = new ArrayList<MenuTable>();
			for (Module menu : pageInfo.getContent()) {
				menuTables.add(new MenuTable(menu, null));
			}
			message.setObject(menuTables);
			message.setCurrentPage(currentPage);// 设置当前页
			message.setTotalNumber(list.size());// 总记录数
			message.setResposeCode(MessageObject.ResposeCode.code_200);
			message.setResult("获取成功");
		} catch (ServiceException e) {
			message.setResposeCode(MessageObject.ResposeCode.code_404);
			message.setResult("获取失败");
		}
		return new JsonMapper().toJson(message);
	}

	@RequestMapping(value = "getJson", method = RequestMethod.GET)
	@ResponseBody
	public void getJson(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String id = request.getParameter("id");
			if (StringUtils.isEmpty(id)) {
				paramMap.put("name", "目录");
			} else {
				paramMap.put("module.id", id);
			}
			List<Module> modules = menuService.queryByMap(paramMap);
			List<ModuleTree> list = new ArrayList<ModuleTree>();
			for (Module module : modules) {
				paramMap = new HashMap<String, Object>();
				paramMap.put("module.id", module.getId().toString());
				List<Module> menuList = menuService.queryByMap(paramMap);
				ModuleTree moduleTree = new ModuleTree(module, menuList != null && menuList.size() > 0 ? "closed" : "");
				list.add(moduleTree);
			}
			response.getWriter().write(new JsonMapper().toJson(list));
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
