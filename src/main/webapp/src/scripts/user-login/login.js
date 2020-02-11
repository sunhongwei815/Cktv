'use strict';    //严格模式
$(document).ready(function(){
    function checkMobile(str) {
        var re = /^1\d{10}$/;
        if(re.test(str)){
            return true;
        } else {
            return false;
        }
    }
    function checkUser_nameLength(str){
        var arr = str.split("");
        if(arr.length<10 || arr.length>5){
            return true;
        }else{
            return false;
        }
    }
    function checkPs(str) {
        var re = /^[a-zA-z0-9]\w{3,15}$/;
        if(re.test(str)) {
            return true;
        } else {
            return false;
        }
    }
    function save() {
        //如果用户勾选了记住密码，就设置cookie和有效期
        if ($('#rmbPassword').prop('checked') == true){
            var str_username = $('#exampleInputUser1').val();
            var str_password = $('#exampleInputPassword1').val();
            $.cookie('rmbUser','true',{ expires: 7});
            $.cookie('username',str_username,{ expires: 7 });
            $.cookie('password',str_password,{ expires: 7 });
        }
        //反之如果用户没有勾选记住密码，就将cookie设置为空字符串
        else {
            $.cookie('rmbUser','false',{ expire: -1});
            $.cookie('username','',{ expire: -1});
            $.cookie('password','',{ expire: -1});
        }
    }
    $('.mobile').blur(function(){
        var mobile = $.trim($(this).val());
        var bool = checkUser_nameLength(mobile);
        if(mobile === ''){
            layer.tips('用户名不能为空！', '.mobile',{
                tips: [2,'#000']
            });
            // $(this).focus();
        }else if(!bool){
            layer.tips('用户名的长度为5~10，请重新输入您的用户名！', '.mobile',{
                tips: [2,'#000']
            });
            // $(this).focus();
        }else{

        }
    });
    $('.password').blur(function(){
        var password = $.trim($(this).val());
        var bool = checkPs(password);
        if(password === ''){
            layer.tips('请填写密码！', '.password',{
                tips: [2,'#000']
            });
            // $(this).focus();
        }else if(!bool){
            layer.tips('密码为4-16位字母或数字组合！', '.password',{
                tips: [2,'#000']
            });
            // $(this).focus();
        }else{

        }
    });
    $('.login').click(function(){
        $('#login').val('正在登陆 ...');
        var params = {
            user_name: $('.mobile').val(),
            user_pwd: $('.password').val()
        };
        ajax.post('/Cktv/user/userLogin', params,function(data){
            if (data.success) {
                // data:Object
                // {msg: "登录成功！",
                // success: true,
                // user: Object
                  /*add_date:null
                    devicedevice_dids:null
                    part_id:0
                    security_key:"0"
                    tpluser_ids:null
                    user_desc:null
                    user_id:31
                    user_name:"15656002187"
                    user_pwd:"123456"
                    user_status:2
                    user_type:2*/
                // redirect_url: "/Cktv/pages/display-manage/publish-list.html"}
                save();
                location.href= data.redirect_url;//跳转页面
            } else {
                layer.msg('手机号或密码错误！');
                $('#login').val('登陆');
            }
        });
    });
    // 页面加载时，根据是否设置cookie，判断是否记住用户名和密码
    if ($.cookie('rmbUser') == 'true'){
        $('#rmbPassword').prop('checked',true);
        $('#exampleInputUser1').val($.cookie('username'));
        $('#exampleInputPassword1').val($.cookie('password'));
    }
});

