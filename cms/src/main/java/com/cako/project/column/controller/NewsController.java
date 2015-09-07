package com.cako.project.column.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cako.basic.platform.user.entity.User;
import com.cako.basic.util.ListTools;
import com.cako.basic.util.MessageObject;
import com.cako.basic.util.WebUtils;
import com.cako.basic.version.entity.Version;
import com.cako.basic.version.service.IVersionService;
import com.cako.project.column.entity.Column;
import com.cako.project.column.entity.News;
import com.cako.project.column.service.ColumnService;
import com.cako.project.column.service.NewsService;
import com.cako.project.column.tree.NewsClass;
import com.orm.commons.exception.ServiceException;
import com.orm.commons.utils.HtmlSplit;
import com.orm.commons.utils.JsonMapper;
import com.orm.commons.utils.PageSupport;
import com.orm.commons.utils.Pager;

@Controller
@RequestMapping("/news")
public class NewsController {

//	private static final Logger logger = Logger.getLogger("NewsController.class");
	
	private MessageObject message = new MessageObject();

	@Autowired
	private IVersionService versionService;

	@Autowired
	private ColumnService columnService;

	@Autowired
	private NewsService newsService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(HttpServletRequest request, Model model) {
		return "news/create";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody void save(HttpServletRequest request, HttpServletResponse response, News news) throws IOException {
		try {
			String content = request.getParameter("content");
			String columnId = request.getParameter("columnId");
			if (StringUtils.isNotEmpty(columnId)) {
				Column column = columnService.get(columnId);
				news.setColumn(column);
			}
			if (StringUtils.isEmpty(news.getId())) {
				HttpSession session = request.getSession(true); 
				User user = (User) session.getAttribute("user");
				news.setUser(user);
				news.setCreateTime(new Date());
			} else {
				news.setUpdateTime(new Date());
			}
			String versionIds = request.getParameter("versionIds");
			if (StringUtils.isNotEmpty(versionIds)) {
				List<Version> versions = versionService.getVersions(ListTools.toArrayList(versionIds));
				news.setVersions(versions);
				news.setContent(content);
			}
			newsService.save(news);
			message.setResposeCode(MessageObject.ResposeCode.code_200);
			message.setResult("新增新闻成功");
		} catch (ServiceException e) {
			message.setResposeCode(MessageObject.ResposeCode.code_404);
			message.setResult("新增新闻失败");
		} finally {
			response.setHeader("content-type", "text/html;charset=UTF-8");
			response.getWriter().write(new JsonMapper().toJson(message));
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list() {
		return "news/list";
	}

	@RequestMapping(value = "/getNewsJson.json", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getNewsJson(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String, Object> paramMap = WebUtils.getRequestToMap(request);
			long totalRecord = newsService.queryByMap(paramMap).size();
			String currentPage = request.getParameter("page");
			paramMap.remove(currentPage);
			Pager pager = new Pager((int) totalRecord, currentPage);
			PageRequest pageable = new PageRequest(pager.getCurrentPage() - 1, pager.getPageSize());
			PageSupport pageSupport = new PageSupport(pageable, new Sort(Sort.Direction.DESC, "createTime"));
			Page<News> pageInfo = newsService.queryPageByMap(paramMap, pageSupport);
			List<NewsClass> newsClasses = new ArrayList<NewsClass>();
			for (News news : pageInfo) {
				Column column = newsService.getColumnByNewsId(news.getId());
				List<Version> versions = newsService.getVersionList(news.getId());
				String content = news.getContent();
				content = new HtmlSplit(news.getContent(), null).doStartTag();
				news.setContent(content);
				newsClasses.add(new NewsClass(news, column, versions ));
			}
			String data = "{\"total\":" + totalRecord + ",\"rows\":" + new JsonMapper().toJson(newsClasses) + "}";
			return data;
		} catch (ServiceException e) {
		}
		return null;
	}
	
	
}
