<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
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
<script type="text/javascript">
	$(document).ready(function(){
		var uploader = $("#fileupload-file");
		uploader.fileupload({
		    url : "${ctx}/user/add",
		    dataType : 'json',
		    autoUpload : false,
		    maxNumberOfFiles : 5,
		    fileInput : uploader.find("input:file"),
		    maxFileSize : 5000000,
		    previewMaxWidth : 200,
		    previewMaxHeight : 200
		});
		uploader.bind('fileuploaddone', function(e, data) {
		    var json = data.result;
		    /* alert(json.userdata.fileRealName);
		    alert(json.userdata.id); */
		    alert(json.userdata.name);
		    alert(json.userdata.id);
		});
		uploader.bind('fileuploadfailed', function(e, data) {
		    //eva.p(data);
		});
		uploader.bind('fileuploadadded', function(e, data) {
		    if (!data.files.valid) {
		        //uploader.find('.files .cancel').click();
		    }
		});
		uploader.bind('fileuploadchange', function(e, data) {
		    //uploader.find('.files').empty();
		});
	});
</script>
</head>
<body>
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-success">
				<div class="panel-heading">
					<h3 class="panel-title">附件上传</h3>
				</div>
				<div class="panel-body">
					<br>
					<form id="fileupload-file" method="post"
						enctype="multipart/form-data" action="${ctx}/version/add">
						<div class="row fileupload-buttonbar">
							<div class="col-lg-7">
								<span class="btn btn-success fileinput-button"> <i
									class="glyphicon glyphicon-plus"></i> <span>选择文件</span> <input
									type="file" name="attachments[]" multiple>
								</span>
								<button type="submit" class="btn btn-primary start">
									<i class="glyphicon glyphicon-upload"></i> <span>开始上传</span>
								</button>
								<button type="reset" class="btn btn-warning cancel">
									<i class="glyphicon glyphicon-ban-circle"></i> <span>全部取消</span>
								</button>
								<button type="button" class="btn btn-danger delete">
									<i class="glyphicon glyphicon-trash"></i> <span>删除</span>
								</button>
								<input type="checkbox" class="toggle"> <span
									class="fileupload-process"></span>
							</div>
							<div class="col-lg-5 fileupload-progress fade">
								<div class="progress progress-striped active" role="progressbar"
									aria-valuemin="0" aria-valuemax="100">
									<div class="progress-bar progress-bar-success"
										style="width: 0%;"></div>
								</div>
								<div class="progress-extended">&nbsp;</div>
							</div>
						</div>
						<table role="presentation" class="table table-striped">
							<tbody class="files"></tbody>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>