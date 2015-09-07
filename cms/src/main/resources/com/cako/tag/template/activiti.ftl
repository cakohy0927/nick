<link rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="${ctx}/static/jquery-file-upload/css/jquery.fileupload-ui-noscript.css">
<link rel="stylesheet" href="${ctx}/static/jquery-file-upload/css/jquery.fileupload-ui.css">
<link rel="stylesheet" href="${ctx}/static/jquery-file-upload/css/jquery.fileupload.css">

<script type="text/javascript" src="${ctx}/static/jquery-easyui/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-file-upload/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-file-upload/js/jquery.fileupload.js"></script><style type="text/css">
	.container{
		margin-top:50px;
	}
	.row>div{
		margin-top:20px;
		height:50px;
		line-height:50px;
	}
	#file-table{
		margin-top:25px;
		margin-left:-15px;
	}
	#file-list>td{
		width:6%;
	}
	.bar {
	    height: 22px;
	    margin-left: 330px;
	    margin-top: 35px;
	    background: green;
	}
	input[type="radio"], input[type="checkbox"] {
	    margin: 10px 9px 0;
	}
	#progress{
		margin-left: 80px;
	    margin-top: -63px;
	    width: 500px;
	}
</style>

<script type="text/javascript">
	$(document).ready(function(){
		var uploader = $("#fileupload-file");
		var num = '1';
		$('#fileupload').fileupload({
			autoUpload : true,//是否自动上传  
			url: '${ctx}/version/copy?activiti=activiti',//上传地址  
			dataType : 'json',
			done : function(e,data) {//设置文件上传完毕事件的回调函数  
				var content = "";
				$("#file-table").css("display","block");
				var json = JSON.stringify(data.result);
				var obj2 = eval(json);
				for(var i = 0;i < obj2.length;i++){
					num ++ ;
					content += "<tr id='"+num+"'><td>"
						+"<input type='hidden' name='myfiles' value='"+obj2[i].path+"'>"
						+obj2[i].name+"</td><td>"+obj2[i].size+"</td><td>"+obj2[i].suffix+"</td><td><a href=\"javascript:deleteTr('"+num+"')\">删除</a></td></tr>"
				}
				$("#file-list").after(content);
				$('#progress .bar').css('width','0%');
			},
			progressall : function(e, data) {//设置上传进度事件的回调函数  
				var progress = parseInt(data.loaded / data.total *60, 20);
				$('#progress .bar').css('width', progress + '%');
			}
		});
	});
	//删除当前行
     //要点删除一行输入框的方法
    function deleteTr(id){
        $("#"+id).remove();
    }
    function fileUploadFile(){
    	$.ajax({
  			type: "POST",
  			url: "${ctx}/activiti/deployed",
  			dataType: "json",
 	        cache: true,
  			data:$("#fileupload-file").serialize(),// 你的formid
            async: false,
  			success:function(data){
  				if(data.resposeCode == 200){
  					alert(data.result);
  				}else{
  					alert(data.result);
  				}
  			}
		});
    }
    
</script>
<div class="container-fluid">
	<form id="fileupload-file">
		<div class="row">
			<span class="btn btn-success fileinput-button"> 
				<i class="glyphicon glyphicon-plus"></i> 
				<span>选择文件</span> 
				<input id="fileupload" name="files[]" multiple="" type="file">
			</span>
			<button type="button" onclick="fileUploadFile()" id="btn-upload" class="btn btn-primary start">
				<i class="glyphicon glyphicon-upload"></i> 
				<span>开始上传</span>
			</button>
		</div>
		<div id="progress" >
		    <div class="bar" style="width: 0%;"></div>
		</div>
		<table id="file-table" class="table table-striped table-bordered table-hover" style="display:none">
			<tr id="file-list"><td>文件名</td><td>文件大小</td><td>类型</td><td>操作</td></tr>
		</table>
	</form>
</div>