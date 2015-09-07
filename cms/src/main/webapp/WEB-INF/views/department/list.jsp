<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
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
<title>组织机构列表</title>
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
   		<shiro:hasPermission name="createDepartment">
			<a href="javascript:void(0);" onclick="addModule()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
   		</shiro:hasPermission>
		<shiro:hasPermission name="editDepartment">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
   		</shiro:hasPermission>
		<shiro:hasPermission name="deleteDepartment">
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
   		</shiro:hasPermission>
		<a href="#" class="easyui-linkbutton" onclick="addUser()" iconCls="icon-add" plain="true">添加人员</a>
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
				<label for="name">机构名称</label>  
				<input missingMessage="不能为空" invalidMessage="必填项" class="easyui-validatebox textbox" type="text" name="name" id="name" data-options="required:true" />
			</div>
			<div>
				<label for="parentName">所属机构</label>
				<select id="cc" onclick="getModule()" name="parentId" id="parentId" style="width:210px;height:25px;line-height: 25px;" class="easyui-validatebox textbox"></select>
			</div>
		</form>
	</div>
	<form id="updateUser" method="post">
		<input type="hidden" value="" id="deptIds" name="deptIds" />
		<input type="hidden" value="" id="userIds" name="userIds" />
	</form>
	<div id="addUser" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"   
		        data-options="iconCls:'icon-save',resizable:true,modal:true"> 
		<table id="userList"> 
	    </table>
	</div>
</body>
<script type="text/javascript">
	function initUser(condition){
		$('#userList').datagrid({ 
	        pagesize:1,  //默认10 
	        nowrap: true, 
	        autoRowHeight: true, 
	        striped: true, 
	        collapsible:false, 
	        url:'${ctx}/user/getListJson.json?condition='+condition, //服务器地址,返回json格式数据
	        loadMsg:'数据加载中......',  
	        fitColumns:true,//允许表格自动缩放,以适应父容器  
	        singleSelect:false,//是否单选 
	        pagination:true,//分页控件 
	        idField:'id', 
	        frozenColumns:[[ 
	            {field:'ck',checkbox:true}, 
	        ]], 
	        columns : [ 
	                    [
						  {field : 'id',hidden : true}, 
	                      {field : 'realName',title : '姓名'}, 
	                      {field : 'sex', title : '性别',
	                    	  formatter:function(value,rec,index){
	                    		  var sex = rec.sex;
	                    		  if(sex == 'man'){
	                    			  sex = '男';
	                    		  }else{
	                    			  sex = '女';
	                    		  }
	                    		  return sex;
	                    	  }
	                      },
	                      {field : 'loginName', title : '登录名称'}, 
	                      {field : 'email', title : 'Email'},
	                      {field : 'phone', title : '电话号码'},
	                      {field : 'qq',title : 'QQ号码'},
	                    ] 
	                  ],
	        pagination:true //分页控件
	    }); 
	}
	function addUser() {
		var departRows = $("#list_data").datagrid("getSelections");
		if(departRows.length <= 0){
			$.messager.alert('提示信息','请选择组织机构','warning');
			return ;
		}
		var deptIds = '';
		for (var i = 0; i < departRows.length; i++) {
			deptIds += departRows[i].id;
		}
		$("#addUser").dialog("open");
		initUser();
		$('#addUser').dialog({
			title : '选择用户',
			width : 660,
			height : 400,
			draggable : false,
			closed : false,
			resizable : false,
			modal : true,
			buttons : [ {
				text : '保存',
				handler : function() {
					var userRows = $('#userList').datagrid("getSelections");
					var userIds = '';
					if(userRows.length == 0){
						$.messager.alert('提示信息','请选择用户','warning');
						return ;
					}else{
						for (var j = 0; j < userRows.length; j++) {
							userIds += userRows[j].id + ",";
						}
					}
					$("#deptIds").val(deptIds);
					$("#userIds").val(userIds);
					$('#updateUser').form('submit', {
						url : '${ctx}/user/updateUser',
						onSubmit : function() {
						},
						success : function(data) {
							data = $.parseJSON(data);
							if (data.resposeCode == 200) {
								init();
								$('#addUser').dialog('close');
								$("#addUser").attr("display", "none");
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
					$('#addUser').dialog('close');
				}
			} ]
		});
	}
	
	function getModule() {
		$('#cc').combotree({
			url:"${ctx}/department/departmentListTree.json",
			method:'POST',
			animate:true,
			lines : true,
			checkbox:true,
			onBeforeExpand:function(node,param){  
				$('#cc').combotree('options').url = "${ctx}/department/departmentListTree.json?id=" + node.id;
            }
		});
	}
	var sort = '${sort}';
	function addModule() {
		$("#addForm").form("clear");
		$("#dlg").dialog("open");
		$('#dlg').dialog({
			title : '组织机构信息操作',
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
						url : '${ctx}/department/save',
						onSubmit : function() {
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
			url : '${ctx}/department/departmentList.json?name='+condition, //服务器地址,返回json格式数据
			title : "组织机构列表",
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
			    	$('#list_data').treegrid('options').url = "${ctx}/department/departmentListTree.json?id=" + row.id;
		    	}
		    },
			columns : [ [
					{ field : 'text', width : 150, title : '名称' },
					{ field : 'pName', width : 150, title : '上级部门' }
					]],
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
		$("#addUser").dialog("close");
	});
</script>
</html>