package com.cako.project.column.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cako.basic.util.MessageObject;
import com.cako.project.column.entity.Column;
import com.cako.project.column.service.ColumnService;
import com.cako.project.column.tree.ColumnClass;
import com.orm.commons.exception.ServiceException;
import com.orm.commons.utils.JsonMapper;

@Controller
@RequestMapping("/column")
public class ColumnController {

	@Autowired
	private ColumnService columnService;

	private MessageObject message = new MessageObject();

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public void save(HttpServletResponse response, HttpServletRequest request, Column column) {
		try {
			String parentId = request.getParameter("parentId");
			if (StringUtils.isNotEmpty(parentId)) {
				column.setColumn(columnService.get(parentId));
				column.setFlag(true);
			}
			if (!StringUtils.isEmpty(column.getId())) {
			}
			columnService.save(column);
			message.setResposeCode(MessageObject.ResposeCode.code_200);
			message.setResult("添加栏目信息成功");
		} catch (ServiceException e) {
			message.setResposeCode(MessageObject.ResposeCode.code_404);
			message.setResult("添加栏目信息失败");
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
	public String list() {

		return "column/list";
	}

	@RequestMapping(value = "columnList.json", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody void columnList(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			List<Column> pageInfo = columnService.getColumnList(id);
			List<ColumnClass> lists = new ArrayList<ColumnClass>();
			for (Column column : pageInfo) {
				List<Column> columns = columnService.getColumnList(column.getId());
				column.setColumn(columnService.getColumn(column.getId()));
				lists.add(new ColumnClass(column, columns != null && columns.size() > 0 ? "closed" : "open"));
			}
			try {
				response.setHeader("content-type", "text/html;charset=UTF-8");
				response.getWriter().write(new JsonMapper().toJson(lists));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "getColumnList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void getColumnList(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			List<Column> columns = columnService.getColumnList(id);
			List<ColumnClass> columnClasses = new ArrayList<ColumnClass>();
			for (Column column : columns) {
				List<Column> cList = columnService.getColumnList(column.getId());
				column.setColumn(columnService.getColumn(column.getId()));
				columnClasses.add(new ColumnClass(column, cList != null && cList.size() > 0 ? "closed" : "open"));
			}
			response.setHeader("content-type", "text/html;charset=UTF-8");
			response.getWriter().write(new JsonMapper().toJson(columnClasses));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
