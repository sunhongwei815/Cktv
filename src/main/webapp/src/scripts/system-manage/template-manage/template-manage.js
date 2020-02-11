/**
 * Created by asus on 2016/6/19.
 */
$(document).ready(function(){
    $("#add").click(function(){
        index = layer.open({
            type: 1,
            title: '新增模板', //样式类名
            closeBtn: 1, //显示关闭按钮
            shift: 2,
            shadeClose: true, //开启遮罩关闭
            area:['430px','400px'],
            maxmin:true,
            content: $('#wrap')
        });
    });
    $('#close').click(function(){
        layer.msg("已取消！");
        layer.close(index);
    });
    $("#submit").click(function(){
        $("#submit").attr("value","上传中");
        var formData=new FormData($("#uploadForm")[0]);
        var file=$(".files input[name='file']").val();
        var img=$(".pict input[name='thumb']").val();
        var tpl_model=$(".model input[name='tpl_model']:checked").val();
        var is_video=$(".video input[name='is_video']:checked").val();
        if(file==""){
            layer.msg("未选择文件！");
            $("#submit").attr("value","提交");
        }else if(img==""){
            layer.msg("未选择图片！");
            $("#submit").attr("value","提交");
        }else if(tpl_model=="undefined"){
            layer.msg("模板模式未选择！");
            $("#submit").attr("value","提交");
        }else if(is_video=="undefined"){
            layer.msg("未选定模板是否含有视屏！");
            $("#submit").attr("value","提交");
        }else{
            $.ajax({
                url:"/Cktv/tpl/upload/",
                type:"post",
                data:formData,
                async:false,
                cache:false,
                contentType:false,
                processData:false,
                success:function(data){
                    var result;
                    if((typeof data) == 'String'){
                        result = JSON.parse(data);
                    } else if((typeof data) == 'Object' || 'Array'){
                        result = eval(data);
                    }
                    if(result.success){
                        layer.msg("上传成功！");
                        location.reload(true);
                    }else if(result.msg=="模板重名！"){
                        layer.msg("文件重名，上传失败！");
                        $("#submit").attr("value","提交");
                    }else{
                        layer.msg("上传失败！");
                        $("#submit").attr("value","提交");
                    }
                },
                error:function(data){
                    layer.msg("上传失败了！");
                }
            });
        }
    })
});
function visitTpl(){
    $(".visit").click(function(){
        var visit=$(this).attr("myTpl_visit");
        var host=window.location.host;
        var visit_url=host+visit;
        console.log(visit_url);
        layer.open({
            type: 2,
            title: '模板预览', //样式类名
            closeBtn: 1, //显示关闭按钮
            shift: 2,
            shadeClose: true, //开启遮罩关闭
            area:['700px','500px'],
            maxmin:true,
            content: "http://"+visit_url
        });
    })
}
function deleteTpl(){
    $(".det").click(function(){
        var tpl_id = $(this).find(".delete").attr("myTpl_id");
        var tpl_div = $(this).parent('.box');
        layer.confirm("确定删除？",
            {btn:['确定','取消']},
            function(){
                var url = '/Cktv/tpl/deleteTplByTpl_id'+'/'+tpl_id;
                ajax.post(url,null,function(data){
                    if(data.success){
                        tpl_div.remove();
                        layer.msg("删除成功！");
                        location.reload(true);

                    }else{
                        layer.msg("删除失败！");
                    }
                });
            },function(){
                layer.msg("已取消删除！",{
                    time: 1000, //1s后自动关闭
                });
            }
        )
    });
}
function download(){
    $(".download").click(function(){
        var tpl_id = $(this).attr("myTpl_id");
        var url = '/Cktv/tpl/download'+'/'+tpl_id;
        ajax.post(url,null,function(data){
            window.location.href = eval(data).url;
            /*var result=eval(data);
            var str=result.url;
            var strArr = str.split("\\");
            var fileName = strArr[strArr.length-1];
            var host=window.location.host;
            $(".fileDown").attr("href","http://"+host+"/"+str);
            $(".fileDown").attr("download",fileName);*/
        })
    })
}

window.onload = function () {
    /*注册一个分页按钮的helper*/
    Handlebars.registerHelper('page',function (sum, options) {
        var html = '';
        for(var i = 1; i <= sum; i++){
            html = html + options.fn(i);
        }
        return html;
    });
    /*定义ajax函数*/
    function sendAjax(startPage,eachpagesize,el) {
        $.ajax({
            type: 'post',
            url: '/Cktv/tpl/selectTplsByPages/' + startPage +'/' + eachpagesize,
            success: function (getData) {
                var tpl = document.getElementById("template-list").innerHTML;
                var template = Handlebars.compile(tpl);
                var context = template(getData.tpls);
                document.getElementById("main").innerHTML = context;

                var pagelist = document.getElementById('page-list').innerHTML;
                var pagetpl = Handlebars.compile(pagelist);
                pagebtn = Math.ceil((getData.sumNum)/eachpagesize);
                var pagetxt = pagetpl(pagebtn);
                document.getElementById('page').innerHTML = pagetxt;

                console.log(startPage);
                console.log(el);
                if(el != undefined){
                    el.css('color','blue');
                    el.css('backgroundColor','red');
                }


                
                visitTpl();
                deleteTpl();
                download();

                btnclick();
            },
            error:function(e){
                layer.msg(e);
            }
        });
    }
    //页面一加载就发ajax请求数据,渲染模板列表
    var cur = 1;
    var pagebtn;
    var size = 9;
    sendAjax(cur,size);
    function btnclick() {
        $('.eachbtn').unbind();
        $('.eachbtn').on('click',function () {
            var curclick = $(this).html();
            cur = curclick;
            sendAjax(cur,size,$(this));
        })
    }
    $('#prev').on('click',function () {
        if(cur == 1){
            layer.msg('当前已是第1页!')
        }else{
            sendAjax(--cur,size,$(this));
        }
    });
    $('#next').on('click',function () {
        if(cur == pagebtn){
            layer.msg('当前已是最后一页!');
        }else{
            sendAjax(++cur,size,$(this));
        }
    })

};
//绑定鼠标滚动事件
/*
window.onscroll = function () {
    if (checkscrollside()) {
        $.ajax({
            type: 'post',
            url: "/Cktv/tpl/allTpls",
            // data:data,//data没有
            success: function (data) {
                var tpl = document.getElementById("template-list").innerHTML;
                var template = Handlebars.compile(tpl);
                var context = template(data);
                document.getElementById("main").innerHTML = context;
                visitTpl();
                deleteTpl();
                download();
            },
            error: function (e) {
                alert(e);
            }
        })

    }
}
//获得class元素(有待完善)
function getByClass(parent, clsName) {
    var boxArr = new Array();
    var oElements = parent.getElementsByTagName('*');
    for (var i = 0; i < oElements.length; i++) {
        if (oElements[i].className == clsName) {
            boxArr.push(oElements[i]);
        }
    }
    return boxArr;
}
//设置滚动条件
function checkscrollside() {
    var oParent = document.getElementById('main');
    var oBoxs = getByClass(oParent, 'box');
    var lastBOXH = oBoxs[oBoxs.length - 1].offsetTop + Math.floor(oBoxs[oBoxs.length - 1].offsetHeight / 2);
    var scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
    var height = document.body.clientHeight || document.documentElement.clientHeight;
    //console.log(scrollTop);
    //console.log(height);
    return (lastBOXH < scrollTop + height) ? true : false;
}
*/
