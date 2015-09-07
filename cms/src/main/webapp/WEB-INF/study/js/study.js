$(document).ready(function(event){
	var windowWidth = $(window).width();
	var windowHeight = $(window).height();
	$(".view-image img").click(function(){
		// 创建对象
		var img = new Image();
		// 改变图片的src
		img.src = $(this).attr("src");
		var width = img.width;
		var height = img.height;
		if(height > 500){
			width = 500;
			height = 500;
		}
		var obj = $(".show-view");
		obj.attr("style","position:absolute;display:block;width:" + (width + 100) + "px;height:"+height+"px;margin-left:"+($(window).width() / 2 - width /2)+"px;")
		obj.find("img").attr("src",$(this).attr("src")).width(width).height(height);
		$(".left").height(height).css("line-height",height);
		$(".right").height(height).css("line-height",height);
	});
});

