$(document).ready(function() {
	$('.special-name').blur(function() {
		var special_name = $.trim($(this).val());
		if (special_name === '') {
			layer.tips('请输入专辑名称！', '.special-name',{
				tips: [2,'#000']
		});
			$(this).focus();
		} else {

		}
	});
	$('#create').click(function() {
		$('#create').text('正在创建 ...');
		var params = {
			publish_screen_mode:$("input[name='mode']:checked").attr('tp1_model'),
			publish_name: $('#exampleInputUser1').val()
		};
		ajax.post('/Cktv/publish/insertPublish',params,function(getData){
			if(getData.success){
				//专辑名称正确且无重复
				location.href='/Cktv/publish/selectPublishByPublish_name/'+params.publish_name;
			}else{
				layer.msg(getData.msg);
				$('#create').text('确认创建');
			}
		});
	});
});