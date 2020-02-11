/**
 * Created by gyh on 2016/8/18.
 */
$(document).ready(function(){
    $('#mode-name').blur(function(){
        var mode_name = $(this).val();
        if(mode_name === ''){
            layer.tips('请输入模板名称','#mode-name',{
                tips: [2,'#000']
            });
            $(this).focus();
        }else{

        }
    });
    $('#file-img').blur(function(){
        var file_img = $(this).val();
        var file_img_arr = file_img.split('.');
        var postfix = file_img_arr[file_img_arr.length-1];
        if(file_img === ''){
            layer.tips('请选择图片文件','#file-img',{
                tips: [2,'#000']
            });
        }
        /*图片文件的格式太多了，先不写*/
        /*if(postfix !== ('jpg' || 'jpeg')){
            layer.tips('请选择图片文件','#file-img',{
                tips: [2,'#000']
            });
        }*/
    });

    /*
    * 有好几种方式可以在两个页面之间传递数据
    * */

    $('#confirm').click(function(){
        var that = $(this);
        var mode_name = $('#mode-name').val();
        var file_img = $('#file-img').val();
        if(mode_name === ''){
            layer.tips('请输入模板名称','#mode-name',{
                tips: [2,'#000']
            });
            $(this).focus();
        } else if(file_img === ''){
            layer.tips('请选择封面图片','#file-img',{
                tips: [2,'#000']
            });
        } else{
            /*$('#formoftpl').submit();*/
            /*利用ajax提交*/
            var hasVideo = $("#formoftpl input[name='is_video']:checked").val();
            var tpl_name = $.trim($('#mode-name').val());
            that.html('正在上传...');
            var theurl = '/Cktv/tpl/saveTpl';
            var paramData = new FormData($('#formoftpl')[0]);
            /*$.ajax({
                url: theurl,
                data: paramData,
                type: 'post',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function(getData){
                    console.log(getData);
                    var tpl_id = getData.tpl.tpl_id;
                    if(getData.success){
                        location.href = '/Cktv/pages/userTemplate-manage/create-template.html?hasVideo=' + hasVideo + '&tpl_name=' + tpl_name + '&tpl_id=' + tpl_id;
                    }else{
                        layer.msg(Data.msg);
                    }
                },
                error: function(){
                    layer.msg('出错！');
                }
            })*/
            var sendAjax = function (url) {
                var promise = new Promise(function (resolve, reject) {
                    var xhr = new XMLHttpRequest();
                    xhr.open('post',url,true);
                    xhr.onreadystatechange = function () {
                        if(this.readyState === 4){
                            if(this.status === 200){
                                resolve(this.response);
                                console.log(this.responseText + ',' + this.response);
                            }else{
                                reject(new Error(this.status + ',' + this.statusText));
                            }
                        }
                    };
                    xhr.send(paramData);
                });
                return promise;
            };
            sendAjax(theurl).then(function (params) {
                console.log(params);
                console.log(typeof params);
                var getData;
                if(typeof params == 'string'){
                    getData = JSON.parse(params);
                }
                if(getData.success){
                    var tpl_id = getData.tpl.tpl_id;
                    sessionStorage.setItem('tpl_id',tpl_id);
                    sessionStorage.setItem('hasVideo',hasVideo);
                    sessionStorage.setItem('tpl_name',tpl_name);
                    location.href = '/Cktv/pages/userTemplate-manage/create-template.html';
                }
            },function (error) {
                layer.msg(error);
            });
        }

    });


});