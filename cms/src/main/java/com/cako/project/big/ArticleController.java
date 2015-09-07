package com.cako.project.big;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cako.basic.util.MessageObject;
import com.cako.project.column.entity.News;
import com.cako.project.column.service.NewsService;
import com.orm.commons.exception.ServiceException;
import com.orm.commons.utils.JsonMapper;

@Controller
@RequestMapping("/big")
public class ArticleController {

	@Autowired
	private NewsService newsService;
	@Autowired
	private DataBaseIndexer dataBaseIndexer;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String content = request.getParameter("content");
			if (StringUtils.isNotEmpty(content)) {
				List<String> ids = dataBaseIndexer.searcher(content);
				if (ids.size() > 0) {
					paramMap.put("id_in", ids);
				}
			}
			List<News> list = newsService.queryByMap(paramMap, new Sort(Sort.Direction.DESC, "createTime"));
			model.addAttribute("list", list);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "big/article/list";
	}

	@RequestMapping(value = "/viewInfo/{id}", method = RequestMethod.GET)
	public String viewInfo(@PathVariable("id") String id, HttpServletRequest request, Model model) {
		try {
			if (StringUtils.isNotEmpty(id)) {
				News news = newsService.get(id);
				model.addAttribute("news", news);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return "big/article/info";
	}

	@RequestMapping(value = "/newsList", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String newsList(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<NewsClass> newsClasses = new ArrayList<NewsClass>();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String content = request.getParameter("content");
			if (StringUtils.isNotEmpty(content)) {
				List<String> ids = dataBaseIndexer.searcher(content);
				if (ids.size() > 0) {
					paramMap.put("id_in", ids);
				}
			}
			List<News> list = newsService.queryByMap(paramMap);
			for (int i = 0; i < list.size(); i++) {
				News news = list.get(i);
				newsClasses.add(new NewsClass(news));
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		System.out.println(new JsonMapper().toJson(newsClasses));
		return new JsonMapper().toJson(newsClasses);
	}

	@RequestMapping(value = "createIndex", method = RequestMethod.GET)
	public @ResponseBody void createIndex(HttpServletResponse response) {
		try {
			dataBaseIndexer.createIndex();
			MessageObject message = new MessageObject();
			message.setResposeCode(MessageObject.ResposeCode.code_200);
			response.getWriter().write(new JsonMapper().toJson(message));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
