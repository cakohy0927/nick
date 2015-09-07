package com.cako.basic.version.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cako.basic.util.FileTools;
import com.cako.basic.util.MessageObject;
import com.cako.basic.version.entity.Version;
import com.cako.basic.version.service.IVersionService;
import com.orm.commons.utils.JsonMapper;

@Controller
@RequestMapping(value = "/version")
public class VersionController {

	@Autowired
	private IVersionService versionService;

	private MessageObject message = new MessageObject();
	
	@RequestMapping(value = "/create")
	public String create(HttpServletRequest request, Model model) {

		return "system/version/index";
	}

	@RequestMapping(value = "/copy", method = RequestMethod.POST)
	@ResponseBody
	public String copy(@RequestParam MultipartFile[] myfiles, MultipartHttpServletRequest request, HttpServletResponse response){
		String activiti = request.getParameter("activiti");
		String realPath = "";
		if (StringUtils.isNotEmpty(activiti)) {
			realPath = request.getSession().getServletContext().getRealPath("/upload") + File.separatorChar + activiti;
		}else {
			realPath = request.getSession().getServletContext().getRealPath("/upload/temp");
		}
		if (!new File(realPath).exists()) {
			new File(realPath).mkdirs();
		}
		
		Iterator<String> iterator = request.getFileNames();
		List<Version> versions = new ArrayList<Version>();
		while (iterator.hasNext()) {
			MultipartFile multipartFile = request.getFile((String) iterator.next());
			String type = multipartFile.getContentType();
			String name = multipartFile.getOriginalFilename();
			float size = (float) (multipartFile.getSize() / (1024));
			String fileSize = "";
			if (size > 1024 && size < 1024 * 1024) {
				fileSize = (size / 1024) + "MB";
			} else if (size >= 1024 * 1024) {
				fileSize = (size / 1024 / 1024) + "GB";
			}else {
				fileSize = size + "KB";
			}
			String path = realPath + File.separatorChar + name;
			Version version = new Version(name, new File(path).getPath(), type, fileSize,FileTools.getFileType(path));
            try {
				FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), new File(realPath,multipartFile.getOriginalFilename()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			versions.add(version);
		}
		return new JsonMapper().toJson(versions);
	}

	
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public String fileUpload(HttpServletRequest request,HttpServletResponse response){
		String realPath = request.getSession().getServletContext().getRealPath("/upload");
		if (!new File(realPath).exists()) {
			new File(realPath).mkdirs();
		}
		String versionIds = "";
		String[] values = request.getParameterValues("myfiles");
		if (values != null && values.length > 0) {
			for (String filePath : values) {
				File file = new File(filePath);
				try {
					FileInputStream fin = new FileInputStream(file);
					long files = FileTools.getFileSizes(file);
					String fileSize = FileTools.formetFileSize(files);//文件的大小
					String type = FileTools.getFileExtension(file);//文件的后缀
					Version version = new Version(file.getName(), "/upload/" + file.getName(), type, fileSize);
					version = versionService.save(version);
		            FileUtils.copyInputStreamToFile(fin, new File(realPath,file.getName()));
		            versionIds += version.getId() + ",";
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (StringUtils.isNotEmpty(versionIds)) {
				versionIds = versionIds.substring(0, versionIds.length() - 1);
			}
			message.setObject(versionIds);
			message.setResposeCode(MessageObject.ResposeCode.code_200);
			message.setResult("上传成功");
		}else {
			message.setResposeCode(MessageObject.ResposeCode.code_403);
			message.setResult("请先选择文件，再上传");
		}
		return new JsonMapper().toJson(message);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String addUser(@RequestParam MultipartFile[] myfiles, MultipartHttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String realPath = request.getSession().getServletContext().getRealPath("/upload");
		if (!new File(realPath).exists()) {
			new File(realPath).mkdirs();
		}
		Iterator<String> iterator = request.getFileNames();
		List<Version> versions = new ArrayList<Version>();
		while (iterator.hasNext()) {
			MultipartFile multipartFile = request.getFile((String) iterator.next());
			String type = multipartFile.getContentType();
			String name = multipartFile.getOriginalFilename();
			float size = (float) (multipartFile.getSize() / (1024));
			String fileSize = "";
			if (size > 1024 && size < 1024 * 1024) {
				fileSize = (size / 1024) + "MB";
			} else if (size >= 1024 * 1024) {
				fileSize = (size / 1024 / 1024) + "GB";
			}else {
				fileSize = size + "KB";
			}
			Version version = new Version(name, "/upload/" + name, type, fileSize);
			version = versionService.save(version);
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), new File(realPath,multipartFile.getOriginalFilename()));
			versions.add(version);
		}
		MessageObject message = new MessageObject();
		message.setObject(versions);
		message.setResposeCode(MessageObject.ResposeCode.code_200);
		message.setResult("上传成功");
		return new JsonMapper().toJson(message);
	}
}
