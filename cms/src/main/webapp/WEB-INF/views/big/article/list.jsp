<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.webapp.com.HTMLSpirit" prefix="HTMLSpirit" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.css">
<script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/bootstrap/js/bootstrap.js"></script>
<title>全文检索</title>
<style type="text/css">
	table tr td {
		border:1px solid #f2f2f2;
	}
</style>
<script type="text/javascript">
	$(document).ready(function name() {
		$("#createIndex").on('click',function(){
			$.ajax({
				type : 'GET',
				dataType : 'JSON',
				url : '${ctx}/big/createIndex',
				success: function (data){
					if(data.resposeCode == 200){
						alert('索引创建成功');
						return false;
					}
				}
			});
			
		});
		$("#seacher").click(function(){
			window.location.href='${ctx}/big/list?content='+$("#keyWord").val();
		});
	});
</script>
</head>
<body>
	<div class="container-fluid" style="margin-top:25px;margin-bottom:30px;">
		<div class="form-group">
			<label for="name" class="col-sm-2 control-label">
			</label>
			<div class="col-sm-6">
				<input type="text" name="keyWord" class="form-control" id="keyWord" placeholder="请输入搜索内容">
			</div>
			<label for="name" class="col-sm-3 control-label">
				<button class="btn btn-primary" type="button" id="seacher" style="width:120px;">搜索</button>
				<button class="btn btn-default" id="createIndex" type="button" style="width:120px;">创建索引</button>
			</label>
		</div>
	</div>
	<div class="container-fluid">
		<table class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width: 30%">标题</td>
				<td>内容</td>
				<td style="width: 15%">发布时间</td>
			</tr>
			<c:forEach items="${list}" var="news">
				<tr>
					<td>
						<a href="${ctx}/big/viewInfo/${news.id}" target="_block">${news.title}</a>
					</td>
					<td>
						<HTMLSpirit:subString htmlStr="${news.content}" length="50"/>
					</td>
					<td>
						<fmt:formatDate value="${news.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>