
    //修改信息按钮触发的事件
    $('#set').on('click', function(){
        var array = getChosenDeviceInfos();
        if(array.length ==1){
            $(".screen_name-origin").text(array[0].screen_name);
            layer.open({
                type: 1,
                title: ['修改信息', 'font-size:18px;'],
                area: ['400px', '300px'],
                shadeClose: true, //点击遮罩关闭
                closeBtn: false,
                content: $('#ifream2'),
                btn: ['确定', '取消'],
                btn1:function(){
                    //绑定的AJAX
                    var screen_name = $(".input-design").val();
                    var device_did = array[0].device_did;
                    var url = "/Cktv/device/updateScreen_nameByDevice_did/"+device_did+'/'+screen_name;
                    ajax.post(url,null,function (result3) {
                        if(result3.success){
                            location.href = '/Cktv/pages/device-manage/device-manage1.0.html';
                        } else{
                            layer.msg("修改失败！",{icon:2,time:800});
                        }
                    });
                }
            });
        }else if(array.length == 0){
            layer.msg("请选择您要操作的设备！",{icon:2,time:800});
        }else{
            layer.msg("设备信息修改只能选择一个设备，请重新选择您的设备！",{icon:2,time:800});
        }
    });

function getChosenDeviceInfos(){
    var arr = new Array();
    $("tbody input[type='checkbox']:checked").each(function() {
        var device = new Object();
        device.screen_name = $(this).val();

        device.device_did = $(this).attr("device_did");
        arr.push(device);
    });
    return arr;
}
var page1;

function send(startpage,size,index,run_status,screen_name) {
    var url;
     if(index==1){
        url="/Cktv/device/selectDevicesByRunStatus/"+run_status+"/"+startpage+"/"+size;
    }else if(index==2){
         console.log(screen_name)
        url="/Cktv/device/selectDevicesByScreenName/"+screen_name+"/"+startpage+"/"+size;
    }
    $.ajax({
        url:url,
        type: 'post',
        success:function(data){
            console.log(data);
            var myTemplate = Handlebars.compile($("#device_list_template").html());
            $('#table-list').html(myTemplate(data.devices));
            if(data.sumPages){
                page1=Math.ceil(data.sumPages/size);
            }else{
                page1=Math.ceil(data.lengthOfUserDevice/size);
            }

            console.log(page1);
            $(".tcdPageCode").createPage({
                pageCount:page1,
                current:1,
                backFn:function(page){
                    fanye(page,size,index,run_status,screen_name);
                }
            });
        }
    })
}
//根据index来显示不同分页
function fanye(startpage,size,index,run_status,screen_name){
    var url,data;
    if(index==1){
        url="/Cktv/device/selectDevicesByRunStatus/"+run_status+"/"+startpage+"/"+size;
    }else if(index==2){
        url="/Cktv/device/selectDevicesByScreenName/"+screen_name+"/"+startpage+"/"+size;
        data=screen_name;
    }
    $.ajax({
        url:url,
        type: 'post',
        data:'data',
        success:function(data){
            console.log(data);
            var myTemplate = Handlebars.compile($("#device_list_template").html());
            $('#table-list').html(myTemplate(data.devices));
        }
    })
}
$(function () {
    var size=4; //每一页展示的数据条数
    var index=0;
    //进入页面直接加载第一页设备表；
    var run_status=1;
    send(1,size,1,run_status);
  //  send(startpage,size,index,run_status,screen_name)
    //根据屏幕名称查询
    var device_search = document.getElementById('device-search');
    device_search.onclick=function(){
    //$('#device-search').on('click',function () {
        index=2;
        var screen_name=$("#screen_name").val();
        if(screen_name==""){
            layer.msg("请输入屏幕名称！");
        }else{
            send(1,size,index,1,screen_name);
        }
    };
   /* function search () {
        var params={
            run_status:$('#run_status option').attr('run_status'),
            screen_name:$('#screen_name').val()
        };
        var url='/Cktv/device/selectDevices';
        ajax.post(url,params,function (getData) {
            page2=getData.lengthOfVerifyUnused;
            /!*注册一个Handlebars模版，通过id找到某一个模版，获取模版的html框架*!/
            var myTemplate = Handlebars.compile($("#device_list_template").html());
            $('#table-list').html(myTemplate(getData));
        });
    }*/

    //运行状态筛选
    $("#run_status").bind("change",function(){
        var run_status=$(this).find("option:selected").val();
        index=1;
        send(1,size,index,run_status);
    })

    //全选、取消全选的事件
   $('#SelectAll').click(function () {
        $("tbody input[name='mycheck']").attr('checked',this.checked);
   });

    //绑定按钮的点击事件
    $('#bind').on('click', function(){
        layer.open({
            type: 1,
            title: ['绑定设备', 'font-size:18px;'],
            area: ['400px', '280px'],
            shadeClose: true, //点击遮罩关闭
            content: $('#ifream'),
            btn: ['确定', '取消'],
            btn1:function(){
                //绑定的AJAX
                var code=$("#ifream input").val();
                var url = "/Cktv/device/selectDevicesByUser_id/";
                ajax.post(url,code,function (result2) {
                    if(result2){alert("绑定成功！")}
                    else{alert("激活码错误！")}
                });
            }
        });
    });
    


    //关机按钮
    $("#shutdown").on('click',function(){
        var obj=getCheckedId();
        if(obj.array.length==0){
            layer.msg("请选择您要操作的设备！",{icon:2,time:800});
        }else{
            var sta=getRunStatus();
            if(sta==2){
                layer.msg("你选择的设备已关机！");
            }else{
                layer.confirm("确定关机？",
                    {btn:['确定','取消']},
                    function(){
                        var url="/Cktv/Msg/devices_powerAndShutdown";
                        ajax.post(url,obj,function(data){
                            if(data.success){
                                layer.msg(data.msg,{
                                    time: 1000, //1s后自动关闭
                                });
                                location.reload(true);
                            }else{
                                layer.msg(data.msg,{
                                    time: 1000, //1s后自动关闭
                                });
                            }
                        })
                    },function(){
                        layer.msg("已取消！",{
                            time: 1000, //1s后自动关闭
                        });
                    }
                )
            }
        }
    })

    function getCheckedId(){
        var arr=new Array();
        $("tbody input[type='checkbox']:checked").each(function(){
            var did=$(this).attr("device_did");
            arr.push(did);
        })
        var num=$("#shutdown").attr("value");
        var obj={array:arr,status:num};
        return obj;
    }

    function getRunStatus(){
        var sta;
        $("tbody input[type='checkbox']:checked").each(function(){
            sta=$(this).attr("run");
        });
        return sta;
    }
    //开机按钮
    $("#power").on('click',function(){
        var obj=getCheckedId();
        if(obj.array.length==0){
            layer.msg("请选择您要操作的设备！",{icon:2,time:800});
        }else{
            var sta=getRunStatus();
            if(sta==1){
                layer.msg("你选择的设备已开机！");
            }else{
                layer.confirm("确定开机？",
                    {btn:['确定','取消']},
                    function(){
                        var url="/Cktv/Msg/devices_powerAndShutdown";
                        ajax.post(url,obj,function(data){
                            if(data.success){
                                layer.msg(data.msg,{
                                    time: 1000, //1s后自动关闭
                                });
                                location.reload(true);
                            }else{
                                layer.msg(data.msg,{
                                    time: 1000, //1s后自动关闭
                                });
                            }
                        })
                    },function(){
                        layer.msg("已取消！",{
                            time: 1000, //1s后自动关闭
                        });
                    }
                )

            }
        }
    })
    function deleteGetId(){
        var arr=new Array();
        var b=new Array();
        $("tbody input[type='checkbox']:checked").each(function(){
            var did=$(this).attr("device_did");
            var n=$(this).parent().parent();
            arr.push(did);
            b.push(n);
        })
        var Info={arr:arr,b:b};
        return Info;
    }
    //删除按钮
    $("#delete").on('click',function(){
        var Info=deleteGetId();
        console.log(Info);
        var obj=Info.arr;
        var b=Info.b;
        console.log(b);
        if(obj.length==0){
            layer.msg("请选择您要操作的设备！",{icon:2,time:800});
        }else{
            layer.confirm("确定删除？",
                {btn:['确定','取消']},
                function(){
                    var url="/Cktv/device/deviceDevicesByUserId";
                    ajax.post(url,JSON.stringify(obj),function(data){
                        if(data.success){
                            layer.msg(data.msg,{
                                time: 1000 //1s后自动关闭
                            });
                            for(var i=0;i<= b.length;i++){
                                b[i].remove();
                            }
                        }else{
                            layer.msg(data.msg,{
                                time: 1000 //1s后自动关闭
                            });
                        }
                    })
                },function(){
                    layer.msg("已取消！",{
                        time: 1000, //1s后自动关闭
                    });
                }
            )
        }
    })
});