$(function(){
	//定义访问后台,获取头条列表以及一级类别列表的URL
	var url = '/o2o/frontend/listmainpageinfo';
	//获取头条列表以及一级类别列表
	$.getJSON(url,function(data){
		if(data.success){
			var headLineList = data.headLineList;
			var swiperHtml = '';
			//遍历头条列表拼接出轮播图组
			headLineList.map(function(item,index){
				swiperHtml += ''+'<div class="swiper-slide img-wrap">'
				     +'<a href = "'+item.lineLink
				     +'"external><img class="banner-img" src="'+getContextPath()+
				     item.lineImg +'"alt="'+item.lineName+'"></a>'+'</div>';
			});
			//将轮播图赋值给前端HTML页面
			$('.swiper-wrapper').html(swiperHtml);
			//设置轮换时长
			$('.swiper-container').swiper({
				autoplay:3000,
				autoplayDisableOnInteraction:false
			});
			//获取从后台获取的大类列表
			var shopCategoryList = data.shopCategoryList;
			var categoryHtml = '';
			shopCategoryList.map(function(item,index){
				categoryHtml +=''+ '<div class="col-50 shop-classify" data-category='
				      +item.shopCategoryId+'>'+'<div class="word">'
				      +'<p class="shop-title">'+item.shopCategoryName
				      +'</p>'+'<p class="shop-desc">'
				      +item.shopCategoryDesc+'</p>'+'</div>'
				      +'<div class="shop-classify-img-warp">'
				      +'<img class="shop-img" src="'+ getContextPath()+item.shopCategoryImg
				      +'">'+'</div>'+'</div>';
			});
			//将拼接好的字符串赋值给前端控件
			$('.row').html(categoryHtml);
		}
	});
	
	//若点击"我的"，则显示侧栏
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});

	
	$('.row').on('click','.shop-classify',function(e){
		var shopCategoryId = e.currentTarget.dataset.category;
		console.log(shopCategoryId);
		var newUrl = "/o2o/frontend/shoplist?parentId="+shopCategoryId;
		window.location.href = newUrl;
	});
});