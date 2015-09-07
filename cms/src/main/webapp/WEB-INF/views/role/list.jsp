<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
<script type="text/javascript" src="${ctx}/static/ztree/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui/plugins/jquery.validatebox.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>

<link rel="stylesheet" href="${ctx}/static/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${ctx}/static/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<SCRIPT type="text/javascript">
	var setting = {
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};
	var zNodes =${moduleJson};
	$(document).ready(function(){
		init(condition);
		$('#condition').searchbox({
			width : 260,
			searcher : function(value, name) {
				init(value);
			},
			prompt : '搜索'
		});
		$('#createDialog').dialog('close');
		$('input[type=text]').validatebox();
	});
	
	function giveRoles(){
		var ids = getIds();
		$("#roleIds").val(ids);
		if(ids!=''){
			var array = new Array();
			array = ids.split(",");
			if(array.length == 1){
				$.get("${ctx}/role/getModuleIds?id="+ids,function(data){
					data = eval("(" + data + ")");
					$.fn.zTree.init($("#treeDemo"), setting, zNodes);
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					var nodes = treeObj.transformToArray(treeObj.getNodes());
					for(var j = 0;j < data.length;j++){
						for (var i=0 ; i < nodes.length; i++) {
							if (nodes[i].id == data[j]) {
								treeObj.checkNode(nodes[i], true, true);
							}
						}
					}
				});
			}
		}
		if (ids != '' && ids != undefined){
			$('#giveDialog').dialog({    
			    title: '选择模块',    
			    width: 300,    
			    height: 400,   
			    draggable:false,
			    closed: false,  
			    resizable:false,
			    modal: true,
			    buttons:[{
					text:'保存',
					handler:function(){
						var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
						var nodes = treeObj.getCheckedNodes(true);
						var s = '';
						for (var i = 0; i < nodes.length; i++) {
							if (s != '')
								s += ',';
							s += nodes[i].id;
						}
						$("#moduleIds").val(s);
						$.ajax({
							method: 'POST',
							url: '${ctx}/role/updateModule.json',
							data:$("#getRoles").serialize(),
							dataType: "JSON",
							success: function(data) {
								data = eval("(" + data + ")")
								if (data.resposeCode == 200) {
									init();
									$('#giveDialog').dialog('close');
									$("#giveDialog").attr("display", "none");
									$("#treeDemo").html('');
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
						$("#treeDemo").html('');
						$('#giveDialog').dialog('close');
					}
				} ]
			});
		}
	}
	function saveRole() {
		$('#create_role').form('submit', {
			url : '${ctx}/role/save',
			onSubmit : function() {
				var name = $("#name").val();
				if (name == '') {
					$.messager.alert('提示信息', '角色名称不能为空', 'waring');
					return false;
				}
			},
			success : function(data) {
				data = $.parseJSON(data);
				if (data.resposeCode == 200) {
					init();
					$('#createDialog').dialog('close');
					$("#createDialog").attr("display", "none");
					$.messager.alert('提示信息', data.result, 'info');
				} else {
					$.messager.alert('提示信息', data.result, 'error');
					return;
				}
			}
		});
	}
	function openDialog() {
		$('#createDialog').dialog({
			title : '添加角色',
			width : 400,
			height : 200,
			closed : false,
			modal : true,
			buttons : [ {
				text : '保存',
				handler : function() {
					saveRole();
				}
			}, {
				text : '关闭',
				handler : function() {
					$("#create_role").form("clear");
					$('#createDialog').dialog('close');
				}
			} ]
		});
	}

	function getIds(flag, tableId) {
		var ids = "";
		var rows = $('#list').datagrid('getChecked');
		if (rows.length == 0) {
			$.messager.alert('提示信息', '请选择需要操作的行数据', 'warning');
			return;
		} else {
			if (flag == 1) {
				if (rows.length > 1) {
					$.messager.alert('提示信息', '编辑不能选择多行数据', 'warning');
					return;
				} else {
					ids = rows[0].id;
					return ids;
				}
			} else {
				for (var i = 0; i < rows.length; i++) {
					ids += rows[i].id + ",";
				}
				if (ids != '') {
					ids = ids.substring(0,ids.length - 1);
				}
				return ids;
			}
		}
	}

	function deleteInfo() {
		var ids = getIds();
		$.messager.confirm('确认信息', '删除后无法恢复，你确定删除？', function(r) {
			if (r) {
				$.getJSON("${ctx}/role/delete?ids=" + ids, function(data) {
					if (data.resposeCode == 200) {
						init();
						$("#create_role").form("clear");
						$('#createDialog').dialog('close');
						$.messager.alert('提示信息', data.result, 'info');
					} else {
						$.messager.alert('提示信息', data.result, 'error');
						return;
					}
				});
			}
		});
	}

	function edit() {
		var id = getIds(1, 'list');
		if (id != '' && id != undefined) {
			$.getJSON("${ctx}/role/edit?id=" + id, function(data) {
				if (data.resposeCode == 200) {
					$("#name").val(data.object.name);
					$("#id").val(data.object.id);
				} else {
					$.messager.alert('提示信息', data.result, 'error');
					return;
				}
			});
			$('#createDialog').dialog({
				title : '修改角色',
				width : 400,
				height : 200,
				closed : false,
				modal : true,
				buttons : [ {
					text : '保存',
					handler : function() {
						saveRole();
					}
				}, {
					text : '关闭',
					handler : function() {
						$("#create_role").form("clear");
						$('#createDialog').dialog('close');
					}
				} ]
			});
		}
	}

	function init(condition) {
		$('#list').datagrid({
			title : '用户列表',
			pagesize : 10, //默认10 
			autoRowHeight : true,
			striped : true,
			collapsible : false,
			url : '${ctx}/role/getListJson.json?condition=' + condition, //服务器地址,返回json格式数据
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
				width : 400
			}, {
				field : 'name',
				width : 400,
				title : '角色名称'
			}, ] ],
			toolbar : "#search"
		});
		$(".panel").attr("style", "width:100%");
		$(".panel-header").attr("style", "width:auto");
		$(".panel-body").attr("style", "width:auto");
		$(".datagrid-header-inner").attr("style", "width:auto");
		$('#giveDialog').dialog('close');
		$('#createDialog').dialog('close');
	}
</SCRIPT>
<title>角色列表</title>
<style type="text/css">
	.textbox {
		width:180px;
		height:18px;
		line-height:18px;
		padding:3px;
		border-radius:5px;
	}
</style>
</head>
<body>
	<div>
        <div id="search">
			<a href="javascript:void(0)" onclick="openDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="edit()" iconCls="icon-edit" plain="true">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="deleteInfo()" iconCls="icon-remove" plain="true">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="giveRoles()" iconCls="icon-add" plain="true">赋权限</a>
			<div style="float:right;">
				<input id="condition" class="easyui-searchbox" data-options="prompt:'搜索'" style="width:230px;vertical-align:middle;" />
         		<a href="#" class="easyui-linkbutton" plain="true">高级检索</a>
			</div>
        </div>
        <table id="list" class="easyui-datagrid"> 
	    </table>
	    
	    <!-- 赋权限的对话框 -->
	     <div id="giveDialog" class="easyui-dialog" style="width:400px;height:200px;">  
			<ul id="treeDemo" class="ztree"></ul>
	    </div>
	    
	    <div id="createDialog" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"   
		        data-options="iconCls:'icon-save',resizable:true,modal:true">   
		    <form action="${ctx}/role/save" method="post" id="create_role">
		    	<div style="margin: 30px 0 0 65px;">
		    		<input type="hidden" value="" name="id" id="id">
		    		<label>角色名称：<input missingMessage="不能为空" invalidMessage="必填项" class="easyui-validatebox textbox" type="text" name="name" id="name" data-options="required:true" /></label>
		    	</div>
		    </form>   
		    <form id="getRoles" method="post">
		    	<input type="hidden" name="roleIds" id="roleIds" >
	    		<input type="hidden" name="moduleIds" id="moduleIds" >
		    </form>
		</div>  
	</div>
</body>
</html>