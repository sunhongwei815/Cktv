/**
 * Created by Gy on 2016/6/24.
 */
function getChosenvideoInfos(){
    var arr = new Array();
    $("tbody input[type='checkbox']:checked").each(function() {
        var video = new Object();
        video.video_name = $(this).val();
        video.video_did = $(this).attr("video_did");
        arr.push(video);
    });
    return arr;
}
var page;

function send(p) {
    var url = "/Cktv/video/allVideos"+"/"+p+"/"+3;
    $.ajax({
        url:url,
        type: 'post',
        async: false,
        success:function(data){
            var result=eval(data);
            console.log(result);
            page=result.sumPage;
            var myTemplate = Handlebars.compile($("#video_list_template").html());
            $('#table-list').html(myTemplate(result.videos));

        }
    })
}
$("#fileId").click(function(){
    $('.submit').show();
})
$('#close').click(function(){
    $('.submit').hide();
})
function doUpload(){
    $('.submit').hide();
    var formData=new FormData($("#uploadForm")[0]);
    $.ajax({
        url:"/Cktv/video/uploadVideo",
        type:"post",
        data:formData,
        async:false,
        cache:false,
        contentType:false,
        processData:false,
        success:function(data){
            var result = eval(data);
            console.log(result);
            alert(result.msg);
            if(result.success==true){location.reload();}
        },
        error:function(returndata){
            alert("发送失败了！");
        }
    })
}
$(function () {
    //查询
    send(1);//进入页面直接加载第一页设备表；
    $(".tcdPageCode").createPage({
        pageCount:page,
        current:1,
        backFn:function(page){
            send(page);
            console.log("页数："+page);
        }
    });

    //全选、取消全选的事件
    $('#SelectAll').click(function () {
        $("tbody input[name='mycheck']").attr('checked',this.checked);
    });

    //为操作按钮绑定点击事件
    //$("#fileId").change(function(){
    //    console.log($( '#uploadForm').serialize())
    //    $.ajax({
    //        url : "/Cktv/video/uploadVideo",
    //        type : "POST",
    //        data : $( '#uploadForm').serialize(),
    //        success : function(data) {
    //            $( '#serverResponse').html(data);
    //        },
    //        error : function(data) {
    //            $( '#serverResponse').html(data.status + " : " + data.statusText + " : " + data.responseText);
    //        }
    //    });
    //})
    $(".delete").click(function(){
        var video_id=$(this).attr('video_id');
        $.ajax({
            url : "/Cktv/video/deleteVideoByVideo_id/"+video_id,
            type : "POST",
            success : function(data) {
                $( '#serverResponse').html(data);
                location.reload();
            },
            error : function(data) {
                $( '#serverResponse').html(data.status + " : " + data.statusText + " : " + data.responseText);
            }
        });
    })

    $('.operate-type').on('click',function () {

        var arrayList=new Array();
        $('input[name=mycheck]:checked').each(function () {
            var video_id=parseInt($(this).attr('video_id'));
            arrayList.push(video_id);
        });
        console.log(arrayList);

        var url = "/Cktv/video/deleteVideos";
        $.ajax({
            url: url,
            type: 'POST',
            data : {
                video_ids: arrayList
            },
            success: function(data){
                console.log(data);
            }
        });
    })
    $(".video").click(function(){
        var u=$(this).attr("video_url");
        console.log(u);
        layer.open({
            type: 2,
            title: false,
            content: u,
            area: ['720px', '480px'],
        });
    })
});