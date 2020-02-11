$(document).ready(function(){
   $('.active').click(function(){
   	$('.meta').css('display','none');
       $(this).show(1000,$(this).nextAll().css('display','block'))
    //$('.left_contain').slideUp(100000,$(this).nextAll().css('display','block'));
	//$(this).nextAll().css('display','block');$(selector).animate({params},speed,callback);
   });
})
