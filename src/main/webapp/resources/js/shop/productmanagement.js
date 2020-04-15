$(function(){
	//获取此店铺下所有商品列表
	var productListUrl = "/o2o/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=999";
	//商品下架URL
	var statusUrl = "/o2o/shopadmin/modifyproduct";
	getList();
	
	/**
	 * 获取此店铺下商品列表
	 */
	
	function getList() {
		$.getJSON(productListUrl,function(data){
			if(data.success){
				var productList = data.productList;
				var tempHtml = '';
				//遍历每条数据商品信息，拼接成一行显示，列信息包括：
				//商品名称，优先级，上架/下架(含productId) 编辑按钮(productId)
				//预览(productId)
				productList.map(function(item,index){
					var textOp = "下架";
					var contraryStatus = 0;
					if(item.enableStatus == 0){
						//若状态值为0,则表示商品已经下架,操作变为上架
						textOp = "上架";
						contraryStatus = 1;
					}else{
						contraryStatus = 0;
					}
					tempHtml +=''+ '<div class="row row-product">'
					     +'<div class="col-33">'
					     +item.productName
					     +'</div>'
					     +'<div class="col-20">'
					     +item.point
					     +'</div>'
					     +'<div class="col-40">'
					     +'<a href="#" class="edit" data-id="'
					     +item.productId
					     +'" data-status="'
					     +item.enableStatus
					     +'">编辑</a>'
					     +'<a href="#" class="status" data-id="'
					     +item.productId
					     +'" data-status="'
					     +contraryStatus
					     +'">'
					     +textOp
					     +'</a>'
					     +'<a href="#" class="preview" data-id="'
					     +item.productId
					     +'" data-status="'
					     +item.enableStatus
					     +'">预览</a>'
					     +'</div>'
					     +'</div>'
				});
				//将拼接好的信息赋值进html控件中
				$('.product-wrap').html(tempHtml);
			}
		});
	}
	
	    
	//将class为category-wrap里面的a标签绑定事件
	$('.product-wrap').on('click','a',function(e){
		var target = $(e.currentTarget);
		//hasClass() 方法检查被选元素是否包含指定的类名称。如果被选元素包含指定的类，该方法返回 "true"。
		if(target.hasClass("edit")){
			window.location.href = '/o2o/shopadmin/productoperation?productId='
				+e.currentTarget.dataset.id;
		}else if(target.hasClass("status")){
			//如果有class status 则调用后台商品上下架功能
			changeItemStatus(e.currentTarget.dataset.id,e.currentTarget.dataset.status);
		}else if(target.hasClass("preview")){
			//如果有class preview 则转发到商品详情页面
            window.location.href = '/o2o/frontend/productdetail?productId='
                + e.currentTarget.dataset.id;
		}
		
	});
	
	
	function changeItemStatus(id,status){
		//定义product json对象向其中添加商品编号及上下架
		var product = {};
		product.productId = id;
		product.enableStatus = status;
		$.confirm('确定吗?',function(){
			//上下架商品信息
			$.ajax({
				url:statusUrl,
				type:'POST',
				data:{
					productStr:JSON.stringify(product),
					statusChange:true
				},
				datatype:'json',
				success:function(data){
					if(data.success){
						$.toast('操作成功');
						getList();
					}else{
						$.toast('操作失败');
					}
				}
			});
		});
	}
});