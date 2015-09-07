<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui/plugins/jquery.validatebox.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<title>列表</title>
<style type="text/css">
	#addForm div{
		font-size:14px;
		margin:10px 50px 10px 80px;
	}
	#addForm >div>label {
		width:80px !important;
	}
	.textbox {
		width:200px;
		height:18px;
		line-height:18px;
		padding:3px;
		border-radius:5px;
	}
	select{
		height:25px;
		line-height:25px;
	}
</style>
</head>
<body>
   <div id="search">
		<a href="javascript:void(0);" onclick="addModule()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		<div style="float:right;">
			<input id="condition" class="easyui-searchbox" data-options="prompt:'搜索'" style="width:230px;vertical-align:middle;" />
	       		<a href="#" class="easyui-linkbutton" plain="true">高级检索</a>
		</div>
    </div>
    <table id="list_data" cellspacing="0" cellpadding="0"> 
    </table>
	<div id="dlg" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"   
		        data-options="iconCls:'icon-save',resizable:true,modal:true"> 
		<form action="" method="post" id="addForm" class="easyui-form">
			<div>
				<label for="name">菜单名称</label>  
				<input missingMessage="不能为空" invalidMessage="必填项" class="easyui-validatebox textbox" type="text" name="name" id="name" data-options="required:true" />
			</div>
			<div>
				<label for="path">菜单路径</label>
				<input missingMessage="不能为空" invalidMessage="必填项" class="easyui-validatebox textbox" type="text" name="path" id="path" data-options="required:true"/>
			</div>
			<div>
				<label for="path">菜单别名</label>
				<input missingMessage="不能为空" invalidMessage="必填项" class="easyui-validatebox textbox" type="text" name="permission" id="permission" data-options="required:true"/>
			</div>
			<div>
				<label for="path">菜单别名</label>
				<input type="radio" name="isDisplay" value="1" <c:if test="${isDisplay == true}">checked="checked"</c:if> class="">显示
				<input type="radio" name="isDisplay" value="0" <c:if test="${isDisplay == false}">checked="checked"</c:if> class="">不显示
			</div>
			<div>
				<label for="parentName">父级菜单</label>
				<select id="cc" missingMessage="不能为空" invalidMessage="必填项" onclick="getModule()" name="parentId" id="parentId" style="width:210px;height:25px;line-height: 25px;" class="easyui-validatebox textbox" data-options="required:true"></select>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
	
	function getModule() {
		$('#cc').combotree({
			url:"${ctx}/menu/getJson",
			method:'GET',
			animate:true,
			lines : true,
			checkbox:true,
			onBeforeExpand:function(node,param){  
				$('#cc').combotree('options').url = "${ctx}/menu/getMenuJson?id=" + node.id;
            }
		});
	}
	var sort = '${sort}';
	function addModule() {
		$("#addForm").form("clear");
		$("#dlg").dialog("open");
		$('#dlg').dialog({
			title : '菜单操作',
			width : 460,
			height : 300,
			draggable : false,
			closed : false,
			resizable : false,
			modal : true,
			buttons : [ {
				text : '保存',
				handler : function() {
					$('#addForm').form('submit', {
						url : '${ctx}/menu/save',
						onSubmit : function() {
							var permission = $("#permission").val();
							var parent=/^[A-Za-z]+$/;
							if(!parent.test(permission)) {
							  	$.messager.alert('提示信息','别名只能是英文字母','warning');
							  	return false;
							}
							return $("#addForm").form('validate')
						},
						success : function(data) {
							data = $.parseJSON(data);
							if (data.resposeCode == 200) {
								init();
								window.location.reload();
								$('#dlg').dialog('close');
								$("#dlg").attr("display", "none");
								$.messager.alert('提示信息', data.result, 'info');
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

	function init(condition) {
		$('#list_data').treegrid({
			url : '${ctx}/menu/getListJson.json?condition='+ condition +"&flag = 1", //服务器地址,返回json格式数据
			title : '菜单列表',
			width : 700,
			idField:'id',
		    method:'POST',
		    treeField:'text',
			pagesize : 1, //默认10 
			nowrap : true,
			autoRowHeight : true,
			striped : true,
			collapsible : false,
			loadMsg : '数据加载中......',
			fitColumns : true,//允许表格自动缩放,以适应父容器  
			singleSelect : false,//是否单选 
			pagination : true,//分页控件 
			frozenColumns : [ [ {
				field : 'ck',
				checkbox : true
			}, ] ],
			onBeforeExpand:function(row){
	    	 	if(row){
			    	$('#list_data').treegrid('options').url = "${ctx}/menu/getMenuJson?id=" + row.id+"&flag=1";
		    	}
		    },
			columns : [ [
					{ field : 'text', width : 150, title : '名称' },
					{ field : 'uri', width : 150, title : '路径' },
					{ field : 'permission', width : 150, title : '英文名称' },
					{ field : 'isDisable', width : 150, title : '是否可用',formatter : function(value, row, index){
						return row.isDisable == 1 ? '可用' : '不可用';
					} },
					{ field : 'isDisplay', width : 150, title : '是否显示',formatter : function(value, row, index){
						return row.isDisplay == 1 ? '显示' : '不显示';
					} }/* ,
					{ field : 'opt', width : 150, title : '操作', align : 'center', formatter : function(value, rec, index) {
							return '<span style="color:red"><a href="#" onclick="testSave(' + rec.id + ')">修改</a> <a href="javascript:void(0)">删除</a></span>';
						}
					} */ ] ],
			pagination : true, //分页控件
			toolbar : "#search"
		});
		$(".panel").attr("style", "width:100%");
		$(".panel-header").attr("style", "width:auto");
		$(".panel-body").attr("style", "width:auto");
		$(".datagrid-header-inner").attr("style", "width:auto");
		$("#dlg").dialog("close");
	}
	$(function() {
		init(condition);
		$('#condition').searchbox({
			width : 260,
			searcher : function(value, name) {
				init(value);
			},
			prompt : '搜索'
		});
	});
</script>
</html>