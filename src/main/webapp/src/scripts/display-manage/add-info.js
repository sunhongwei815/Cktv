$(document).ready(function () {
    /*
    * 专辑详情部分的修改保存
    * */
    $('.save-change').click(function () {
        var publish_screen_mode = $("input[name='mode']:checked").attr('tp1_model');
        var publish_name = $.trim($('#exampleInputName1').val());
        var publish_id =  $('#publish-id').html();
        if (publish_name === '') {
            layer.tips('专辑名称不能为空！', '#exampleInputName1',{
                tips: [2,'#000']
            });
            $(this).focus();
        } else {
            // $('.save-change').text('正在保存...');
        }
        ajax.post('/Cktv/publish/updatePublish/'+publish_id+"/"+publish_name + "/" + publish_screen_mode, null, function (getData) {
            if (getData.success) {
                // $('.save-change').text('保存修改');
                layer.msg(getData.msg);
            } else {
                // $('.save-change').text('保存修改');
                layer.msg(getData.msg);
            }
        })
    });

    /*
    * 添加内容的系列操作
    * */
    function orderVal() {
        var valList = new Array();
        $('.play-order').each(function () {
            var play_order = $.trim($(this).val());
            valList.push(parseInt(play_order));
        });
        return valList;
    }
    //判断播放顺序是否有重复数字
    // 验证重复元素，有重复返回true；否则返回false
    function isRepeat(arr){
        var hash = {};
        for(var i in arr){
            if(hash[arr[i]]){
                return true;
            }
            // 不存在该元素，则赋值为true，可以赋任意值，相应的修改if判断条件即可
            hash[arr[i]] = true;
        }
        return false;
    }
    if(typeof Array.prototype['min'] == 'undefined'){
        Array.prototype.min = function(){
            var min = this[0];
            var len = this.length;
            for(var i = 1;i < len; i++){
                if(this[i] < min){
                    min = this[i];
                }
            }
            return min;
        };
    }
    if(typeof Array.prototype['max'] == 'undefined'){
        Array.prototype.max = function(){
            var max = this[0];
            var len = this.length;
            for(var i = 1;i < len; i++){
                if(this[i] > max){
                    max = this[i];
                }
            }
            return max;
        };
    }
    //验证播放顺序的正确性
    function checkOrder(arr) {
        var min = arr.min();
        var max = arr.max();
        for(var i=0;i<arr.length;i++){
            if(arr[i] <= 0){
                return true;
            }else if(arr[i] > arr.length){
                return true;
            }else if(min != 1) {
                return true;
            }else if(max != arr.length){
                return true;
            }
            return false;
        }
    }
    $('.play-time').blur(function () {
        var play_time = $.trim($(this).val());
        if(play_time == ''){
            layer.tips('请输入播放时长', $(this),{
                tips: [2,'#000']
            });
            $(this).focus();
        }
    });
    //用户点击确认设置时，把用户填写的顺序发送给后台
    $('#confirm-set').click(function () {
        var index = 0;
        for(var i = 0,len = $('.play-time').length;i < len; i++){
            var val = $.trim(($('.play-time')[i]).value);
            if(!(val == '')){
                index++;
            }
        }
        if(index < $('.play-time').length){
            layer.msg('请填写播放时长！');
        }else if(isRepeat(orderVal()) || checkOrder(orderVal())){
            layer.msg('顺序设置错误');
        }else{
            var tplList = new Array();
            $('.play-order').each(function () {
                var publish_tpl_id = $(this).attr('publish_tpl_id');
                var play_order = $.trim($(this).val());
                var play_time = $.trim($(this).siblings('input').val());
                tplList.push({
                    publish_tpl_id: parseInt(publish_tpl_id),
                    play_order: parseInt(play_order),
                    duration: play_time
                });
            });
            console.log(tplList);
            ajax.post('/Cktv/publish_tpl/updatePublish_tplPlay_order',tplList,function(getData){
                if (getData.success) {
                    //getData{
                    //      msg:'更新播放顺序和时长成功',
                    //      success:true}
                    layer.msg(getData.msg)
                } else {
                    layer.msg('设置失败！');
                }
            });
        }
    });
    //删除按钮添加的事件
    $('.btn-remove').click(function(){
        var publish_tpl_id = $(this).attr("publish_tpl_id");
        var publish_tpl_div = $(this).parents('.content-each');
        layer.confirm("确定删除吗？",
            {btn:['是','否']},
            function(){
                var publish_id =  $('#publish-id').html();
                var url = '/Cktv/publish_tpl/deletePublish_tplByPublish_tpl_id'+'/'+publish_tpl_id;
                var params = {
                    publish_tpl_id:publish_tpl_id
                };
                ajax.post(url,params,function(getData){
                    if(getData.success){
                        //getData{
                        //      msg:'删除成功',
                        //      success:true}
                        publish_tpl_div.remove();
                        layer.msg(getData.msg);
                    }else{
                        layer.msg('删除出错！');
                    }
                });
            },function(){
                layer.msg("已取消");
                /*location.href='/Cktv/publish/publishAddPage/'+publish_id;*/
            }
        )
    });

    /*
    * 添加设备的系列操作
    * */
    //当添加的设备名字过长时需要截取并添加省略号
    function cut(h) {
        var name = h.text();
        var len = name.length;
        if(len>3){
            var cutName = name.substring(0,3)+"...";
            return cutName;
        }
        return name;
    }
    //点击新增设备按钮时，渲染模态框的页面，并弹出模态框
    $('.add-display').click(function () {
        ajax.post('/Cktv/device/selectUserDevices',null,function(device_data){
            /*
            * device_data[{
            *       app_name:null
                    app_version:null
                    deviceUser:null
                    deviceVerify_code:null
                    device_did:"ceshi"
                    is_register:0
                    resolution:null
                    run_status:2
                    screen_key:"3445"
                    screen_name:"ceshi"
                    status:0
                    user_id:31
                    verify_code_id:32
                    verify_date:null
               },.....]
            * */
            //这些数据通过Handlebars渲染后展示在模态框内相应位置
            var deviceTemplate = Handlebars.compile($('#device-template').html());
            $('#deviceList').html(deviceTemplate(device_data));
            //页面渲染好后，弹出模态框，就可以看见渲染成功的页面
            $('#mymodal-add').modal({keyboard: false});
            //模态框内点击屏幕这个图标按钮时的操作
            //注意这个是通过Handlebars渲染的，要在渲染之后添加事件
            $('.btn-screen').click(function () {
                var screenName = $(this).siblings('span');
                $(this).siblings('span').text(cut(screenName));
                $(this).siblings("button").fadeToggle(100,function(){
                    $(this).css({'background-color':'green','color':'white'});
                    // 设备选中状态的改变
                    if($(this).attr('checked') == null || $(this).attr('checked') == false){
                        $(this).attr('checked',true);
                    }else {
                        $(this).attr('checked',false);
                    }
                });
            })
        });
    });
    //模态框内保存按钮的操作，这个按钮不是通过Handlebars渲染的，可以在外面添加事件
    $('#save-device').click(function(){
        var publish_id =  $('#publish-id').html();
        var checked_device = $('button[checked=checked]');
        var device_dids = [];
        var publish_device = [];
        $.each(checked_device,function () {
            publish_device.push({
                screen_name : $(this).siblings("span").text(),
                device_did : $(this).attr('device_did')
            });
            device_dids.push({
                publish_id : parseInt(publish_id),
                device_did : $(this).attr('device_did')
            });
        });
        //如果设备已存在则不允许添加
        var isExist = false;
        $('#tableList').children('tr').each(function () {
            var deviceId = $(this).attr('device_did');
            $.each(publish_device,function () {
                var modalDevice = this.device_did;
                if (modalDevice == deviceId){
                    isExist = true;
                    return false;
                }
            });
        });
        if (isExist == true){
            layer.msg("该设备已存在");
        }else {
            //如果设备不存在,向页面表格添加设备
            var url = '/Cktv/publish_device/insertPublish_device';
            ajax.post(url,device_dids,function(table_data){
                //table_data = Object {msg: "添加成功", success: true}
                if(table_data.success){
                    var tableTemplate = Handlebars.compile($('#table-template').html());
                    $('#tableList').append(tableTemplate(publish_device));//尾部追加
                    /*
                    publish_device[Object,Object]
                    Object{device_did:"ceshi",screen_name:"ces..."}
                    */
                    //点击删除时，设备的删除，删除的是添加设备时通过Handlebars渲染的
                    removePublishDevice();
                }else{
                    layer.msg(table_data.msg);
                }
            })
        }
    });
    removePublishDevice();//这里删除的是页面上原本存在的设备，是通过velocity渲染的
    function removePublishDevice() {
        // $('.device-remove').unbind('click');
        $('.device-remove').click(function(){
            var publish_id =  $('#publish-id').html();
            var device_did = $(this).attr('device_did');
            var device_tr = $(this).parents('tr');
            layer.confirm('确定删除么？',{btn:['是','否']},function(){
                ajax.post('/Cktv/publish_device/deletePublish_deviceByPublish_device/'+publish_id+"/"+device_did,null,function(device_dids){
                    if(device_dids.success){
                        /*
                        * device_dids=Object{msg: "删除成功", success: true}
                        * */
                        device_tr.remove();
                        layer.msg(device_dids.msg);
                    }else{
                        layer.msg(device_dids.msg);
                    }
                })
            },function(){
                layer.msg("已取消");
            })
        });
    }

    /*
    * 时段信息的系列操作
    * */
    //点击修改时弹出模态框
    $('.modify').click(function () {
        $('#mymodal-modify').modal({keyboard: false});
    });
    //用户选择时段单选框时，下面弹出的详细信息
    $('#hour').click(function () {
        $('.day').css('display', 'none');
        $('.hour').fadeIn(500);
    });
    $('#day').click(function () {
        $('.hour').css('display', 'none');
        $('.day').fadeIn(500);
    });
    //用户修改时间插件datetimepicker
    $('#timepicker-hourstarttime').datetimepicker({
        format: 'hh:ii',
        startView:1,
        lang: 'zh-CN',
        autoclose:true
    });
    $('#timepicker-hourovertime').datetimepicker({
        format: 'hh:ii',
        startView:1,
        lang: 'zh-CN',
        autoclose:true
    });
    $('.save').click(function () {
        var hst = $.trim($('#timepicker-hourstarttime').val());
        var hot = $.trim($('#timepicker-hourovertime').val());
        if(hst == "" || hot == ""){
            layer.msg("请选择开始时间和结束时间");
        }else if(hst > hot){
            layer.msg("结束时间不能小于开始时间！请重新设置");
        }else{
            var publishId = $('#publish-id').text();
            var url ="/Cktv/publish/updateStart_timeEnd_time/"+publishId + "/" + hst + "/" + hot;
            ajax.post(url,null,function (getData) {
               if(getData.success){
                   /*
                   * getData=Object {msg: "设置时间成功", success: true}
                   * */
                   layer.msg(getData.msg);
                   location.reload();
               }else{
                   layer.msg(getData.msg);
               }
            })
        }
    });

    /*
    * 投放的系列操作
    * */
    $('.put').click(function(){
        var tr = $('#tableList tr');
        var device = tr.attr('device_did');
        if(device){
            var status = $('.put').html();
            var publish_id = $('#publish-id').html();

            if(status == '立即投放'){
                $('.put').html('投放中...');
                ajax.post('/Cktv/publish/updatePublish_statusAndSche_v/'+publish_id+"/startPublish",null,function(getData){
                    /*
                     * getData=Object {msg: "投放成功", success: true}
                     * */
                    if(getData.success){
                        layer.msg(getData.msg);
                        $('.put').html('暂停投放');
                    }else{
                        layer.msg(getData.msg);
                    }
                });
            }else if(status == '暂停投放'){
                $('.put').html('暂停投放中...');
                ajax.post('/Cktv/publish/updatePublish_statusAndSche_v/'+publish_id+"/pausePublish",null,function(getData){
                    /*
                     * getData=Object {msg: "暂停投放成功", success: true}
                     * */
                    if(getData.success){
                        layer.msg(getData.msg);
                        $('.put').html('立即投放');
                    }else{
                        layer.msg(getData.msg);
                    }
                });
            }
        }else{
            layer.msg('您还没有添加设备，请添加！');
        }
    });
});