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
<title>角色列表</title>
<style type="text/css">
	.textbox {
		width:180px;
		height:18px;
		line-height:18px;
		padding:3px;
		border-radius:5px;
	}
	#dlg{
		display:none;
	}
</style>
</head>
<body>
  	<div id="search">
		<a href="javascript:void(0)" onclick="openDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="edit()" iconCls="icon-edit" plain="true">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="deleteInfo()" iconCls="icon-remove" plain="true">删除</a>
		<div style="float:right;">
			<input id="condition" class="easyui-searchbox" data-options="prompt:'搜索'" style="width:230px;vertical-align:middle;" />
       		<a href="#" class="easyui-linkbutton" plain="true">高级检索</a>
		</div>
   	</div>
    <table id="list" class="easyui-datagrid"></table>
</body>
<script type="text/javascript">
	function deleteInfo() {
		var ids = getIds();
		$.messager.alert("提示信息", ids, 'info');
	}
	
	function edit(){
		var ids = getIds(1);
		if(ids != undefined){
			$.messager.alert("提示信息", ids, 'info');
		}
	}

	function getIds(flag) {
		var ids = "";
		var rows = $('#list').datagrid('getChecked');
		if (rows.length == 0) {
			$.messager.alert('提示信息', '请选择需要操作的行数据', 'warning');
			return;
		} else {
			if (flag == 1) {
				if (rows.length > 1) {
					$.messager.alert('提示信息', '不能选择多行数据进行编辑', 'warning');
					return;
				} else {
					ids = rows[0].id;
					return ids;
				}
			} else {
				for (var i = 0; i < rows.length; i++) {
					ids += rows[i].id + ",";
				}
				return ids;
			}
		}
	}
	
	function init(condition) {
		$('#list').datagrid({
			title : '用户列表',
			pagesize : 20, //默认10 
			autoRowHeight : true,
			striped : true,
			collapsible : false,
			url : '${ctx}/news/getNewsJson.json', //服务器地址,返回json格式数据
			loadMsg : '数据加载中......',
			fitColumns : true,//允许表格自动缩放,以适应父容器  
			pagination : true,//分页控件 
			idField : 'id',
			frozenColumns : [ [ {
				field : 'ck',
				checkbox : true
			}, ] ],
			columns : [ [ {
				field : 'id',
				hidden : true,
				width : 100
			}, {
				field : 'columnName',
				width : 100,
				title : '栏目名称',
				sortable:true
				
			}, {
				field : 'title',
				width : 200,
				title : '标题'
			}, {
				field : 'content',
				width : 400,
				title : '内容'
			}, {
				field : 'opt',
				width: 50,
				align:'center',
				title : '操作',
				formatter: function(value,row,index){
					var str = "";
					if(row.deployStatus == 1){
						str = "<a href=\"javascript:void(0)\" class=\"l-btn-disabled\" disabled=\"disabled\">启用</a>&nbsp;&nbsp;<a href=\"javascript:void(0)\">停用</a>";
					}else {
						str = "<a href=\"javascript:void(0)\">启用</a>&nbsp;&nbsp;<a href=\"javascript:void(0)\" class=\"l-btn-disabled\" disabled=\"disabled\">停用</a>"
					}
					
					return str;
				}
			}]],
			pagination : true, //分页控件
			toolbar : "#search"
		});
	}
	function openDialog(){
		var height = window.screen.height;
		var width = window.screen.width / 2;
		window.open("${ctx}/news/create", '添加新闻信息', "height="+height+", width="+(width + 200)+", top=0, left="+(width/2 - 100)+",scrollbars=1, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
	}
	$(function() {
		init();
		$(".panel").attr("style", "width:100%");
		$(".panel-header").attr("style", "width:auto");
		$(".panel-body").attr("style", "width:auto");
		$(".datagrid-header-inner").attr("style", "width:auto");
		$('input[type=text]').validatebox();
	}); 
</script>
</html>