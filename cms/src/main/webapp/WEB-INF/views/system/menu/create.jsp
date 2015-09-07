<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" type="text/css" href="${ctx}/static/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/bootstrap/css/bootstrap.css.map">
<link rel="stylesheet" type="text/css" href="${ctx}/static/ztree/css/zTreeStyle/zTreeStyle.css">

<script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<title>创建菜单</title>
<style type="text/css">
	.container-fluid{
		margin-top:20px;
	}
	.z-list{
		margin-left:-6px;
	}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#parentName").click(function (event) {
			var z_div = $(".z-div");
			z_div.css("display","block");
			$(this).after(z_div);
		});
	});
	var setting = {
		check: {
			enable: true,
			chkStyle: "radio",
			radioType: "all"
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onCheck: zTreeOnCheck
		}
	};
	function zTreeOnCheck(event, treeId, treeNode) {
		$("#parentId").val(treeNode.id);
		$("#parentName").val(treeNode.name);
	};
	var zNodes =${json};
	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	});
	
	function hidden_div() {
		var z_div = $(".z-div");
		z_div.css("display","none");
	}
	function clear_div(){
		$("#parentId").val('');
		$("#parentName").val('');
		var z_div = $(".z-div");
		z_div.css("display","none");
	}
</script>
</head>
<body>
	<div class="container-fluid">
		<form class="form-horizontal" action="${ctx}/menu/save" method="post">
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label">菜单名称</label>
				<div class="col-sm-4">
					<input type="text" name="name" class="form-control" id="name" placeholder="菜单名称">
				</div>
			</div>
			<div class="form-group">
				<label for="path" class="col-sm-2 control-label">菜单路径</label>
				<div class="col-sm-4">
					<input type="text" name="path" class="form-control" id="path" placeholder="菜单路径">
				</div>
			</div>
			<div class="form-group">
				<label for="sort" class="col-sm-2 control-label">排序号</label>
				<div class="col-sm-4">
					<input type="text" readonly="readonly" name="sort" value="${sort}" class="form-control" id="sort" placeholder="排序号">
				</div>
			</div>
			<div class="form-group">
				<label for="isDisplay" class="col-sm-2 control-label">排序号</label>
				<div class="col-sm-4">
					<input type="radio" name="isDisplay" value="1" <c:if test="${isDisplay == true}">checked="checked"</c:if> class="form-control">显示
					<input type="radio" name="isDisplay" value="0" <c:if test="${isDisplay == false}">checked="checked"</c:if> class="form-control">不显示
				</div>
			</div>
			<div class="form-group">
				<label for="parentName" class="col-sm-2 control-label">父级菜单</label>
				<div class="col-sm-4">
					<input type="text" name="parentName" class="form-control" readonly="readonly" id="parentName" placeholder="上级菜单" />
					<input type="hidden" name="parentId" id="parentId"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-2">
					<button type="submit" class="btn btn-default">提交</button>
				</div>
			</div>
		</form>
	</div>
	<div class="form-group col-sm-12 z-div" style="display: none;">
	   	<div>
	   		<ul id="treeDemo" class="ztree z-list"></ul>
	   	</div>
	   	<div style="width: 100%">
	   		<div style="float: right;">
		   		<a class="btn btn-primary" href="javascript:hidden_div()">确定</a>
		   		<a class="btn btn-default" href="javascript:clear_div();">关闭</a>
	   		</div>
	   	</div>
	</div>
</body>
</html>