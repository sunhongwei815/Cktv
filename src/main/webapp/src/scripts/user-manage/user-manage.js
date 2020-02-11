/**
 * Created by Gy on 2016/6/16.
 */
$(document).ready(function () {
var f, m,A1,A4,A2,A3,pageSize = 4, userDevicePageSize = 4,DevicePageSize=4;
//声明全局变量
//分页发AJAX
function send(pagenow,pagesize) {
    $('#news-table').empty();
    $.ajax({
        type: "post",
        url: "/Cktv/user/selectUsersByUser_status/"+pagenow+'/'+pagesize+'/'+1,
        dataType: "json",
        async : false,
        success: function (data) {
            console.log(data);
             A1=Math.ceil(data.lengthOfUser/pageSize);
            var myTemplate = Handlebars.compile($("#user-list").html());
            $("#table-list").html(myTemplate(data.users));
            click();
            $('.stop-use').on('click', function(){
               var useId = $(this).attr("user_id");
                layer.confirm('确定停用该用户？', {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    $.ajax({
                        type: "post",
                        url: "/Cktv/user/updateUser_statusByUser_id"+'/'+1+'/'+useId,
                        dataType: "json",
                        success: function (data) {
                            layer.msg(data.msg);
                            window.location.reload();
                        }
                    });
                }, function(){
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(index);
                });
            });
        }
    });
}

function send2(pagenow,pagesize) {
    $('#news-table').empty();
    $.ajax({
        type: "post",
        url: "/Cktv/user/selectUsersByUser_status/"+pagenow+'/'+pagesize+'/'+2,
        dataType: "json",
        async : false,
        success: function (data) {
            var result2 = eval(data);
            A4=Math.ceil(result2.lengthOfUser/pageSize);

            var myTemplate2 = Handlebars.compile($("#user-list2").html());
            $("#table-list").html(myTemplate2(result2.users));
            $('.begin-use').on('click', function(){
                var useId = $(this).attr("user_id");
                layer.confirm('确定启用该用户？', {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    $.ajax({
                        type: "post",
                        url: "/Cktv/user/updateUser_statusByUser_id"+'/'+2+'/'+useId,
                        dataType: "json",
                        success: function (data) {
                            if(data.success){
                            layer.msg("启用成功！");}
                            else{layer.msg("操作失败，请重试！")}
                            window.location.reload();
                        }
                    });
                }, function(){
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(index);
                });
            });
            $('.del-use').on('click', function(){
                var useId = $(this).attr("user_id");
                layer.confirm('确定删除该用户？', {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    $.ajax({
                        type: "post",
                        url: "/Cktv/user/deleteUserByUser_id"+'/'+useId,
                        dataType: "json",
                        success: function (data) {
                            layer.msg(data.msg);
                            window.location.reload();
                        }
                    });
                }, function(){
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(index);
                });
            });
        }
    });
}

function click(){
    $('.show-d').click(function(){
        layer.open({
            type: 1,
            title: ['设备列表', 'font-size:18px;'],
            area: ['800px', '600px'],
            shadeClose: true, //点击遮罩关闭
            content: $('#ifream')
        });
        f = $(this).attr("user_id");
        show(f, 1,userDevicePageSize,2);
    });}

    //激活状态筛选
    $("#activation").bind("change",function(){
        var run_status=$(this).find("option:selected").val();
        show (f,1,userDevicePageSize,run_status);
    })

//展示详细设置
function show (f,userDevicePageNow,userDevicePageSize,run_status) {
    var url;
    if(run_status==2){
        url="/Cktv/device/selectDevicesOfUser/" + f+'/'+userDevicePageNow+'/'+userDevicePageSize;
    }else{
        url="/Cktv/device/selectIs_registerDevicesOfUser/" + f+'/'+run_status+'/'+userDevicePageNow+'/'+userDevicePageSize;
    }
    $.ajax({
        type: "post",
        url: url,
        dataType: "json",
        async : false,
        success: function (data) {
            console.log(data);
            if(data.lengthOfUserDevice==null){
                A2=Math.ceil(data.lengthOfUserIs_registerDevice/pageSize);
            }else{
                A2=Math.ceil(data.lengthOfUserDevice/pageSize);
            }
            $(".tcdPageCode2").createPage({
                pageCount: A2,
                current: 1,
                backFn: function (page) {
                    fanye(page,f ,userDevicePageSize,run_status);
                }
            });
            var myTemplate2 = Handlebars.compile($("#device-list").html());
            $("#table-list2").html(myTemplate2(data.devices));
        }
    });
}
function fanye (userDevicePageNow,f,userDevicePageSize,run_status) {
    if(run_status==2){
        url="/Cktv/device/selectDevicesOfUser/" + f+'/'+userDevicePageNow+'/'+userDevicePageSize;
    }else{
        url="/Cktv/device/selectIs_registerDevicesOfUser/" + f+'/'+run_status+'/'+userDevicePageNow+'/'+userDevicePageSize;
    }
        $.ajax({
            type: "post",
            url: url,
            dataType: "json",
            async : false,
            success: function (data) {
                var myTemplate2 = Handlebars.compile($("#device-list").html());
                $("#table-list2").html(myTemplate2(data.devices));
            }
        });
    }

    $(".add").click(function(){
        layer.open({
            type: 1,
            title:false,
            area: [],
            shadeClose: true, //点击遮罩关闭
            content: $('#form')
        });
    });
    $("#send").click(function () {
        var name = $("#uname").val();
        var tip = $("#utip").val();
        var pwd = $("#upwd").val();
        var type=$("#utype option:selected").val();
        if (name.length!=0){
            if (pwd.length>=4&&pwd.length<=16){
        var users = {"user_name":name, "user_pwd":pwd,"user_desc":tip,"user_type":type};
        ajax.post('/Cktv/user/insertUser', users, function(data){
            if(data.success){
                layer.msg(data.msg);
            }else{
                layer.msg(data.msg);
            }
            window.location.reload();
        }, true);}
            else{layer.msg("密码请设置4到16位之间！")}
        }
        else {layer.msg("用户名不能为空！")}
    });
    send(1,pageSize);
    $(".used").click(function (){
        $(".tcdPageCode4").hide();
        $(".tcdPageCode").show();
        send(1,pageSize);
    });
    $(".stopd").click(function (){

        $(".tcdPageCode").hide();
        $(".tcdPageCode4").show();
        send2(1,pageSize);
        $(".tcdPageCode4").createPage({
            pageCount:A4,
            current:1,
            backFn:function(pageNow){
                send2(pageNow,pageSize);
            }
        });
    })
    $(".tcdPageCode").createPage({
        pageCount:A1,
        current:1,
        backFn:function(pageNow){
            send(pageNow,pageSize);
        }
    });

});

function check(f,DevicePageNow,DevicePageSize){
    var is_register = $("#activation option:selected").attr("is_register");
    $.ajax({
            type: "post",
            url: "/Cktv/device/selectIs_registerDevicesOfUser/" +f+"/"+is_register+"/"+DevicePageNow+"/"+DevicePageSize,
            dataType: "json",
            async : false,
            success: function (data) {
                var result3 = eval(data);
                console.log(result3);
                A3=Math.ceil(result3.lengthOfUserIs_registerDevice/pageSize);
                var myTemplate3 = Handlebars.compile($("#device-list").html());
                $("#table-list2").html(myTemplate3(result3));
                sort();
            }
        }
    )
}


