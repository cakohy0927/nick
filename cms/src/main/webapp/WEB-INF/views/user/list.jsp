<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
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
<title>用户列表</title>
</head>
<body>
	<div>
        <div id="search">
        	<shiro:hasPermission name="createUser">
				<a href="javascript:void(0);" onclick="window.location.href='${ctx}/user/create'" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
        	</shiro:hasPermission>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delInfo()">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="openDialog()" plain="true">赋权限</a>
			<div style="float:right;">
				<input id="condition" class="easyui-searchbox" data-options="prompt:'搜索'" style="width:230px;vertical-align:middle;" />
         		<a href="#" class="easyui-linkbutton" plain="true">高级检索</a>
			</div>
        </div>
        <div id="roleSearch" style="display: none;">
			<div style="float:right;">
				<input id="condition" class="easyui-searchbox" data-options="prompt:'搜索'" style="width:230px;vertical-align:middle;" />
         		<a href="#" class="easyui-linkbutton" plain="true">高级检索</a>
			</div>
        </div>
        <table id="list_data" cellspacing="0" cellpadding="0"> 
	    </table>
	    <div id="dlg" class="easyui-dialog" style="display:none;padding:5px;width:400px;height:200px;" title="角色列表">
			<table id="roleList">
				
			</table>
		</div>
		<div id="dlg-toolbar" style="padding:2px 0;display: none;">
			<table cellpadding="0" cellspacing="0" style="width:100%">
				<tr>
					<td style="text-align:right;padding-right:2px">
						<input class="easyui-searchbox" data-options="prompt:'请输入角色名称'" style="width:150px;border-radius:5px"></input>
					</td>
				</tr>
			</table>
		</div>
		<div id="dlg-buttons" style="display: none;">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
		</div>
	</div>
	<div style="display:none;">
		<form action="" id="addForm" method="post">
			<input type="hidden" name="userIds" id="userIds">
			<input type="hidden" name="roleIds" id="roleIds">
		</form>
	</div>
	
</body>
<script type="text/javascript">
	function saveRole() {
		var rows = $('#list_data').datagrid('getSelections');
		var userIds = '';
		for(var i = 0; i < rows.length; i++){
			userIds += rows[i].id + ",";
		}
		var roleIds = '';
		var roleRows = $('#roleList').datagrid("getSelections");
		if(roleRows.length < 1){
			$.messager.alert("提示信息",'请选择一行再进行操作');
			return ;
		}
		for(var i = 0; i < roleRows.length; i++){
			roleIds += roleRows[i].id + ",";
		}
		$("#roleIds").val(roleIds);
		$("#userIds").val(userIds);
		$('#addForm').form('submit', {
			url : '${ctx}/user/addRole',
			onSubmit : function() {
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
	function openDialog(){
		var rows = $('#list_data').datagrid('getSelections');
		if(rows.length > 0){
			roleList()
			$('#dlg').show(); 
			$("#dlg").dialog({
				modal:true,
				iconCls: 'icon-save',
				width:600,
				height:350,
				draggable:false,
			    closed: false,  
			    resizable:false,
			    buttons: [{
			    	icons: 'icon-ok',
			    	text: "确定",
			    	handler: function() {
			    		saveRole();
					}
			    }, {
					text : '关闭',
					handler : function() {
						$('#dlg').dialog('close');
					}
				}]
			});
		} else {
			$.messager.alert("提示信息",'请选择一行再进行操作');
		}
	}
	
	function delInfo(){
		var rows = $('#list_data').datagrid('getSelections');
		if(rows <= 0){
			$.messager.alert('提示信息','请选中一行进行删除');
		} else {
			$.messager.confirm('提示框', '删除将无法恢复，你确定要删除吗?',function(){
				var ids = "";
				for(var i = 0; i < rows.length; i++){
					ids += rows[i].id + ",";
				}
				$.get('${ctx}/user/delete?ids='+ids,function(data){
					data = $.parseJSON(data);
					if (data.resposeCode == 200) {
						init();
						$.messager.alert('提示信息',data.result,'info');
					}else{
						$.messager.alert('提示信息',data.result,'error');
						return;
					}
				});
				
			});
		}
		/* $.messager.show({
			showType:'show',
			showSpeed:10000,
			msg:'你有新短消息，请注意查收'
		}); */
	}

	function roleList(condition) {
		$('#roleList').datagrid({
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
			pagination : true, //分页控件
			toolbar : "#roleSearch"
		});
		$(".panel").attr("style", "width:100%");
		$(".panel-header").attr("style", "width:auto");
		$(".panel-body").attr("style", "width:auto");
		$(".datagrid-header-inner").attr("style", "width:auto");
		$('#giveDialog').dialog('close');
		$('#createDialog').dialog('close');
	}
	
	function init(condition){
		$('#list_data').datagrid({ 
            title:'用户列表', 
            width:700, 
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
            rownumbers:true,//行号  
            idField:'id', 
            frozenColumns:[[ 
                {field:'ck',checkbox:true}, 
            ]], 
            columns : [ 
                        [
						  {field : 'id',hidden : true, width : 150}, 
                          {field : 'realName', width : 150, title : '姓名'}, 
                          {field : 'sex', width : 150, title : '性别',
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
                          {field : 'loginName', width : 150, title : '登录名称'}, 
                          {field : 'email', width : 150, title : 'Email'},
                          {field : 'phone', width : 220, title : '电话号码'},
                          {field : 'qq', width : 220, title : 'QQ号码'},
                        ] 
                      ],
            pagination:true,  //分页控件
            rownumbers:true,  //行号
            toolbar:"#search"
        }); 
		$(".panel").attr("style","width:100%");
		$(".panel-header").attr("style","width:auto");
		$(".panel-body").attr("style","width:auto");
		$(".datagrid-header-inner").attr("style","width:auto");
	}
	
	$(function(){
		init(condition);
		$('#condition').searchbox({  
		    width:260,  
		    searcher:function(value,name){  
		    	init(value);
		    },  
		    prompt:'搜索'  
		}); 
		$('#dlg').dialog('close');
	});
</script>
</html>