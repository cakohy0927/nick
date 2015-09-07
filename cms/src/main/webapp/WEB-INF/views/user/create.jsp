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
<title>Insert title here</title>
<style type="text/css">
	.panel-header{
		width:100%;
	}
	.textbox{
			height:20px;
			margin:0;
			padding:0 2px;
			box-sizing:content-box;
		}
		div{
			width:auto;
		}
		table > tr >td{
			text-align:left;
		}
		#saveForm{
			text-align:left;
		}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		 //设置text需要验证  
        $('input[type=text]').validatebox();  
      	//扩展easyui表单的验证  
        $.extend($.fn.validatebox.defaults.rules, {  
            //验证汉子  
            CHS: {  
                validator: function (value) {  
                    return /^[\u0391-\uFFE5]+$/.test(value);  
                },  
                message: '只能输入汉字'  
            },  
            regIdcard : {// 验证身份证 
                validator : function(value,param) { 
                    return idCard(value); 
                }, 
                message : '身份证号码格式不正确' 
            },
            //移动手机号码验证  
            mobile: {//value值为文本框中的值  
                validator: function (value,param) {
                	return /^(13|15|18)\d{9}$/i.test(value);
                },
                message: '请输入正确电话或手机格式'
            },  
            //国内邮编验证  
            zipcode: {  
                validator: function (value) {  
                    var reg = /^[1-9]\d{5}$/;  
                    return reg.test(value);  
                },  
                message: '邮编必须是非0开始的6位数字.'  
            },  
            //用户账号验证(只能包括 _ 数字 字母)   
            account: {//param的值为[]中值  
                validator: function (value, param) {  
                    if (value.length < param[0] || value.length > param[1]) {  
                        $.fn.validatebox.defaults.rules.account.message = '用户名长度必须在' + param[0] + '至' + param[1] + '范围';  
                        return false;  
                    } else {  
                        if (!/^[\w]+$/.test(value)) {  
                            $.fn.validatebox.defaults.rules.account.message = '用户名只能数字、字母、下划线组成.';  
                            return false;  
                        } else {  
                            return true;  
                        }  
                    }  
                }, message: ''  
            }, 
            pwd: {  
            	validator: function (value, param) {  
                    if (value.length < param[0] || value.length > param[1]) {  
                        $.fn.validatebox.defaults.rules.pwd.message = '密码长度必须在' + param[0] + '至' + param[1] + '范围';  
                        return false;  
                    } else {  
                        if (!/^[\w]+$/.test(value)) {  
                            $.fn.validatebox.defaults.rules.pwd.message = '密码只能由数字、英文字母和下划线组成.';  
                            return false;  
                        } else {  
                            return true;  
                        }  
                    }  
                }, message: '' 
            },
            /*必须和某个字段相等*/
            equalTo: {
            	validator:function(value,param){
            		return $(param[0]).val() == value;
            	},
            	message:'两次密码不一致，请重新输入.'
            },
            qq : {// 验证QQ,从10000开始 
                validator : function(value) { 
                    return /^[1-9]\d{4,9}$/i.test(value); 
                }, 
                message : 'QQ号码格式不正确' 
            }, 
        });
        var idCard = function (value) {
            if (value.length == 18 && 18 != value.length) return false;
            var number = value.toLowerCase();
            var d, sum = 0, v = '10x98765432', w = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2], a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';
            var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);
            if (re == null || a.indexOf(re[1]) < 0) return false;
            if (re[2].length == 9) {
                number = number.substr(0, 6) + '19' + number.substr(6);
                d = ['19' + re[4], re[5], re[6]].join('-');
            } else d = [re[9], re[10], re[11]].join('-');
            if (!isDateTime.call(d, 'yyyy-MM-dd')) return false;
            for (var i = 0; i < 17; i++) 
            	sum += number.charAt(i) * w[i];
            return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));
        }
        
        var isDateTime = function (format, reObj) {
            format = format || 'yyyy-MM-dd';
            var input = this, o = {}, d = new Date();
            var f1 = format.split(/[^a-z]+/gi), f2 = input.split(/\D+/g), f3 = format.split(/[a-z]+/gi), f4 = input.split(/\d+/g);
            var len = f1.length, len1 = f3.length;
            if (len != f2.length || len1 != f4.length) return false;
            for (var i = 0; i < len1; i++) if (f3[i] != f4[i]) return false;
            for (var i = 0; i < len; i++) o[f1[i]] = f2[i];
            o.yyyy = s(o.yyyy, o.yy, d.getFullYear(), 9999, 4);
            o.MM = s(o.MM, o.M, d.getMonth() + 1, 12);
            o.dd = s(o.dd, o.d, d.getDate(), 31);
            o.hh = s(o.hh, o.h, d.getHours(), 24);
            o.mm = s(o.mm, o.m, d.getMinutes());
            o.ss = s(o.ss, o.s, d.getSeconds());
            o.ms = s(o.ms, o.ms, d.getMilliseconds(), 999, 3);
            if (o.yyyy + o.MM + o.dd + o.hh + o.mm + o.ss + o.ms < 0) return false;
            if (o.yyyy < 100) o.yyyy += (o.yyyy > 30 ? 1900 : 2000);
            d = new Date(o.yyyy, o.MM - 1, o.dd, o.hh, o.mm, o.ss, o.ms);
            var reVal = d.getFullYear() == o.yyyy && d.getMonth() + 1 == o.MM && d.getDate() == o.dd && d.getHours() == o.hh && d.getMinutes() == o.mm && d.getSeconds() == o.ss && d.getMilliseconds() == o.ms;
            return reVal && reObj ? d : reVal;
            function s(s1, s2, s3, s4, s5) {
                s4 = s4 || 60, s5 = s5 || 2;
                var reVal = s3;
                if (s1 != undefined && s1 != '' || !isNaN(s1)) reVal = s1 * 1;
                if (s2 != undefined && s2 != '' && !isNaN(s2)) reVal = s2 * 1;
                return (reVal == s1 && s1.length != s5 || reVal > s4) ? -10000 : reVal;
            }
        }
	});
</script>
</head>
<body>
	<div class="easyui-panel" title="用户注册" style="width:100%">
		<div style="padding: 10px 60px 20px 60px">
			<form id="saveForm">
				<table cellpadding="5">
					<tr>
						<td>登录名称:</td>
						<td>
							<input missingMessage="不能为空" validtype="account[6,20]" invalidMessage="长度为6-20个" class="easyui-validatebox textbox" type="text" name="loginName" id="loginName" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<td>真实姓名:</td>
						<td>
							<input missingMessage="不能为空" validtype="CHS" invalidMessage="必须是中文" class="easyui-validatebox textbox" type="text" name="realName" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<td>登录密码:</td>
						<td>
							<input missingMessage="不能为空" validtype="pwd[8,20]" class="easyui-validatebox textbox" type="password" name="password" id="password" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<td>确定密码:</td>
						<td>
							<input missingMessage="不能为空" validtype="equalTo['#password']" class="easyui-validatebox textbox" type="password" name="npassword" id="npassword" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<td>性别:</td>
						<td>
							<input type="radio" id="man" name="sex" checked="checked" value="man" data-options="required:true" />男
							<input type="radio" id="women" name="sex" value="women" data-options="required:true" />女
						</td>
					</tr>
					<tr>
						<td>Email:</td>
						<td>
							<input missingMessage="不能为空" invalidMessage="邮箱格式不正确" class="easyui-validatebox textbox" type="text" name="email" data-options="required:true,validType:'email'" />
						</td>
					</tr>
					<tr>
						<td>电话号码:</td>
						<td>
							<input missingMessage="不能为空" validtype="mobile" class="easyui-validatebox textbox"  type="text" name="phone" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<td>QQ号码:</td>
						<td>
							<input missingMessage="不能为空" validtype="qq" class="easyui-validatebox textbox"  type="text" name="QQ" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<td>家庭住址:</td>
						<td>
							<input class="easyui-validatebox textbox" missingMessage="不能为空" type="text" data-options="required:true" name="address" />
						</td>
					</tr>
				</table>
			</form>
			<div style="text-align: center; padding: 5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a> 
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		function submitForm(){
			$('#saveForm').form('submit', {
				url : '${ctx}/user/save',
				onSubmit : function() {
					return $("#saveForm").form('validate')
				},
				success : function(data) {
					data = $.parseJSON(data);
					if (data.resposeCode == 200) {
						$.messager.confirm('提示信息', data.result, function(r){
							if(r){
								window.location.href = '${ctx}/user/list';
							}
						});
					}else if(data.resposeCode == 100){
						$.messager.alert('提示信息', data.result, 'error');
						return ;
					}else {
						$.messager.alert('提示信息', data.result, 'error');
						return;
					}
				}
			});
		}
	
		$(function(){
			$(".panel").attr("style","width:auto");
			$(".panel-header").attr("style","width:auto");
			$(".easyui-panel").attr("style","width:auto");
			$("input[type='text'],input[type='password']").attr("style","width:260px;height:23px;")
			$("input[type='radio']").attr("style","width:30px;margin-left:10px");
		});
		function clearForm(){
			$('#saveForm').form('clear');
		}
	</script>
</body>
</html>