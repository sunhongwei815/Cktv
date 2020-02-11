/**
 * Created by ssl on 16/5/4.
 */
function on_load(iframe) {
    $(iframe).contents().find('body').css('zoom', 0.4);
    /*给模板文字绑定监听事件*/
    $(iframe).contents().find("div[class^='WordLine'],div[class^='Wordline']").on('click', function (event) {
        var myTemplate = Handlebars.compile($("#wordLine_template").html());
        var html = myTemplate({
            wordLine: $(this).text()
        });
        var index = layer.open({
            type: 1,
            title: ['请输入文字', 'font-size:18px'],
            skin: 'layui-layer-rim', //加上边框,边框应该可以换
            area: ['420px', '270px'], //宽高
            content: html
        });
        var wordLine = $(this);
        $("#confirm").on('click', function () {
            var text = $("#wordLine").val();
            wordLine.text(text);
            layer.close(index);
        });
        $("#cancel").on("click",function(){
            layer.close(index);
        })
        event.stopPropagation();    //  阻止事件冒泡
    });
    /*给模板图片绑定监听事件,该事件未执行*/
    $(iframe).contents().find("div[class^='BackgroundImg'],div[class^='Backgroundimg']").on('click', function (event) {
        console.log(1);
        var $this = $(this);
        $("#pic_tpl_mask").removeClass('hide');
        $('#file').unbind("change");
        /!*上传图片*!/
        $("#file").on('change', function () {
            var form = document.getElementById('pic_form');
            var formData = new FormData(form);
            $.ajax({
                url: '/Cktv/publish_tpl/uploadPublish_tplImg',
                data: formData,
                processData: false,// 告诉jQuery不要去处理发送的数据
                contentType: false,// 告诉jQuery不要去设置Content-Type请求头
                type: 'post',
                success: function (response) {
                    var getData;
                    if ((typeof response) == 'string') {
                        getData = JSON.parse(response);
                    } else if ((typeof response) == 'object') {
                        getData = eval(response);
                    }
                    var url = getData.filePath;
                    /!* $this.css('background-image','url("'+url+'")');*!/
                    $this.attr('style', "background-image:url('" + url + "');background-size:100%,100%;");
                    $('#pic_tpl_mask').addClass('hide');
                }
            });
        })
        event.stopPropagation();    //  阻止事件冒泡
    });

    /*给所有图片加边框*/
    function shake_first(ele, style, times) {
        var i = 0;
        var t = false;
        if (t) {
            return;
        }
        var $class = ele.attr('class') + "",
            t = setInterval(function () {
                i++;
                var c = i % 2 ? $class + style : $class
                ele.attr('class', c)
                if (i == 2 * times) {
                    clearInterval(t);
                    ele.removeClass(style);
                }
            }, 200)
    }
    /*给所有文字加边框*/
    function shake_second(ele, times) {
        var i = 0;
        var t = false;
        if (t) {
            return;
        }
        t = setInterval(function () {
            i++;
            var c = i % 2 ? "border:4px solid rgb(60,159,251);" : "";
            ele.attr('style', c);
            if (i == 2 * times) {
                clearInterval(t);
                ele.attr("");
            }

        }, 200);
    }
    /*编辑文字*/
    $("#edit_font").on('click', function () {
        $.each($(iframe).contents().find("div[class^='WordLine'],div[class^='Wordline']"), function () {
            shake_second($(this), 3);
            $('#pic_tpl_mask').addClass('hide');
        })
    });
    /*替换图片*/
    $("#replace_img").on('click', function () {
        console.log("hh");
        /*.css('border','4px solid #ddd');*/
        $.each($(iframe).contents().find("div[class^='BackgroundImg'],div[class^='Backgroundimg']"), function () {
            shake_first($(this), 'blue', 3);
            $('#pic_tpl_mask').addClass('hide');
        })
    });
    /*替换视频*/
    $("#replace_video").on('click', function () {
        var $this = $(this);
        $("#video_tpl_mask").removeClass('hide');

    });
    /*设置属性*/
    $("#set_property").on('click', function () {
        var myTemplate = Handlebars.compile($("#setProperty_template").html());
        var html = myTemplate();
        var index = layer.open({
            type: 1,
            title: ['设置模板属性', 'font_size:18px;font-weight:bold'],
            skin: 'layui-layer-rim',
            area: ['440px', '280px'],
            content: html
        })
    });
    /*选择视频*/
    $('#sel_video').on('click', function () {
        var url = "/Cktv/video/allVideos/" + 1 + "/" + 10;
        ajax.post(url, null, function (data) {
            //handlebars渲染视频列表
            var videoList = Handlebars.compile($('#videoListTemplate').html());
            var html = videoList(data.videos);//data为后台返回的视频数据
            var videoIndex = layer.open({
                type: 1,
                title: ['选择视频文件', 'font-size:18px;font-weight:bold'],
                skin: 'layui-layer-rim',
                area: ['740px', '580px'],
                content: html,
                btn: ['确定', '取消'],
                yes: function (index, layero) {
                    var source = $(".basicInfo input[name='video']:checked").parent().next().children('img').attr('video_url');
                    $(iframe).contents().find("video").attr('src', source);
                    layer.close(videoIndex);
                    $("#video_tpl_mask").addClass('hide');
                },
                cancel: function () {
                    layer.close(videoIndex);
                    $("#video_tpl_mask").addClass('hide');
                }
            });
        });
    });
    /*保存*/
    $("#save").on('click', function () {
        $(iframe).contents().find('body').css('zoom', 1);
        layer.msg('加载中', {icon: 16});
        var url = "/Cktv/publish_tpl/updatePublish_tplContent";
        var object = $(iframe).contents()[0].documentElement;
        var params = {
            html: object.innerHTML,
            publish_tpl_id: parseInt($('#publish_tpl_id').attr('publish_tpl_id'))
        };
        ajax.post(url, params, function (getData) {
            if (getData.success) {
                var publish_id = $("#publish_id").attr('publish_id');
                location.href = "/Cktv/publish/publishAddPage/" + publish_id;
            }else{
                layer.msg('error');
            }
        })
    })/*
=======
/!**
 * Created by ssl on 16/5/4.
 *!/
function on_load(iframe) {
    $(iframe).contents().find('body').css('zoom', 0.4);
    /!*给模板文字绑定监听事件*!/
    $(iframe).contents().find("div[class^='WordLine']").on('click', function () {
        var myTemplate = Handlebars.compile($("#wordLine_template").html());
        var html = myTemplate({
            wordLine: $(this).text()
        });
        var index = layer.open({
            type: 1,
            title: ['请输入文字', 'font-size:18px'],
            skin: 'layui-layer-rim', //加上边框,边框应该可以换
            area: ['420px', '270px'], //宽高
            content: html
        });
        var wordLine = $(this);
        $("#confirm").on('click', function () {
            var text = $("#wordLine").val();
            wordLine.text(text);
            layer.close(index);
        });
        $("#cancel").on("click",function(){
            layer.close(index);
        })
    });
    /!*给模板图片绑定监听事件,该事件未执行*!/
    $(iframe).contents().find("div[class^='BackgroundImg']").on('click', function () {
        console.log(1);
        var $this = $(this);
        $("#pic_tpl_mask").removeClass('hide');
        $('#file').unbind("change");
        /!*上传图片*!/
        $("#file").on('change', function () {
            var form = document.getElementById('pic_form');
            var formData = new FormData(form);
            $.ajax({
                url: '/Cktv/publish_tpl/uploadPublish_tplImg',
                data: formData,
                processData: false,// 告诉jQuery不要去处理发送的数据
                contentType: false,// 告诉jQuery不要去设置Content-Type请求头
                type: 'post',
                success: function (response) {
                    var getData;
                    if ((typeof response) == 'string') {
                        getData = JSON.parse(response);
                    } else if ((typeof response) == 'object') {
                        getData = eval(response);
                    }
                    var url = getData.filePath;
                    /!* $this.css('background-image','url("'+url+'")');*!/
                    $this.attr('style', "background-image:url('" + url + "');background-size:100%,100%;");
                    $('#pic_tpl_mask').addClass('hide');
                }
            });
        })
    });

    /!*给所有图片加边框*!/
    function shake_first(ele, style, times) {
        var i = 0;
        var t = false;
        if (t) {
            return;
        }
        var $class = ele.attr('class') + "",
            t = setInterval(function () {
                i++;
                var c = i % 2 ? $class + style : $class
                ele.attr('class', c)
                if (i == 2 * times) {
                    clearInterval(t);
                    ele.removeClass(style);
                }
            }, 200)
    }

    /!*给所有文字加边框*!/
    function shake_second(ele, times) {
        var i = 0;
        var t = false;
        if (t) {
            return;
        }
        t = setInterval(function () {
            i++;
            var c = i % 2 ? "border:4px solid rgb(60,159,251);" : "";
            ele.attr('style', c);
            if (i == 2 * times) {
                clearInterval(t);
                ele.attr("");
            }

        }, 200);
    }
    /!*编辑文字*!/
    $("#edit_font").on('click', function () {
        $.each($(iframe).contents().find("div[class^='WordLine']"), function () {
            shake_second($(this), 3);
            $('#pic_tpl_mask').addClass('hide');
        })
    });
    /!*替换图片*!/
    $("#replace_img").on('click', function () {
        console.log("hh");
        /!*.css('border','4px solid #ddd');*!/
        $.each($(iframe).contents().find("div[class^='BackgroundImg']"), function () {
            shake_first($(this), 'blue', 3);
            $('#pic_tpl_mask').addClass('hide');
        })
    });
    /!*替换视频*!/
    $("#replace_video").on('click', function () {
        var $this = $(this);
        $("#video_tpl_mask").removeClass('hide');

    });
    /!*设置属性*!/
    $("#set_property").on('click', function () {
        var myTemplate = Handlebars.compile($("#setProperty_template").html());
        var html = myTemplate();
        var index = layer.open({
            type: 1,
            title: ['设置模板属性', 'font_size:18px;font-weight:bold'],
            skin: 'layui-layer-rim',
            area: ['440px', '280px'],
            content: html
        })
    });
    /!*选择视频*!/
    $('#sel_video').on('click', function () {
        var url = "/Cktv/video/allVideos/" + 1 + "/" + 10;
        ajax.post(url, null, function (data) {
            //handlebars渲染视频列表
            var videoList = Handlebars.compile($('#videoListTemplate').html());
            var html = videoList(data.videos);//data为后台返回的视频数据
            var videoIndex = layer.open({
                type: 1,
                title: ['选择视频文件', 'font-size:18px;font-weight:bold'],
                skin: 'layui-layer-rim',
                area: ['740px', '580px'],
                content: html,
                btn: ['确定', '取消'],
                yes: function (index, layero) {
                    var source = $(".basicInfo input[name='video']:checked").parent().next().children('img').attr('video_url');
                    $(iframe).contents().find("video").attr('src', source);
                    layer.close(videoIndex);
                    $("#video_tpl_mask").addClass('hide');
                },
                cancel: function () {
                    layer.close(videoIndex);
                    $("#video_tpl_mask").addClass('hide');
                }
            });
        });
    });
    /!*保存*!/
    $("#save").on('click', function () {
        $(iframe).contents().find('body').css('zoom', 1);
        layer.msg('加载中', {icon: 16});
        var url = "/Cktv/publish_tpl/updatePublish_tplContent";
        var object = $(iframe).contents()[0].documentElement;
        var params = {
            html: object.innerHTML,
            publish_tpl_id: parseInt($('#publish_tpl_id').attr('publish_tpl_id'))
        };
        ajax.post(url, params, function (getData) {
            if (getData.success) {
                var publish_id = $("#publish_id").attr('publish_id');
                location.href = "/Cktv/publish/publishAddPage/" + publish_id;
            }else{
                layer.msg('error');
            }
        })
    })
>>>>>>> bab34a70c00f2d9e93912a6ae706968f6e6e78e0*/
}