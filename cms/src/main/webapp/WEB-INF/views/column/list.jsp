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
<title>栏目列表</title>
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
		<a href="javascript:void(0);" onclick="addColumn()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="#" class="easyui-linkbutton" onclick="deleteInfo()" iconCls="icon-remove" plain="true">删除</a>
		<div style="float:right;">
			<input id="condition" class="easyui-searchbox" data-options="prompt:'搜索'" style="width:230px;vertical-align:middle;" />
	       		<a href="#" class="easyui-linkbutton" plain="true">高级检索</a>
		</div>
    </div>
	<div id="dlg" class="easyui-dialog" title="My Dialog" style="width:400px;height:200px;"   
		        data-options="iconCls:'icon-save',resizable:true,modal:true"> 
		<form action="" method="post" id="addForm" class="easyui-form">
			<div>
				<label for="name">栏目名称</label>  
				<input missingMessage="不能为空" invalidMessage="必填项" class="easyui-validatebox textbox" type="text" name="name" id="name" data-options="required:true" />
			</div>
			<div>
				<label for="parentName">父级菜单</label>
				<select id="cc" missingMessage="不能为空" invalidMessage="必填项" onclick="getModule()" name="parentId" id="parentId" style="width:210px;height:25px;line-height: 25px;" class="easyui-validatebox textbox"></select>
			</div>
		</form>
	</div>
	<table id="tt" class="easyui-treegrid" style="width:700px;" fit="true" toolbar="#search">
    </table>
</body>
<script type="text/javascript">
	function addColumn() {
		$("#addForm").form("clear");
		$("#dlg").dialog("open");
		$('#dlg').dialog({
			title : '栏目操作',
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
						url : '${ctx}/column/save',
						onSubmit : function() {
							return $("#addForm").form('validate')
						},
						success : function(data) {
							data = $.parseJSON(data);
							if (data.resposeCode == 200) {
								loadTreeTable();
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
	
	function deleteInfo() {
		var ids = getIds();
		$.messager.alert('提示信息', ids, 'warning');
	}
	function getIds(flag) {
		var ids = "";
		var rows = $('#tt').treegrid('getChecked');
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
				return ids;
			}
		}
	}

	function loadTreeTable(){
		$(".panel").attr("style", "width:100%");
		$(".panel-header").attr("style", "width:auto");
		$(".panel-body").attr("style", "width:auto");
		$(".datagrid-header-inner").attr("style", "width:auto");
		$('#dlg').dialog('close');
	}
	
	function edit(id){
		alert(id);
	}
	$(function() {
		loadTreeTable();
		$('#tt').treegrid({    
		    url:'${ctx}/column/columnList.json',    
		    idField:'id',
		    method:'POST',
		    treeField:'name',
		    fitColumns:true,
		    striped:true,
		    parentField : 'pid',  
            initialState:"collapsed",
		    singleSelect : false,//是否单选 
		    loadMsg:'正在加载数据，请稍等...',
		    frozenColumns : [ [ {
				field : 'ck',
				checkbox : true
			}]],
		    columns:[[    
		        {field:'id',width:180,hidden:true},
		        {title:'栏目名称',field:'name',width:500},
		        {title:'上级栏目',field:'pName',width:400},
		        {title:'操作',field:'opt',width:500,formatter:function(value,row,index){
		        	return "<a href=\"javascript:void(0)\" onclick=\"edit('" + row.id + "')\">修改</a>"
		        }},
		    ]],
		    onBeforeExpand:function(row){
	    	 	if(row){
			    	$('#tt').treegrid('options').url = "${ctx}/column/getColumnList?id=" + row.id;
		    	}
		    }
		});  
		$('input[type=text]').validatebox();
	});
</script>
</html>