(function($){
	$.fn.saveOrUpdate = function(opts){
		var settings = $.extend({
			method:'POST',
			saveUrl:'',
			formId:'',
			aimUrl:''
		},opts||{});
		$(this).click(function(){
			save(settings);
		});
	}
	$.fn.deleteInfo = function(opts){
		var settings = $.extend({
			deleteUrl:'',
			id:'',
			aimUrl:''
		},opts||{});
		$(this).on('click',function(){
			alert("这是删除的方法");
		});
	}
})(jQuery);

function save(settings){
	$.ajax({
		type:settings.method,
		dataType: "json",
		url: settings.saveUrl,
		sync:false,
		data:$(settings.formId).serialize(),
		success:function(data){
			var flag = data.resposeCode;
			if(flag==200){
				$.messager.alert("信息",data.result);
				window.location.href = settings.aimUrl;
			} else {
				$.messager.alert("信息",data.result,'error');
				return false;
			}
		}
	});
}

