/**
 * Created by mgh、ssl、gyh on 2016/4/20.
 */
$(document).ready(function () {
    //加载层
    var opts = {
        lines: 10,//花瓣数目
        length: 0,//花瓣长度
        width: 17,//花瓣宽度
        radius: 20,//花瓣距离中心的半径
        scale: 1,
        corners: 1,//花瓣圆滑度
        opacity: 0.5,
        speed: 1,//花瓣旋转速度
        trail: 100//花瓣旋转时的拖影
    };
    var spinner = new Spinner(opts);
    var target = document.getElementById('publish-load');

    //handlebars实现内容的呈现
    var activePage = 1;
    currPage(activePage);
    var sumpage;

    function cb(getData){
        spinner.spin();//取消加载状态
        $('#publish-load').css('display', 'none');//隐藏加载框
        //handlebars渲染模板
        var myPublish = Handlebars.compile($('#publish_list_template').html());
        //注册一个helper，控制最多展示四个模板缩略图
        //count = 0, num = 4,options = Object {name: "compare", hash: Object, data: Object}
        Handlebars.registerHelper("compare", function (count, num, options) {
            if ((count + 1) <= num) {
                return options.fn(this);
                /*
                * options.fn(this)
                *
                 "<div class="late-post">
                 <img src="/Cktv/src/upload/tpl/model3c/bg.jpg" alt="海报内容">
                 </div>
                 "
                *...
                * */
            }
        });
        //根据横竖屏给模板缩略图设置高度
        $('#publish-list').html(myPublish(getData.publishs));

        //handlebars渲染页数
        sumpage = getData.sumpage;
        var pages = Handlebars.compile($('#pageNumTemplate').html());
        //注册一个helper，渲染分页按钮
        //sumPage = 1, options = Object{name: "eachPage", hash: Object, data: Object}
        Handlebars.registerHelper("eachPage", function (sumPage, options) {
            var html = "";
            for (var i = 1; i <= sumPage; i++) {
                html = html + options.fn(i);
            }
            return html;
            /*
            * html
             "<button class="btn btn-default numPage" data-page=1>1</button>
             "
            * */
        });
        $('#pageNum').html(pages(sumpage));

        paging();
        edit();
        deletepublish();
    }

    function currPage(page,tp1_model) {
        $('#publish-list').html('');//清空数据
        spinner.spin(target);
        $('#publish-load').css('display', 'block');
        //对屏幕模式的判断
        if(tp1_model == "1"){
            var url = '/Cktv/publish/selectPublishsByScreen_mode/'+page+'/'+5+'/'+tp1_model;
            ajax.post(url,null,function (getData) {
                cb(getData);
            });
        }else if(tp1_model == "2"){
            var url = '/Cktv/publish/selectPublishsByScreen_mode/'+page+'/'+5+'/'+tp1_model;
            ajax.post(url,null,function (getData) {
                cb(getData);
            });
        }else{
            var url = '/Cktv/publish/selectUserPublishsByPage/' + page + "/" + 5;
            ajax.post(url, null, function (getData) {
                /*
                * getData = Object {sumpage: 1, publishs: Array[3]}
                * */
                cb(getData);
            });
        }
    }
    //屏幕模式点击事件
    $('#hengping').click(function(){
        currPage(1,"1");
    });
    $('#shuping').click(function(){
        currPage(1,"2");
    });
    $('#quanbu').click(function(){
        currPage(1);
    });
    //专辑分页点击事件
    function paging() {
        $('.paging-tab .numPage').on('click', function () {
            $('#prePage').removeClass('disabled');
            $('#nextPage').removeClass('disabled');
            activePage = $(this).attr('data-page');
            $('#publish-list').html('');
            var tp1_model = $("input[name='screen']:checked").attr('tp1_model');
            currPage(activePage,tp1_model);
        });
    }

    $('#prePage').on('click', function () {
        $('#prePage').removeClass('disabled');
        $('#nextPage').removeClass('disabled');
        if (activePage == 1) {
            $(this).addClass('disabled');
        } else {
            $('#publish-list').html('');
            var tp1_model = $("input[name='screen']:checked").attr('tp1_model');
            currPage(--activePage,tp1_model);
        }

    });
    $('#nextPage').on('click', function () {
        $('#prePage').removeClass('disabled');
        $('#nextPage').removeClass('disabled');
        if (activePage == sumpage) {
            $(this).addClass('disabled');
        } else {
            $('#publish-list').html('');
            var tp1_model = $("input[name='screen']:checked").attr('tp1_model');
            currPage(++activePage,tp1_model);
        }
    });

    //跳转至新增发布的页面
    var edit = function () {
        $(".edit").click(function () {
            var publish_id = $(this).attr("publish_id");
            location.href = '/Cktv/publish/publishAddPage/' + publish_id;
        });
    };
    //对发布内容进行删除操作
    var deletepublish = function () {
        $(".delete").click(function () {
            var deletetr = $(this).parents("tr");
            var publish_id = $(this).attr("publish_id");
            layer.confirm("是否确定要删除？", {
                btn: ['是', '否']
            }, function () {
                var url = '/Cktv/publish/deletePublishByPublish_id/' + publish_id;
                ajax.post(url,null,function (getData) {
                    /*
                    * getData = Object {msg: "删除成功", success: true}
                    * */
                   if(getData.success){
                       layer.msg(getData.msg);
                       deletetr.remove();
                   }else{
                       layer.msg(getData.msg);
                   }
                });
            },function () {
                layer.msg("已取消");
            });
        });
    }
});