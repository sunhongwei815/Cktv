/**
 * Created by ssl on 16/4/17.
 */
$(function () {
    //右上角三角按钮的点击事件
    $('.panel-heading i').click(function () {
        var a = $(this).attr('class');
        if (a=='glyphicon glyphicon-chevron-up'){
            $(this).addClass('hide');
            $('.glyphicon-chevron-down').removeClass('hide');
            $('.panel-body').css('display','none');
        }else if (a='glyphicon glyphicon-chevron-down'){
            $(this).addClass('hide');
            $('.glyphicon-chevron-up').removeClass('hide');
            $('.panel-body').css('display','block');
        }
    });

    $("#close").on('click',function(){
        var num=$(this).attr('value');
        var device_did = $("#device_did").attr("device_did");
        console.log(device_did);
            var url="/Cktv/Msg/device_powerAndShutdown"+"/"+device_did+"/"+num;
            ajax.post(url,null,function(data){
                if(data.success){
                    layer.msg(data.msg);
                }else{
                    layer.msg(data.msg);
                }
            })
    })

});


