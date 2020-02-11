$(document).ready(function(){
    var pathname = window.location.pathname;
    $('#left .meta').each(function(){
        var list =$(this).find(' a').attr('href');
        if(list == pathname){
            $('#left .meta a').removeAttr('class');
            $(this).parent('ul').children('.meta').css('display','block');
            // console.log($(this).parent('li').children('.meta'));
            $(this).find('a').addClass('act');
        }
    });
    $('.head_link').click(function(){
        var name = $(this).text(),
            content='内容管理',
            equipment='设备管理',
            link_name=$('.link_name').text();
        $('.icon-angle-down').attr('class','icon-angle-left');
        if(name == content){
            $('.meta').css('display','none');
            $('.active').css('display','none');
            $('.content_list').css('display','block');
            if(link_name != name){
                $('.link_name').html(name);
            }else{};
            var selecter=$('.content_list').next('.meta').first().text();
            $('.left_link').html(selecter);
        }else if(name == equipment){
            $('.meta').css('display','none');
            $('.active').css('display','none');
            $('.equipment_list').css('display','block');
            if(link_name!=name){
                $('.link_name').html(name);
            }else{};
            var selecter=$('.equipment_list').next('.meta').first().text();
            $('.left_link').html(selecter);
        }else{}
    });
    $('.active').click(function(){
        $($(this).nextAll('.meta')).toggle();
        $($(this).find('.icon-angle-left')).attr('class','icon-angle-down');
        var dis = $(this).next().css('display');
        if (dis == 'none'){
            $($(this).find('.icon-angle-down').addClass('icon-angle-left').removeClass('icon-angle-down'))
        }
    });
    $('.meta').click(function(){
        var selecter =$(this).text();
        $('.left_link').html(selecter);
    });

});