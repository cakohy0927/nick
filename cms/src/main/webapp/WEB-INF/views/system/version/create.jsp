<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<%@taglib uri="http://file-upload.upload" prefix="file" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EDGE">
<link rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${ctx}/static/jquery-file-upload/css/jquery.fileupload-ui-noscript.css">
<link rel="stylesheet" href="${ctx}/static/jquery-file-upload/css/jquery.fileupload-ui.css">
<link rel="stylesheet" href="${ctx}/static/jquery-file-upload/css/jquery.fileupload.css">

<script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/ajaxfileupload.js"></script>

<script type="text/javascript" src="${ctx}/static/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-file-upload/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-file-upload/js/jquery.fileupload.js"></script>
<title>测试文件的上传</title>
<style type="text/css">
	.container{
		margin-top:50px;
	}
	.row>div{
		margin-top:20px;
		height:50px;
		line-height:50px;
	}
	.bar {
	    height: 22px;
	    margin-left: 330px;
	    margin-top: 35px;
	    background: green;
	}
	input[type="radio"], input[type="checkbox"] {
	    margin: 10px 9px 0;
	}
	#progress{
		margin-left: 80px;
	    margin-top: -63px;
	    width: 500px;
	}
</style>
<script type="text/javascript">
	var maxsize = 2 * 1024 * 1024;//2M
	var errMsg = "上传的附件文件不能超过2M！！！";
	var tipMsg = "您的浏览器暂不支持计算上传文件的大小，确保上传文件不要超过2M，建议使用IE、FireFox、Chrome浏览器。";
	var browserCfg = {};
	var ua = window.navigator.userAgent;
	if (ua.indexOf("MSIE") >= 1) {
		browserCfg.ie = true;
	} else if (ua.indexOf("Firefox") >= 1) {
		browserCfg.firefox = true;
	} else if (ua.indexOf("Chrome") >= 1) {
		browserCfg.chrome = true;
	}
	//获取文件的大小
	function checkfile() {
		try {
			var obj_file = document.getElementById("file-person");
			if (obj_file.value == "") {
				alert("请先选择上传文件");
				return;
			}
			var filesize = 0;
			if (browserCfg.firefox || browserCfg.chrome) {
				filesize = obj_file.files[0].size;
			} else if (browserCfg.ie) {
				var obj_img = document.getElementById('tempimg');
				obj_img.dynsrc = obj_file.value;
				filesize = obj_img.fileSize;
			} else {
				alert(tipMsg);
				return;
			}
			if (filesize == -1) {
				alert(tipMsg);
				return;
			} else if (filesize > maxsize) {
				alert(errMsg);
				return;
			} else {
				filesize = filesize/1024
				if(filesize > 1024){
					filesize = filesize /1024;
				}
				alert("文件大小符合要求,大小为：" + filesize);
				return;
			}
		} catch (e) {
			alert(e);
		}
	}
	$(document).ready(function() {
		$("#btn-upload").click(function() {
			$("#file-person").click();
		});
		
		//文件上传地址  
		//var url = 'http://localhost/index.php/upload/do_upload';  
		var url = '${ctx}/version/add';
		//初始化，主要是设置上传参数，以及事件处理方法(回调函数)  
		$('#fileupload').fileupload({
			autoUpload : true,//是否自动上传  
			url: '${ctx}/version/add',//上传地址  
			dataType : 'json',
			done : function(e,data) {//设置文件上传完毕事件的回调函数  
				$('#progress .bar').css('width','0%');
			},
			progressall : function(e, data) {//设置上传进度事件的回调函数  
				var progress = parseInt(data.loaded / data.total *60, 20);
				$('#progress .bar').css('width', progress + '%');
			}
		});
	});
</script>
</head>
<body>
	<div class="container">
		<form action="${ctx}/version/add" method="post" enctype="multipart/form-data">
			<div class="row">
				<div class="btn-group">
					<button id="btn-upload" class="btn btn-primary" type="button">选择文件</button>
					<button id="btn-submit" class="btn btn-success" type="submit">开始上传</button>
				</div>
				<img id="tempimg" dynsrc="" src="" style="display: none" /> 
				<input type="file" id="file-person" name="myfiles" style="display:none;" onchange="checkfile()">
			</div>
			<div class="row">
				<div class="col-md-4" style="background-color:#F2F2F2;border:1px solid rgba(53,44,124,.12)">.col-md-8</div>
				<div class="col-md-8" style="background-color:#F2F2F2;border:1px solid rgba(53,44,124,.12)">.col-md-4</div>
			</div>
		</form>
		<form action="${ctx}/version/add" method="post" enctype="multipart/form-data">
			<div class="row">
				<span class="btn btn-success fileinput-button"> 
					<i class="glyphicon glyphicon-plus"></i> 
					<span>选择文件</span> 
					<input id="fileupload" name="files[]" multiple="" type="file">
				</span>
				<button type="submit" class="btn btn-primary start">
					<i class="glyphicon glyphicon-upload"></i> 
					<span>开始上传</span>
				</button>
				<button type="reset" class="btn btn-warning cancel">
					<i class="glyphicon glyphicon-ban-circle"></i> 
					<span>取消上传</span>
				</button>
				<button type="button" class="btn btn-danger delete">
					<i class="glyphicon glyphicon-trash"></i> 
					<span>删除</span>
				</button>
				<input class="toggle" type="checkbox">
			</div>
			<div id="progress" >
			    <div class="bar" style="width: 0%;"></div>
			</div>
			
			<table role="presentation" class="table table-striped">
				<tbody class="files"></tbody>
			</table>
		</form>
		<file:upload/>
	</div>
</body>
</html>