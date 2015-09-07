<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx" ></c:set>
<%@ taglib uri="http://file-upload.upload" prefix="file"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.css">
<script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/bootstrap/js/bootstrap.js"></script>
<title>流程列表</title>
<script type="text/javascript">
	function deleteProcess(id) {
		$.get("${ctx}/activiti/deleteDeploy?id="+id,function(data){
			data = eval("(" + data + ")");
			if(data.resposeCode==200){
				alert(data.result);
				window.location.reload(true);
			}else{
				alert(data.result);
				return;
			}
		});
	}
</script>
</head>
<body>
	<div class="container-fluid" style="margin-top:15px;">
		<file:activiti/>
		<br>
		<table class="table table-striped table-bordered table-hover">
			<tr>
       			<td>编号</td>
       			<td>名称</td>
       			<td>版本</td>
       			<td>资源名称</td>
       			<td>流程定义KEY</td>
       			<td>资源名称</td>
       			<td>图片名称</td>
       			<td>流程部署ID</td>
       			<td>操作</td>
       		</tr>
       		<c:forEach items="${processDefinitionList}" var="process" varStatus="s">
       			<tr>
       				<td>${s.count}</td>
       				<td>${process.name}</td>
       				<td>${process.version}</td>
       				<td>${process.name}</td>
       				<td>${process.key}</td>
       				<td>${process.resourceName}</td>
       				<td>${process.diagramResourceName}</td>
       				<td>${process.deploymentId}</td>
       				<td>
       					<a href="${ctx}/activiti/startTask?id=${process.id}">启动</a>
       					<a href="${ctx}/activiti/viewImage?deploymentId=${process.deploymentId}&imageName=${process.diagramResourceName}">查看流程图</a>
       					<a onclick="deleteProcess('${process.deploymentId}')" href="javascript:void(0)">删除流程</a>
       				</td>
       			</tr>
       		</c:forEach>
		</table>
		<br>
		<table class="table table-striped table-bordered table-hover">
			<tr>
       			<td>编号</td>
       			<td>发布名称</td>
       			<td>发布时间</td>
       		</tr>
       		<c:forEach items="${dpList}" var="deploy" varStatus="s">
       			<tr>
       				<td>${s.count}</td>
       				<td>${deploy.name}</td>
       				<td><fmt:formatDate value="${deploy.deploymentTime}" pattern="yyyy-dd-mm HH:mm"/></td>
       			</tr>
       		</c:forEach>
    	</table>
	</div>
</body>
</html>