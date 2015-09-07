<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/demo.css">
<script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui/plugins/jquery.validatebox.js"></script>
<title>列表</title>
</head>
<body>
	<table id="page" title="列表分页" style="width:100%;height:auto;"
			data-options="rownumbers:true,singleSelect:true,pagination:true,url:'${ctx}/static/jquery-easyui/datagrid_data1.json',method:'get',toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:120">编号</th>
				<th data-options="field:'productid',width:400">产品</th>
				<th data-options="field:'listprice',width:200,align:'right'">价格</th>
				<th data-options="field:'unitcost',width:160,align:'right'">数量</th>
				<th data-options="field:'attr1',width:240">属性</th>
				<th data-options="field:'status',align:'center'">状态</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"></a>
		</div>
	</div>
	
	<div>
		<h2>
           	复杂数据表格 - Complex DataGrid
        </h2>
        <div class="demo-info">
            <div class="demo-tip icon-tip"></div>
            <div>
                	单击按钮试验数据表格各项功能。
                <br>
            </div>
        </div>
 
        <div style="margin: 10px 0;">
            <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="getSelected()">取得选中项</a>
            <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="getSelections()">取得所有</a>
            <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="selectRow()">选择行（2）</a>
            <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="selectRecord()">通过ID选择行（002）</a>
            <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="unselectRow()">取消行检查</a>
            <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="clearSelections()">取消已选</a>
            <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="resize()">缩放布局（W:700H:400</a>
            <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="mergeCells()">合并单元格</a>
        </div>
        <table id="test">
        </table>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		$('#test').datagrid({ 
            title:'我的表格', 
            iconCls:'icon-save', 
            width:700, 
            height:350, 
            nowrap: true, 
            autoRowHeight: false, 
            striped: true, 
            collapsible:true, 
            url:'${ctx}/static/jquery-easyui/datagrid_data1.json', //服务器地址,返回json格式数据
            sortName: 'code', 
            sortOrder: 'desc', 
            remoteSort: false, 
            idField:'code', 
            frozenColumns:[[ 
                {field:'ck',checkbox:true}, 
                {title:'代码',field:'code',width:80,sortable:true} 
            ]], 
            columns:[[ 
                {title:'基本信息',colspan:5}, 
                {field:'opt',title:'操作',width:100,align:'center', rowspan:2, 
                    formatter:function(value,rec,index){ 
                        return '<span style="color:red"><a href="#" onclick="testSave('+rec.code+')">修改</a> <a href="">删除</a></span>'; 
                    } 
                } 
            ],[ 
                {field:'name',title:'姓名',width:290}, 
                {field:'addr',title:'地址',width:500,rowspan:2,sortable:true, 
                    sorter:function(a,b){ 
                        return (a>b?1:-1); 
                    } 
                }, 
                {field:'col4',title:'年龄',width:150,rowspan:2} 
            ]], 
            pagination:true,  //分页控件
            rownumbers:true,  //行号
            toolbar:[{ 
                id:'btnadd', 
                text:'添加', 
                iconCls:'icon-add', 
                handler:function(){ 
                    $('#btnsave').linkbutton('enable'); 
                    alert('这里是添加的代码') 
                } 
            },{ 
                id:'btncut', 
                text:'剪切', 
                iconCls:'icon-cut', 
                handler:function(){ 
                    $('#btnsave').linkbutton('enable'); 
                    alert('这里是剪切的代码') 
                } 
            },'-',{ 
                id:'btnsave', 
                text:'保存', 
                disabled:true, 
                iconCls:'icon-save', 
                handler:function(){ 
                    $('#btnsave').linkbutton('disable'); 
                    alert('这里是保存的代码') 
                } 
            }] 
        }); 
		
		var pager = $('#page').datagrid().datagrid('getPager');	// get the pager of datagrid
		pager.pagination({
			pageSize: 20,//每页显示的记录条数，默认为10
            pageList:[5,10,15,20],//每页显示几条记录
            beforePageText: '第',//页数文本框前显示的汉字
            afterPageText: '页    共 {pages}页',
            displayMsg: '当前显示 {from}-{to}条记录    共 {total} 条记录',
            onBeforeRefresh:function(){ 
                $(this).pagination('loading');//正在加载数据中...
                alert('before refresh');
                $(this).pagination('loaded'); //数据加载完毕
            },
			buttons:[{
				iconCls:'icon-search',
				handler:function(){
					alert('search');
				}
			},{
				iconCls:'icon-add',
				handler:function(){
					alert('add');
				}
			},{
				iconCls:'icon-edit',
				handler:function(){
					alert('edit');
				}
			}]
		});	
		$(".panel").attr("style","width:100%");
		$(".panel-header").attr("style","width:auto");
		$(".panel-body").attr("style","width:auto");
		$(".datagrid-header-inner").attr("style","width:auto");
	});
</script>
</html>