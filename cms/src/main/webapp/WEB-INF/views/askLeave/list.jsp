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
	$(document).ready(function(){
		$("#dlg").dialog("close");
	});
	function addModule(){
		$("#dlg").dialog("open");
		$("#days").val("");
		$("#resean").val('');
		$('#dlg').dialog({
			title : '填写申请单',
			width : 460,
			height : 400,
			draggable : false,
			closed : false,
			resizable : false,
			modal : true,
			buttons : [ {
				text : '保存',
				handler : function() {
					$('#addForm').form('submit', {
						url : '${ctx}/askLeave/save',
						onSubmit : function() {
						},
						success : function(data) {
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
						}
					});
				}
			}, {
				text : '关闭',
				handler : function() {
					$("#addForm").form("clear");
					$('#dlg').dialog('close');
				}
			} ]
		});
	}
	
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
	<%
		User user = SystemContext.get();
	%>
	<div id="dlg" class="easyui-dialog" data-options="iconCls:'icon-save',resizable:true,modal:true">
		<div style="margin:5px;">
			<form action="" id="addForm" method="post">
				<div>
					<label for="days">申请人</label>  
					<input type="hidden" name="userId" id="userId" value="<%=user.getId()%>">
					<input class="easyui-validatebox textbox" type="text" style="margin-left:22px;" value="<%=user.getRealName()%>" disabled="disabled" name="username" id="username"/>
				</div>
				<div>
					<label for="days">请假天数</label>  
					<input missingMessage="不能为空" invalidMessage="必填项" class="easyui-validatebox textbox" type="text" name="days" id="days" data-options="required:true" />
				</div>
				<div>
					<label for="days">请假原因</label>  
					<textarea rows="" cols="" style="width:415px;height:100px;" name="resean" id="resean"></textarea>
				</div>
				<div>
					<label for="comment">备注</label>  
					<textarea rows="" cols="" style="width:415px;height:50px;" name="comment" id="comment"></textarea>
				</div>
			</form>
		</div>
    </div>
	<div id="search">
		<a href="javascript:void(0);" onclick="addModule()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		<div style="float:right;">
			<input id="condition" class="easyui-searchbox" data-options="prompt:'搜索'" style="width:230px;vertical-align:middle;" />
	       		<a href="#" class="easyui-linkbutton" plain="true">高级检索</a>
		</div>
    </div>
	<div class="container-fluid" style="margin-top:15px;" >
	 	<table id="list_data" class="easyui-datagrid" data-options="frozenColumns:[[{field : 'ck',checkbox : true}]],fitColumns:true,toolbar:'#search'">   
		 	<thead>   
		        <tr>   
		            <th data-options="field:'username',width:100">请假人</th>   
		            <th data-options="field:'days',width:50">天数</th>   
		            <th data-options="field:'resean',width:450">请假原因</th> 
		             <th data-options="field:'createTime'">提交申请时间</th> 
		            <th data-options="field:'comment'">备注</th> 
		            <th data-options="field:'state',width:100">审核状态</th>  
		            <th data-options="field:'opt',width:130">操作</th>
		        </tr>   
	    	</thead> 
	    	<tbody>
	    		<c:forEach items="${pageInfo}" var="leave">
	    			<tr>
	    				<td></td>
		    			<td>${leave.username}</td>
		    			<td>${leave.days}</td>
		    			<td>${leave.resean}</td>
		    			<td><fmt:formatDate value="${leave.updateTime}"  type="date" dateStyle="full"/> </td>
		    			<td>${leave.comment}</td>
		    			<td>
		    				<c:if test="${leave.state==0}">初始录入</c:if>
		    				<c:if test="${leave.state==1}">审核中</c:if>
		    				<c:if test="${leave.state==2}">审核通过</c:if>
		    			</td>
		    			<td>
		    				<c:if test="${leave.state==0}">
		    					<a href="javascript:void(0)">修改</a>
		    				</c:if>
		    				<c:if test="${leave.state==1}">
			    				<a href="javascript:void(0)">审核</a>
		    				</c:if>
		    				<c:if test="${leave.state==2 || leave.state == 0}">
		    					<a href="javascript:void(0)">删除</a>
		    				</c:if>
		    				<c:if test="${leave.state==0}">
		    					<a href="javascript:void(0)"  onclick="apply('${leave.id}')">申请请假</a>
		    				</c:if>
		    				<a href="javascript:void(0)">查看</a>
		    			</td>
	    			</tr>
	    		</c:forEach>
	    	</tbody>  
    	</table>
	</div>
</body>
</html>