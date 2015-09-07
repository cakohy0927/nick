<div class="row-fluid">
    <div class="span12">
	    <table width="98%" cellpadding="10" cellspacing="10" bgcolor="#c0c0c0" style="margin-left: 15px;margin-right: 20px;"
	       class="table table-striped table-bordered table-hover">
		    <tr>
		        <td width="30%">ID</td>
		        <td width="30%">用户名称</td>
		        <td>用户密码</td>
		    </tr>
		    <#list list as user>
		        <tr>
		            <td>${user.id}</td>
		            <td>${user.username}</td>
		            <td>${user.password}</td>
		        </tr>
		   </#list>
		</table>
        <table id="queryTable" class="table table-striped table-bordered table-hover" style="margin-left: 15px;margin-right: 20px;>
            <tr id="4">
                <td style="text-align: center" colspan = "4">
                		<a href="${ctx}/${url}?currentPage=1">首页</a>
                		<#if  pageInfo.currentPage-1 gte 1>
                			<a href="${ctx}/${url}?currentPage=${pageInfo.currentPage-1}">上一页</a>
                		</#if>
                		<#if  pageInfo.currentPage+1 lt pageInfo.totalPage>
                			<a href="${ctx}/${url}?currentPage=${pageInfo.currentPage +1}">下一页</a>
                		</#if>
                		<a href="${ctx}/${url}?currentPage=${pageInfo.totalPage -1}">尾页</a>
                </td>
            </tr>
        </table>
    </div>
</div>