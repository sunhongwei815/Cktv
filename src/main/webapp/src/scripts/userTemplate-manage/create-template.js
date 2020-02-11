/**
 * Created by gyh on 16-7-16.
 */
window.onload = function () {
    /*
     * 事件笔记:
     * var e = e || window.event;事件对象获取的通用方法
     * e.pageX;e.pageY;事件发生时鼠标相对页面的位置
     * ele.offsetLeft;ele.offsetTop;元素距离页面的位置,左上角
     * */

    //获取元素的4个函数
    function gi(id) {
        return document.getElementById(id);
    }
    function gc(className) {
        return document.getElementsByClassName(className);
    }
    function gifmi(id) {
        var iframe =  document.getElementById("iframe");
        if(!iframe) return false;
        var sub =  iframe.contentWindow.document.getElementById(id);
        if(!sub) return false;
        return sub;
    }
    function gifmc(className){
        var iframe =  document.getElementById("iframe");
        if(!iframe) return false;
        var sub =  iframe.contentWindow.document.getElementsByClassName(className);
        if(!sub) return false;
        return sub;
    }

    //大的页面上的元素拖动事件
    function dragEvent(ele) {
        var mousemoveX = 0;
        var mousemoveY = 0;
        var isDragging = false;
        ele.addEventListener("mousedown",function(e){
            var e = e || window.event;
            mousemoveX = e.pageX - ele.offsetLeft;
            mousemoveY = e.pageY - ele.offsetTop;
            isDragging = true;
        });
        ele.addEventListener("mousemove",function(e){
            var e = e || window.event;
            var moveX = 0;
            var moveY = 0;
            if (isDragging) {
                moveX = e.pageX - mousemoveX;
                moveY = e.pageY - mousemoveY;
                var pageW = document.documentElement.clientWidth || document.body.clientWidth;
                var pageH = document.documentElement.clientHeight || document.body.clientHeight;
                var divW = ele.offsetWidth;
                var divH = ele.offsetHeight;
                var maxW = pageW - divW;
                var maxH = pageH - divH;
                moveX = Math.min(maxW, Math.max(0, moveX));
                moveY = Math.min(maxH, Math.max(0, moveY));
                ele.style.left = moveX + "px";
                ele.style.top = moveY + "px";
                // console.log(ele.style.left + '===' + ele.style.top);
            }
        });
        ele.addEventListener("mouseup",function(e){
            var e = e || window.event;
            isDragging = false;
            var ifmLeft = gi("iframe").offsetLeft*(0.4);//328
            var ifmTop = gi("iframe").offsetTop*(0.4);//24
            var ifmRight = ifmLeft + gi("iframe").clientWidth*(0.4);//1080
            var ifmBottom = ifmTop + gi("iframe").clientHeight*(0.4);//436
            mousemoveX = e.pageX - ele.offsetLeft;
            mousemoveY = e.pageY - ele.offsetTop;
            var moveX = e.pageX - mousemoveX;
            var moveY = e.pageY - mousemoveY;
            //元素没有移到iframe框内
            if(moveX < ifmLeft || moveY < ifmTop || moveX > ifmRight || moveY > ifmBottom){
                ele.style.left = '920px';
                var dragClass = $(this).attr("class");
                if(dragClass == "drag type"){
                    ele.style.top = '200px';
                }
                if(dragClass == "drag photo"){
                    ele.style.top = "300px";
                }
                if(dragClass == "drag video"){
                    ele.style.top = "400px";
                }
            }
            //元素移进了iframe框内,克隆一个元素.移进去的是克隆的元素
            else{
                var x = e.pageX - mousemoveX;
                var y = e.pageY - mousemoveY;
                // $("body").append($(this).clone(true));
                $(this).css("left",'920px');
                var clone = $(this).clone();
                //为什么在火狐中,拖进去之后边框时有时无呢?
                clone.css('border','1px solid #ccc');//加上边框也没有用?
                var drag = clone.attr("class");
                if(drag == "drag type"){
                    // var marquee = clone.find('marquee');
                    // marquee.attr('direction','left');//设置从右边开始滚动到左边
                    // marquee.attr('behavior','slide');//设置让它滚动一次后就停止滚动
                    $(this).css("top",'200px');
                }
                if(drag == "drag photo"){
                    clone.css("background-image","url('/Cktv/src/img/logo.png')");
                    clone.css("background-size","contain");
                    $(this).css("top","300px");
                }
                if(drag == "drag video"){
                    $(this).css("top",'400px');
                }

                clone.css("left",x);
                clone.css("top",y);//这里的left和top值在火狐浏览器上也有一点问题?有一点偏差?

                $("#iframe").contents().find("body").prepend(clone);
                $("#iframe").contents().find(".drag").attr("title", "");

                // $("#iframe").contents().find("div").css("zoom",2.5);//为什么这句话没有起作用?但是不应该这样设计?因为火狐没有zoom属性,google可以

                var ifmDraggable = $("#iframe").contents().find(".drag");
                ifmEvent(ifmDraggable[0]);
                dragClicktoDelete(ifmDraggable[0]);

                //这里的事件全部都使用jquery的事件,双击\弹框事件等
                var typeDraggable = $("#iframe").contents().find(".type");
                typeDblClick(typeDraggable);

                var photoDraggable = $("#iframe").contents().find(".photo");
                photoDblClick(photoDraggable);
                
                var videoDraggable = $("#iframe").contents().find(".video");
                videoDblClick(videoDraggable);
            }
        });
    }

    //iframe内元素的事件
    /*function ifmDragEvent(ele){
     var mousemoveX = 0;
     var mousemoveY = 0;
     var isDragging = false;
     ele.addEventListener("mousedown",function(e){
     var e = e || window.event;
     mousemoveX = e.pageX - ele.offsetLeft;
     mousemoveY = e.pageY - ele.offsetTop;
     isDragging = true;
     });
     ele.addEventListener("mousemove",function(e){
     var e = e || window.event;
     var moveX = 0;
     var moveY = 0;
     if (isDragging) {
     moveX = e.pageX - mousemoveX;
     moveY = e.pageY - mousemoveY;
     // var pageW = document.documentElement.clientWidth || document.body.clientWidth;
     // var pageH = document.documentElement.clientHeight || document.body.clientHeight;
     var pageW = gi("iframe").clientWidth;
     var pageH = gi("iframe").clientHeight;
     // console.log(pageW +"======"+ pageW*2.5);
     var divW = ele.offsetWidth;
     var divH = ele.offsetHeight;
     var maxW = pageW - divW;
     var maxH = pageH - divH;
     moveX = Math.min(maxW, Math.max(0, moveX));
     moveY = Math.min(maxH, Math.max(0, moveY));
     ele.style.left = moveX + "px";
     ele.style.top = moveY + "px";
     }
     });
     ele.addEventListener("mouseup",function(){
     isDragging = false;
     });
     }
     function ifmResizable(ele){
     //定义一些全局变量,来支持控制元素的拖拽
     var m_ele,
     m_ctrl,
     m_type;
     var moving = 0,//为0表示没有拖拽,不为0表示正在拖拽中,即是否按在控制元素
     m_start_x = 0,//鼠标相对于控制元素开始的时候,x轴的距离,left
     m_start_y = 0,//top
     m_to_x = 0,//鼠标的新的位置
     m_to_y = 0;
     resizable(ele);
     function resizable(el){
     var r = document.createElement("div");
     var b = document.createElement("div");
     var rb = document.createElement("div");
     r.class = r.className = "ui-resizable-r ui-resizable-ctrl";
     b.class = b.className = "ui-resizable-b ui-resizable-ctrl";
     rb.class = rb.className = "ui-resizable-rb ui-resizable-ctrl";
     el.appendChild(r);
     el.appendChild(b);
     el.appendChild(rb);
     //为控制元素添加鼠标事件
     r.addEventListener("mousedown",function(e){
     on_mousedown(e,el,r,"r");
     });
     b.addEventListener('mousedown',function(e){
     on_mousedown(e,el,b,"b");
     });
     rb.addEventListener('mousedown',function(e){
     on_mousedown(e,el,rb,"rb");
     })
     }
     function on_mousedown(e,ele,ctrl,type){
     var e = e || window.event;
     m_start_x = e.pageX - ctrl.offsetLeft;
     m_start_y = e.pageY - ctrl.offsetTop;

     m_ele = ele;
     m_ctrl = ctrl;
     m_type = type;

     moving = setInterval(on_mousemove,10);
     }
     function on_mousemove(){
     if(moving){
     var min_left = m_ele.offsetLeft;
     var min_top = m_ele.offsetTop;

     var to_x = m_to_x - m_start_x;
     var to_y = m_to_y - m_start_y;

     to_x = Math.max(to_x,min_left);
     to_y = Math.max(to_y,min_top);

     switch(m_type){
     case "r":
     m_ctrl.style.left = to_x + 10 + 'px';
     m_ele.style.width = to_x + 10 +'px';
     break;
     case "b":
     m_ctrl.style.top = to_y + 10 + 'px';
     m_ele.style.height = to_y + 10 + 'px';
     break;
     case "rb":
     m_ctrl.style.left = to_x + 20 + 'px';
     m_ctrl.style.top = to_y + 20 + 'px';

     m_ele.style.width = to_x + 20 + 'px';
     m_ele.style.height = to_y + 20 + 'px';
     break;
     }
     }
     }
     gi("iframe").contentWindow.document.onmousemove = function(e){
     var e = e || window.event;
     m_to_x = e.pageX;
     m_to_y = e.pageY;
     };
     gi("iframe").contentWindow.document.onmouseup = function(e) {
     clearInterval(moving);
     moving = 0;
     var cls = gi("iframe").contentWindow.document.getElementsByClassName("ui-resizable-ctrl");
     for(var i = 0;i<cls.length;i++){
     cls[i].style.left = "";
     cls[i].style.top = "";
     }
     };
     }*/
    function ifmEvent(ele){
        var isDragging = false;
        var isMoving = false;
        function resizable(el){
            var r = document.createElement("div");
            var b = document.createElement("div");
            var rb = document.createElement("div");
            r.class = r.className = "ui-resizable-r ui-resizable-ctrl";
            b.class = b.className = "ui-resizable-b ui-resizable-ctrl";
            rb.class = rb.className = "ui-resizable-rb ui-resizable-ctrl";
            el.appendChild(r);
            el.appendChild(b);
            el.appendChild(rb);
        }
        resizable(ele);
        ele.addEventListener("mousedown",function(e){
            var e = e || window.event;
            var x = e.pageX;
            var y = e.pageY;
            var x1 = this.offsetLeft;
            var y1 = this.offsetTop;
            var mousePosX = x - x1;
            var mousePosY = y - y1;
            var x2 = x1 + this.clientWidth;
            var y2 = y1 + this.clientHeight;
            var posX = Math.abs(x - x1) > Math.abs(x - x2) ? Math.abs(x - x2) : Math.abs(x - x1);
            var posY = Math.abs(y - y1) > Math.abs(y - y2) ? Math.abs(y - y2) : Math.abs(y - y1);
            if(posX < 20 && posY > 20){
                isMoving = true;
                gi("iframe").contentWindow.document.addEventListener("mousemove",function(e){
                    if(isMoving){
                        ele.style.width = e.pageX - ele.offsetLeft + "px";
                        var widths = e.pageX - ele.offsetLeft + "px";
                        ele.setAttribute('width',widths);
                    }
                });
                gi("iframe").contentWindow.document.addEventListener("mouseup",function(e) {
                    isMoving = false;
                });
            }
            else if(posY < 20 && posX > 20){
                isMoving = true;
                gi("iframe").contentWindow.document.addEventListener("mousemove",function(e){
                    if(isMoving){
                        ele.style.height = e.pageY - ele.offsetTop + "px";
                        var heights = e.pageY - ele.offsetTop + "px";
                        ele.setAttribute('height',heights);
                    }
                });
                gi("iframe").contentWindow.document.addEventListener("mouseup",function(e) {
                    isMoving = false;
                });
            }
            else if(posX < 20 && posY < 20){
                isMoving = true;
                gi("iframe").contentWindow.document.addEventListener("mousemove",function(e){
                    if(isMoving){
                        ele.style.width = e.pageX - ele.offsetLeft + "px";
                        ele.style.height = e.pageY - ele.offsetTop + "px";
                        var widths = e.pageX - ele.offsetLeft + "px";
                        ele.setAttribute('width',widths);
                        var heights = e.pageY - ele.offsetTop + "px";
                        ele.setAttribute('height',heights);
                    }
                });
                gi("iframe").contentWindow.document.addEventListener("mouseup",function(e) {
                    isMoving = false;
                });
            }
            else if(posX > 20 && posY > 20){
                isDragging = true;
                ele.addEventListener("mousemove",function(e){
                    var e = e || window.event;
                    var moveX = 0;
                    var moveY = 0;
                    if (isDragging) {
                        moveX = e.pageX - mousePosX;
                        moveY = e.pageY - mousePosY;
                        // var pageW = document.documentElement.clientWidth || document.body.clientWidth;
                        // var pageH = document.documentElement.clientHeight || document.body.clientHeight;
                        var pageW = gi("iframe").clientWidth;
                        var pageH = gi("iframe").clientHeight;
                        // console.log(pageW +"======"+ pageW*2.5);
                        var divW = ele.offsetWidth;
                        var divH = ele.offsetHeight;
                        var maxW = pageW - divW;
                        var maxH = pageH - divH;
                        moveX = Math.min(maxW, Math.max(0, moveX));
                        moveY = Math.min(maxH, Math.max(0, moveY));
                        ele.style.left = moveX + "px";
                        ele.style.top = moveY + "px";
                    }
                });
                ele.addEventListener("mouseup",function(){
                    isDragging = false;
                });
            }
        });
    }
    function borderShake(ele, times) {
        var i = 0;
        var t = false;
        if (t) {
            return;
        }
        t = setInterval(function () {
            i++;
            // var c = i % 2 ? "4px solid rgb(60,159,251);" : "";
            // ele.attr('style', c);
            ele.style.border = "4px solid rgb(60,159,251)";
            if (i == 2 * times) {
                clearInterval(t);
                // ele.attr("");
                ele.style.border = "";
            }
        }, 200);
    }
    function addDeleteLogo(ele){
        var deleteLogo = document.createElement("span");
        deleteLogo.class = deleteLogo.className = "delete_logo";
        deleteLogo.innerHTML = "X";
        ele.appendChild(deleteLogo);
    }
    function dragClicktoDelete(ele) {
        ele.addEventListener("click",function(){
            // ele.style.borderColor = "red";
            borderShake(this,3);
            gi("iframe").contentWindow.document.onkeydown = function(e){
                var e = e || window.event;
                if(e && e.keyCode == 8){
                    e.returnValue = false;
                    e.preventDefault();//兼容浏览器,禁止backspace或delete的默认行为
                    gi("iframe").contentWindow.document.body.removeChild(ele);
                }
            }
        });
    }
    /*function ifmDblclick(ele) {
        if(ele.className == "drag type"){
            ele.addEventListener("dblclick",function(e){
                var e = e || window.event;
                e.stopPropagation();
                var $this = $(this);
                var index;
                $("#font_input").text($.trim($(".type").text()));
                index = layer.open({
                    type: 1,
                    title: ['请输入文字', 'font-size:18px'],
                    // skin: 'layui-layer-rim',
                    content: $("#layer_type_content")
                });
                $("#layer_type_content .confirm").click(function(){
                    var font_size = $("select[name='font_size'] option:selected").text();
                    var font_color = $("select[name='font_color'] option:selected").text();
                    if(font_color == "黑色"){
                        font_color = "black";
                    }else if(font_color == "红色"){
                        font_color = "red";
                    }else if(font_color == "蓝色"){
                        font_color = "blue";
                    }
                    var font_content = $("#font_input").val();
                    $this[0].style.fontSize = font_size + "px";
                    $this[0].style.color = font_color;
                    var content = ($this[0].childNodes)[1];//第1个元素是text注意
                    // $this[0].css("font-size",font_size + "px");
                    // $this[0].css("color",font_color);
                    // var content = ($this[0].children())[0];
                    console.log(typeof content);
                    content.innerHTML = font_content;
                    layer.close(index);
                });
                $("#layer_type_content .cancel").click(function(){
                    layer.close(index);
                });
            });
        }else if(ele.className == "drag photo"){
            ele.addEventListener("dblclick",function(){
                var $this = $(this);
                console.log($this);
                var index;
                index = layer.open({
                    type:1,
                    title:['选择图片并预览','font-size:18px'],
                    content: $("#layer_photo_content")
                });
                var file,src;
                $("#layer_photo_content .upload").change(function(){
                    file = gc("upload")[0].files[0];
                    src = window.URL.createObjectURL(file);
                    gc("preview")[0].setAttribute("src",src);
                });
                $("#layer_photo_content .confirm").click(function(){
                    var upload_photo = gi("pic_form");
                    var formData = new FormData(upload_photo);
                    $.ajax({
                        url: '/Cktv/tpl/uploadPicture',
                        data: formData,
                        processData: false,// 告诉jQuery不要去处理发送的数据
                        contentType: false,// 告诉jQuery不要去设置Content-Type请求头
                        type: 'post',
                        success: function (data) {
                            var getData;
                            if ((typeof data) == 'string') {
                                getData = JSON.parse(data);
                            } else if ((typeof data) == 'object') {
                                getData = eval(data);
                            }
                            var url = getData.picturePath;
                            console.log(url);
                            ele.style.backgroundImage = "url('" + url + "')";
                            ele.style.backgroundSize = "cover";
                        },
                        error: function (err) {
                            console.log(err);
                        }
                    });
                    // $this[0].style.backgroundImage = "url('" + url + "')";
                    // $this[0].style.backgroundSize = "contain";
                    layer.close(index);
                });
                $("#layer_photo_content .cancel").click(function(){
                    layer.close(index);
                })

            });
        }
    }*/
    /*针对文字部分的事件*/
    /*rgb进制转化16进制*/
    var reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/;
    String.prototype.colorHex = function (){
        var that = this;
        if(/^(rgb|RGB)/.test(that)){
            var aColor = that.replace(/(?:\(|\)|rgb|RGB)*/g,"").split(",");
            var strHex = "#";
            for(var i=0; i<aColor.length; i++){
                var hex = Number(aColor[i]).toString(16);
                if(hex === "0"){
                    hex += hex;
                }
                strHex += hex;
            }
            if(strHex.length !== 7){
                strHex = that;
            }
            return strHex;
        }else if(reg.test(that)){
            var aNum = that.replace(/#/,"").split("");
            if(aNum.length === 6){
                return that;
            }else if(aNum.length === 3){
                var numHex = "#";
                for(var i=0; i<aNum.length; i+=1){
                    numHex += (aNum[i]+aNum[i]);
                }
                return numHex;
            }
        }else{
            return that;
        }
    };
    var font_colors;
    function typeDblClick(words){
        words.unbind();
        words.dblclick(function(){
            //$(this)当前双击的对象
            var type_content = $(this).find('.type_content');

            //字体大小事件
            var font_size = type_content.css('font-size');
            var len = font_size.length;
            font_size = font_size.slice(0,len-2);
            $("#font_size option[value=" + font_size +"]").prop('selected','selected');

            //字体颜色事件
            // gi("color_picker").value = font_colors;
            var font_color = type_content.css('color');
            // alert(font_color);
            var fontColor = font_color.colorHex();
            // alert(fontColor);
            gi("color_picker").value = fontColor;

            //输入框事件
            var divContent = $.trim($(this).find('.type_content').text());
            $('#font_input').text(divContent);

            typeSaveClick($(this));
        });
    }
    function typeSaveClick(ele){
        $('#layer_type_content').modal();
        /*保存按钮的点击事件*/
        $("#layer_type_content .save").unbind();
        $("#layer_type_content .save").click(function(){
            var font_size = $("select[name='font_size'] option:selected").text();
            font_colors = $("#color_picker")[0].value;
            ele.css('font-size',font_size + "px");
            ele.css('color',font_colors);

            var user_data = $("#font_input").val();//获取到用户输入到textarea的内容
            ele.find('.type_content').html(user_data);

            $('#layer_type_content').modal('hide');
            $('#type_form')[0].reset();
        });
    }
    /*针对图片部分的事件*/
    function photoDblClick(photos) {
        photos.unbind();
        photos.dblclick(function () {
            photoSaveClick($(this));
        });
    }
    function photoSaveClick(ele) {
        $("#layer_photo_content").modal();
        $("#layer_photo_content .save").unbind();
        $("#layer_photo_content .save").click(function () {
            var fileImg = $('#fileImg').val();
            if(fileImg == ''){
                layer.tips('请选择图片','#fileImg',{
                    tips: [2,'#000']
                });
            }else {
                var tpl_id = $('#tpl_id').attr('data-tpl_id');
                var url = "/Cktv/tpl/uploadPicture/" + tpl_id;
                var fileImg = new FormData($('#photo_form')[0]);
                $.ajax({
                    url: url,
                    type: "post",
                    cache: false,
                    data: fileImg,
                    processData: false,
                    contentType: false,
                    success: function(data){
                        var url = data.picturePath;
                        ele.attr('style', "background-image:url('" + url + "');background-size:100%,100%;");

                        $('#layer_photo_content').modal('hide');
                        $('#photo_form')[0].reset();
                    },
                    error: function(err){
                        layer.msg(err.msg);
                    }
                });
            }
        });
    }
    $("#fileImg").change(function () {
        var f = gi("fileImg").files[0];
        var src = window.URL.createObjectURL(f);
        gi("preview").src = src;
    });
    /*针对视频部分的事件*/
    function videoDblClick(videos) {
        videos.unbind();
        videos.dblclick(function () {
            // $('#video-tpl-mask').removeClass('hide');
            var url = "/Cktv/video/allVideos/" + 1 + "/" + 10;
            ajax.post(url,null,function (data) {
                var videoList = Handlebars.compile($('#videoListTemplate').html());
                $('#video-choose').html(videoList(data.videos));
                $('#layer_video_content').modal();
                videoSaveClick($(this));
            });
        });
    }

    function videoSaveClick(ele) {
        $('#layer_video_content .save').unbind();
        $('#layer_video_content .save').click(function(){
            alert(1);
            var video = $(".basicInfo input[name='video']:checked");
            if(video.length == 0){
                layer.msg('请选择视频');
            }else{
                var source = video.parent().next().children('img').attr('video_url');
                ele.attr('src',source);
                ele.attr('controls',true);
                $('#layer_video_content').modal('hide');
            }
            
        });
    }
    
    
    function save(){
        gi("save").addEventListener("click",function(){
            var ifm_content = gi("iframe").contentWindow.document.getElementsByTagName("div");
            if(ifm_content.length == 0){
                layer.msg("请添加元素...");
            }else{
                gi("iframe").style.zoom = 1;
                $("#iframe").contents().find('body').css('zoom', 2.5);
                var object = $("#iframe").contents()[0].documentElement;
                var params = {
                    html: object.innerHTML
                };
                var tpl_id = $('#tpl_id').attr('data-tpl_id');
                var url = "/Cktv/tpl/saveTplHtml/" + tpl_id;
                ajax.post(url,params,function(getData){
                    if (getData.success) {
                        alert(2);
                        location.href = '/Cktv/pages/display-manage/publish-list.html';
                    }else {
                        layer.msg(getData.msg);
                    }
                });
            }
        })
    }
    
    gi("iframe").src = "/Cktv/pages/userTemplate-manage/iframe.html";
    dragEvent(gc("type")[0]);
    dragEvent(gc("photo")[0]);
    dragEvent(gc("video")[0]);
    save();
};