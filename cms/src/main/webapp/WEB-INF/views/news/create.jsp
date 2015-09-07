<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib uri="http://file-upload.upload" prefix="file" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑器展示</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui/plugins/jquery.validatebox.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script src="${ctx}/static/ckeditor/ckeditor.js"></script>
<script src="${ctx}/static/ckeditor/config.js"></script>
<script type="text/javascript">
	function getValue() {
		$editor = CKEDITOR.instances.editor01.getData();
		alert($editor);
	}
	$(function() {
		$('input[type=text]').validatebox();
	});
	function saveNews() {
		var content = CKEDITOR.instances.editor01.getData();
		$("#content").val(content);
		var title = $("#title").val();
		if(title == ''){
			$.messager.alert('提示信息', '标题不能为空', 'warning');
			return;
		}
		if(content == ''){
			$.messager.alert('提示信息', '内容不能为空', 'warning');
			return;
		}
		
		var type = $("#cc").val();
		if(type==''){
			$.messager.alert('提示信息', '所属行业不能为空', 'warning');
			return false;
		}
		$('#saveNews').form('submit', {
			url : '${ctx}/news/save',
			onSubmit : function() {
			},
			success : function(data) {
				data = $.parseJSON(data);
				if (data.resposeCode == 200) {
					alert(data.result);
					window.close();
				} else {
					$.messager.alert('提示信息', data.result, 'error');
					return;
				}
			}
		});
	}
	function getModule() {
		$('#cc').combotree({
			url:"${ctx}/column/getColumnList",
			method:'GET',
			animate:true,
			lines : true,
			checkbox:true,
			onBeforeExpand:function(node,param){  
				$('#cc').combotree('options').url = "${ctx}/column/getColumnList?id=" + node.id;
	        }
		});
	}
</script>
<style type="text/css">
	.textbox {
		width:400px;
		height:30px;
		line-height:30px;
		padding:5px;
		border-radius:5px;
	}
	#cc{
		width:400px;
	}
	select{
		height:25px;
		line-height:25px;
		width:400px;
	}
	#saveNews>div{
		font-size:14px;
		margin-top: 20px;
	}
	#saveNews >div>label {
		width:80px !important;
	}
	.tree {
	    list-style-type: none;
	    margin: -15px;
	    padding: 0;
	}
</style>
</head>
<body>
	<form action="" method="post" style="margin: 15px;" id="saveNews">
		<input id="versionIds" type="hidden" name="versionIds">
		<textarea rows="" style="display: none;" cols="" name="content" id="content"></textarea>
		<div>   
	        <label for="name">标题:</label>   
	        <input missingMessage="不能为空" invalidMessage="必填项" class="easyui-validatebox textbox" type="text" name="title" id="title" data-options="required:true" />
	    </div>   
      	<div>
			<label for="parentName">栏目名称</label>
			<select id="cc" missingMessage="不能为空" invalidMessage="必填项" onclick="getModule()" name="columnId" id="columnId" style="width:400px;height:28px;line-height: 28px;" class="easyui-validatebox textbox"></select>
		</div>
      	<div>
      		<label for="name">内容:</label>
			<textarea rows="30" cols="50" id="editor01" name="editor01" placeholder="请输入内容."></textarea>
			<script type="text/javascript">
				CKEDITOR.replace('editor01');
			</script>
      	</div>		       	
	</form>
	<div style="margin: 15px;">   
        <label for="name">上传图片:</label>   
        <file:upload/>
    </div> 
    <div style="margin: 15px;">
    	<a class="btn btn-primary btn-small" onclick="saveNews()">保存</a><a style="margin-left: 10px;" class="btn btn-small btn-warning">取消</a>
    </div>
</body>
</html>