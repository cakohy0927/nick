<%@page import="com.cako.basic.platform.user.entity.User"%>
<%@page import="com.cako.basic.util.SystemContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx" ></c:set>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui/plugins/jquery.validatebox.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/static/bootstrap/js/bootstrap.js"></script>
<title>流程列表</title>
<style type="text/css">
	.textbox {
		width:200px;
		height:25px;
		line-height:25px;
		padding:3px;
		border-radius:5px;
		margin-left:10px;
	}
	#addForm>div {
		margin:10px;
	}
</style>
<script type="text/javascript">
	function apply(id){
		$.get('${ctx}/askLeave/apply?id='+id,function(data){
			data = $.parseJSON(data);
			if (data.resposeCode == 200) {
				$('#addUser').dialog('close');
				$("#addUser").attr("display", "none");
				$.messager.alert('提示信息', data.result, 'info');
				window.location.reload();
			} else {
				$.messager.alert('提示信息', data.result, 'error');
				return;
			}
		});
	}
</script>
</head>
<body>
	<div id="search">
		<div style="float:right;">
			<input id="condition" class="easyui-searchbox" data-options="prompt:'搜索'" style="width:230px;vertical-align:middle;" />
	       		<a href="#" class="easyui-linkbutton" plain="true">高级检索</a>
		</div>
    </div>
	<div class="container-fluid" style="margin-top:15px;" >
	 	<table id="list_data" class="easyui-datagrid" data-options="frozenColumns:[[{field : 'ck',checkbox : true}]],fitColumns:true,toolbar:'#search'">   
		 	<thead>   
		        <tr>   
		        	<th data-options="field:'id',width:100">事项编号</th>
		            <th data-options="field:'name',width:100">办理事项</th>   
		            <th data-options="field:'dueDate',width:100">审核时间</th>  
		            <th data-options="field:'assignee'">办理人</th> 
		            <th data-options="field:'opt',width:130">操作</th>
		        </tr>   
	    	</thead> 
	    	<tbody>
	    		<c:forEach items="${list}" var="leave">
	    			<tr>
	    				<td></td>
		    			<td>${leave.name}</td>
		    			<td>${leave.id}</td>
		    			<td><fmt:formatDate value="${leave.dueDate}"  type="date" dateStyle="full"/> </td>
		    			<td>${leave.assignee}</td>
		    			<td>
		    				<a href="javascript:void(0)">审核</a>
		    				<a href="javascript:void(0)">查看</a>
		    			</td>
	    			</tr>
	    		</c:forEach>
	    	</tbody>  
    	</table>
	</div>
</body>
</html>