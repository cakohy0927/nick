package com.cako.basic.platform.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.cako.basic.platform.menu.entity.Module;
import com.cako.basic.platform.menu.service.ModuleService;
import com.cako.basic.platform.user.entity.User;
import com.cako.basic.platform.user.service.IUserService;
import com.cako.basic.util.MessageObject;
import com.cako.basic.util.SystemContext;
import com.orm.commons.encryption.MD5Encryption;
import com.orm.commons.exception.LoginException;
import com.orm.commons.exception.ServiceException;
import com.orm.commons.utils.JsonMapper;

@Controller
@RequestMapping("/")
public class LoginController {
	private static Logger logger = Logger.getLogger("LoginController");
	@Resource
	private IUserService userService;
	
	@Autowired
	private ModuleService menuService;

	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	public String index(HttpServletRequest request, Model model, HttpSession session) {
		User user = (User) request.getSession().getAttribute("user");
		model.addAttribute("user", user);
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String id = request.getParameter("id");
			if (StringUtils.isEmpty(id)) {
				paramMap.put("name", "目录");
			}else {
				paramMap.put("module.id", id);
			}
			List<Module> menus = menuService.queryByMap(paramMap);
			List<Module> list = new ArrayList<Module>();
			for (Module menu : menus) {
				paramMap = new HashMap<String, Object>();
				paramMap.put("module.id", menu.getId().toString());
				List<Module> menuList = menuService.queryByMap(paramMap);
				for (Module menu2 : menuList) {
					String path = menu2.getPath();
					if (!StringUtils.equals("javascript:void(0)", path)) {
						menu2.setPath(request.getContextPath() + path);
					}
					list.add(menu2);
				}
			}
			model.addAttribute("list", list);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
		return "index";
	}

	@RequestMapping(value = { "/login", "/" }, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView login(String loginName, String password, Model model,HttpServletRequest request) throws LoginException, ServiceException {
		logger.info("进入了登陆的方法  : LoginController.login()");
		try {
			if (StringUtils.isEmpty(loginName)) {
				model.addAttribute("info", "");
				return new ModelAndView("login");
			} else {
				User user = userService.findUserByUsername(loginName);
				// 如果登陆成功
				if (user != null) {
					if (StringUtils.equals(user.getPassword(), MD5Encryption.MD5(password))) {
						System.out.println("...............:" + user.getRoles().size());
						setLogin(user.getLoginName(), user.getPassword());
						request.getSession().setAttribute("user", user);
						request.getSession().setAttribute(SystemContext.GLOBLE_USER_SESSION, user);
						request.getSession().setAttribute("loginName", user.getLoginName());
						String path = request.getContextPath() + "/index?SESSIONID="+request.getSession().getId().toUpperCase();
						return new ModelAndView(new RedirectView(path));
					} else {
						model.addAttribute("info", "你输入的密码不对");
						return new ModelAndView("login");
					}
				} else {
					model.addAttribute("info", "你输入的用户名");
					return new ModelAndView("login");
				}
			}
		} catch (ServiceException e) {
			throw new ServiceException("查询用户失败", new Throwable());
		}
	}

	private static final void setLogin(String userId, String password) {
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken(userId, password);
			token.setRememberMe(true);
			currentUser.login(token);
		}
	}

	@RequestMapping(value = "/logout",method = RequestMethod.GET)
	public void logout() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			subject.logout();
		}
	}
	
	@RequestMapping(value = { "/android"}, method = { RequestMethod.GET, RequestMethod.POST })
	public String android(String loginName, String password, Model model,HttpServletRequest request) throws LoginException, ServiceException {
		logger.info("进入了登陆的方法  : LoginController.android()");
		MessageObject message = new MessageObject();
		try {
			if (StringUtils.isEmpty(loginName)) {
				message.setResposeCode(MessageObject.ResposeCode.code_404);
				message.setResult("登陆失败");
				return new JsonMapper().toJson(message);
			} else {
				User user = userService.findUserByUsername(loginName);
				// 如果登陆成功
				if (user != null) {
					if (StringUtils.equals(user.getPassword(), MD5Encryption.MD5(password))) {
						setLogin(user.getLoginName(), user.getPassword());
						request.getSession().setAttribute("user", user);
						message.setInforamation(user.getLoginName());
						message.setResposeCode(MessageObject.ResposeCode.code_200);
						message.setResult("登陆成功");
						return new JsonMapper().toJson(message);
					} else {
						message.setResposeCode(MessageObject.ResposeCode.code_404);
						message.setResult("你输入的密码不对");
						return new JsonMapper().toJson(message);
					}
				} else {
					message.setResposeCode(MessageObject.ResposeCode.code_404);
					message.setResult("登陆名错误");
					return new JsonMapper().toJson(message);
				}
			}
		} catch (ServiceException e) {
			throw new ServiceException("查询用户失败", new Throwable());
		}
	}
}
