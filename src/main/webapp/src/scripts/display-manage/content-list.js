$(document).ready(function() {
	var data = {
		'content': [
			{
				'index': 1,
				'number': 8,
				'name': '工大物联网',
				'plan': '实验室',
				'time': '2016'
			},
			{
				'index': 2,
				'number': 8,
				'name': '工大物联网',
				'plan': '实验室',
				'time': '2016'
			}
		]
	};
	var contentTemplate = Handlebars.compile($('#content-template').html());
	$('#contentList').html(contentTemplate(data));
});