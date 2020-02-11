$(document).ready(function(){
   $('.head_link').click(function(){
			var name = $(this).text(),
				equipment='设备管理',
				contain='内容管理';
			$('.link_name').html(name);
			if(name == contain){
				$('.meta').css('display','none');
				$('.left_equipment').css('display','none');
				$('.left_contain').css('display','block');
				var selecter=$('.left_contain').find('.meta').first().text();
				$('.left_link').html(selecter);
			}else if(name == equipment){
				$('.left_contain').css('display','none');
				$('.left_equipment').css('display','block');
				var selecter=$('.left_equipment').find('.meta').first().text();
				$('.left_link').html(selecter);
			}else{}
		});
		$('.meta').click(function(){
			var selecter=$(this).text();
		$('.left_link').html(selecter);
	});

});
