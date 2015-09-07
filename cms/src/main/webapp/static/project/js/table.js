var obj;
(function($){
	$.fn.tableList = function(opts){
		var settings = $.extend({
			title:'',
			url:'',
			method:'POST',
			pageSize : 1,
			tableId : '',
			dataType : 'JSON',
			checkbox:false,
			columns : [{
				feild:'',
				width: 200,
				description : '',
				paramformtter:function(){
					return '';
				}
			}],
			operate: [{
				edit : '',
				del : '',
			}],
			param:''
		},opts||{});
		obj = settings;
		$(settings.tableId).before("<div style=\"background:#f2f2f2;height:30px;line-height:30px;\"><span style=\"margin-left:10px;font-weight:bold\">"+obj.title+"</span></div>");
		getData(settings);
		selectAll();
	}
})(jQuery);

var selectAll = function() {
	$("#ckAll").click(function() {
		$("input[name='checkOne']").prop("checked", this.checked);
		$("input[name='checkOne']").parent().parent().css("background:yellow");
	});

	$("input[name='checkOne']").click(function() {
		var $subs = $("input[name='checkOne']");
		$("#ckAll").prop("checked", $subs.length == $subs.filter(":checked").length ? true : false);
	});
}

var totalNumber = 1;
var currentPage = 1;//当前页

var getData = function(settings){
	$("#page").html("");
	$(settings.tableId).html("");
	Loading("正在加载数据，请稍等...");
	$.ajax({
		method: settings.method,
		url:settings.url,
		async: false,
		data:"pageSize=" + settings.pageSize +"&currentPage="+currentPage, 
		dataType:settings.dataType,
		success: function(data){
			totalNumber = data.totalNumber;
			currentPage = data.currentPage;
			create(data,settings.columns,settings.tableId);
		},
		error:function(){
			alert("failiar : " + data.result);
		},
	});
}

var create = function (data,array,tableId) {
	var objs = data.object;
	var trStr = "";
	var td = "";
	if(obj.checkbox){
		td += "<td style=\"width:25px;\"><input style=\"width: 15px;height: 18px;\" type=\"checkbox\" id=\"ckAll\"/></td>";
	}
	for ( var i = 0;i < array.length ; i++) {
		var style = array[i].width + "px;text-align:center";
		if("operate" == array[i].feild){
			td += "<td style=\"" + style + "\">"+array[i].description+"</td>";
		} else {
			td += "<td>"+array[i].description+"</td>";
		}
		
	}
	trStr += "<tr>" + td + "</tr>";
	for (var j = 0; j < objs.length; j++) {
		var jsonStr = eval(objs[j]);
		trStr += "<tr data-id =\"" + jsonStr["id"]+ "\">"
		var tdObj = "";
		if(obj.checkbox){
			tdObj += "<td style=\"width:25px;\"><input id=\""+jsonStr['id']+"\" style=\"width: 15px;height: 18px;\" type=\"checkbox\" name=\"checkOne\"/></td>";
		}
		for ( var i = 0;i < array.length ; i++) {
			var key = array[i].feild;
			var value = jsonStr[key];
			if("operate" == key){
				value = array[i].paramformtter;
				//value = value.replace("<a","<a role-id=\""+jsonStr['id']+"\" ");
				var style = array[i].width + "px;;text-align:center";
				tdObj += "<td style=\"width:" + style + "\" role-id=\""+jsonStr['id']+"\">" + value + "</td>";
			}else {
				tdObj += "<td>" + value + "</td>";
			}
			
		}
		trStr += tdObj +"</tr>";
	}
	$(tableId).html(trStr);
	$(tableId).after("<div id=\"page\" style=\"text-align:center\"></div>");
	page();
	dispalyLoad();
}

$(document).ready(function(){
	
})

//获得当前对象的ID
var getCurrentTrId = function (obj){
	var td = obj.parentNode;
	if(td != null && td != undefined){
		var id = td.getAttribute("data-role");
		return id;
	}
}

//提交是的遮罩层
var Loading = function (content) {
	$("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body");
	$("<div class=\"datagrid-mask-msg\"></div>").html(content).appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: (window.screen.height/2 -100)});
};

//隐藏遮罩层
//hidden Load
var dispalyLoad = function () {
	$(".datagrid-mask").remove();
	$(".datagrid-mask-msg").remove();
};

var page = function(){
	$("#page").html("");
	var div = "";
	var pageSize = obj.pageSize;
	var totalPage = parseInt(totalNumber / pageSize);
	if(totalNumber % pageSize != 0){
		totalPage += 1;
	}
	if(currentPage <= 3 && currentPage <= totalPage){
		for(var i = 1;i <= totalPage; i++){
			if(i == currentPage){
				div += "<span><a class=\"page active\">"+i+"</a></span>";
			}else{
				div += "<span><a class=\"page\" href=\"javascript:returnTo("+i+")\">"+i+"</a></span>";
			}
		}
	}else{
		div += "...";
	}
	$("#page").html(div);
}

var returnTo = function (currPage){
	var pageSize = obj.pageSize;
	currentPage = currPage;
	//alert("当前页：" + currentPage + ",总记录数：" + totalNumber + ",每页的大小为：" + pageSize);
	getData(obj);
}