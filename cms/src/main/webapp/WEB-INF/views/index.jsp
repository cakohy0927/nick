<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/demo.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/project/css/menu.css">

<script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui/plugins/jquery.validatebox.js"></script>
<title>运营管理</title>
<script type="text/javascript">
	window.setInterval('showTime()', 1000);
	function showTime() {
		var enabled = 0;
		today = new Date();
		var day;
		var date;
		if (today.getDay() == 0)
			day = "星期日";
		if (today.getDay() == 1)
			day = "星期一";
		if (today.getDay() == 2)
			day = "星期二";
		if (today.getDay() == 3)
			day = "星期三";
		if (today.getDay() == 4)
			day = "星期四";
		if (today.getDay() == 5)
			day = "星期五";
		if (today.getDay() == 6)
			day = "星期六";
		var minntes = today.getMinutes() <= 9 ? ("0" + today.getMinutes()): today.getMinutes();
		var second = today.getSeconds() <= 9 ? ("0" + today.getSeconds()) : today.getSeconds();
		date = (today.getYear() + 1900) + "年" + (today.getMonth() + 1) + "月"
				+ today.getDate() + "日 " + day +" "+ today.getHours() + ":" + minntes + ":" + second;
		document.getElementById("time").innerHTML = date;
	}
	$(document).ready(function(){
		showTime();
		$(".active").find("a").nextAll().hide();
		$(".menu h3 a").addClass("noSelected");
		$(".menu h3 a").on("click",function(){
			var obj = $(this);
			$("h3").each(function(){
				$(this).find("a").nextAll().hide(500);
				$(this).removeClass("active");
				$(this).find("a").removeClass("sectected");
			});
			var id = $(this).attr("role-id");
			obj.parent().find("ul").remove();
			$.getJSON('${ctx}/menu/getMenuJson?id='+id,function(data){
				var li = "";
				for(var i = 0; i < data.length ;i ++){
					alert
					li += "<li><a href=\""+data[i].uri+"\" target=\""+data[i].target +"\">"+data[i].text+"</li>"
				}
				var ul = "<ul>" + li + "</ul>";
				obj.after(ul)
			});
			$(this).parent().removeClass("active");
			$(this).addClass("sectected");
			$(this).nextAll().slideDown(1000);
		});
	});
</script>
<style type="text/css">
</style>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px">
		欢迎[<shiro:principal/>]登录
		<span style="float:right;">
			<span>欢迎您：${user.realName}</span>
			&nbsp;&nbsp;&nbsp;
			当前时间：<span id="time"></span>
			<span><a href="${ctx}/logout">退出</a></span>
		</span>
		
	</div>
	<div style="width:200px;padding:10px;overflow:visible;" data-options="region:'west',split:true" >
		<div class="menu" style="overflow:visible;">
			<ul>
				<c:forEach items="${list}" var="menu">
					<li>
						<h3 class="active">
							<a href="javascript:void(0)" role-id="${menu.id}">${menu.name}</a>
						</h3>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div data-options="region:'east',split:true,collapsed:true,title:'快捷入口'" style="width:500px;padding:10px;">east region</div>
	<div data-options="region:'south',border:false" style="height:35px;background:#A9FACD;padding:10px;text-align:center;">south region</div>
	<div data-options="region:'center'">
		<iframe id="main_frame" name="main_frame" src="" frameborder="0" width="100%" height="500px;"></iframe>
	</div>
</body>
</html>