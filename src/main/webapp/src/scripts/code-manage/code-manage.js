
$(document).ready(function(){
    //加载显示所有的设备码
    var type_code_used = $(".used-code").attr("type_code");
    var type_code_unused = $(".unused-code").attr("type_code");
    var pageSize = 10;

    $.ajax({
    url:'/Cktv/verify_code/selectVerify_codeByIs_register/'+type_code_used+'/'+1+'/'+pageSize,
        type:'post',
        success:function(data){

            var lengthOfVerifyUsed = Math.ceil(data.lengthOfVerify_code/10);
            var myTemplate = Handlebars.compile($("#code-list").html());
            $("#table-list").html(myTemplate(data.verify_codes));
            turnPageOfVerify_codeUsed(lengthOfVerifyUsed);
        },
        error:function(){
            alert("加载失败！");
        }
    });

    //已使用注册码
    $(".used-code").click(function(){
        $(".delete-code").css("display","none");
        $(".code-unused").css("display","none");
        $(".code-used").css("display","block");
        $.ajax({
            url:'/Cktv/verify_code/selectVerify_codeByIs_register/'+type_code_used+'/'+1+'/'+pageSize,
            type:'post',
            async: false,
            success:function(data){
                var result;
                if((typeof data) == 'String'){
                    result = JSON.parse(data);
                } else if((typeof data) == 'Object'|| 'Array'){
                    result = eval(data);
                }
                var lengthOfVerifyUsed = Math.ceil(result.lengthOfVerify_code/10);
                var myTemplate = Handlebars.compile($("#code-list").html());
                $("#table-list").html(myTemplate(result.verify_codes));
                $(".checkbox-selected").css("display","none");
                turnPageOfVerify_codeUsed(lengthOfVerifyUsed)
            },
            error:function(){
                alert("加载失败！");
            }
        });
    });
    //未使用注册码，
    $(".unused-code").click(function(){
        $(".code-unused").css("display","block");
        $(".code-used").css("display","none");
        $.ajax({
            url: '/Cktv/verify_code/selectVerify_codeByIs_register/'+type_code_unused+'/'+1+'/'+pageSize,
            type: 'post',
            success:function(data){
                console.log(data);
                $(".delete-code").css("display","inline-block");
                var lengthOfVerifyUnused = Math.ceil(data.lengthOfVerify_code / 10);
                var myTemplate = Handlebars.compile($("#code-list").html());
                $("#table-list").html(myTemplate(data.verify_codes));
                $(".checkbox-selected").css("display","block");
                turnPageOfVerify_codeUnused(lengthOfVerifyUnused);
                deleteMode();
            },
            error:function(){
                alert("加载失败！");
            }
        });
    })

    //添加设备
    $(".add-device-code").click(function(){
        layer.open({
            type: 1,
            skin: 'demo-class',
            title: ['添加设备码','font-size:20px;font-family:Microsoft YaHei'],
            area: ['500px','400px'],
            closeBtn: false,
            content: $("#form_parts"),
            btn:['确定','取消'],
            yes:function(){
                var number = $(".number_code").val();
                var length = $(".count_code").val();
                $.ajax({
                    url:'/Cktv/verify_code/generateActive_codes/'+number+'/'+length,
                    type: 'post',
                    async: false,
                    success:function(data){
                        var result;
                        if((typeof data) == 'String'){
                            result = JSON.parse(data);
                        } else if((typeof data) == 'Object'|| 'Array'){
                            result = eval(data);
                        }
                        if(result.success){
                            layer.msg("添加成功！");
                            location.href = "/Cktv/pages/system-manage/code-manage/code-manage.html"
                        } else{
                            layer.msg("添加失败！");
                        }
                    },
                    error:function(){
                        alert("加载失败！");
                    }
                });
            },
        });
    });

    //未使用激活码翻页
    function turnPageOfVerify_codeUnused(lengthOfVerifyUnused){
        $(".tcdPageCodeUnused").createPage({
            pageCount:lengthOfVerifyUnused,
            current:1,
            backFn:function(pageNow){
                console.log(lengthOfVerifyUnused);
                $(".code-used").css("display","none");
                $(".code-unused").css("display","block");
                $.ajax({
                    url: '/Cktv/verify_code/selectVerify_codeByIs_register/' + type_code_unused + '/' + pageNow + '/' +pageSize,
                    type: 'post',
                    success: function (data) {
                        $(".delete-code").css("display", "inline-block");
                        var result;
                        if ((typeof data) == 'String') {
                            result = JSON.parse(data);
                        } else if ((typeof data) == 'Object' || 'Array') {
                            result = eval(data);
                        }
                        var myTemplate = Handlebars.compile($("#code-list").html());
                        $("#table-list").html(myTemplate(result.verify_codes));
                        $(".checkbox-selected").css("display","block");
                        deleteMode()
                    }
                });
            }
        });
    }

    //已使用激活码翻页
    function turnPageOfVerify_codeUsed(lengthOfVerifyUsed){
        $(".tcdPageCodeUsed").createPage({
            pageCount:lengthOfVerifyUsed,
            current:1,
            backFn:function(pageNow){
                $(".code-used").css("display","block");
                $(".code-unused").css("display","none");
                $(".delete-code").css("display","none");
                $.ajax({
                    url:'/Cktv/verify_code/selectVerify_codeByIs_register/'+type_code_used+'/'+pageNow+'/'+pageSize,
                    type:'post',
                    success:function(data){
                        $(".delete-code").css("display","none");
                        var result;
                        if((typeof data) == 'String'){
                            result = JSON.parse(data);
                        } else if((typeof data) == 'Object'|| 'Array'){
                            result = eval(data);
                        }
                        var myTemplate = Handlebars.compile($("#code-list").html());
                        $("#table-list").html(myTemplate(result.verify_codes));
                    },
                    error:function(){
                        alert("加载失败！");
                    }
                });
            }
        });
    }

    //单独选择后删除
    function deleteMode(){
        var checkAll;
        var flag = false;
        var verify_code_ids = new Array();
        $(".select-all-delete").find("input").change(function(){
            checkAll = $(".select-all-delete").find("input").prop("checked");
            if (checkAll) {
                $(".select-delete").find("input").prop("checked",true);
            }else{
                $(".select-delete").find("input").prop("checked",false);
            }
        });

        $(".delete-device-code").click(function(){

            $(".select-delete").find("input[type='checkbox']").each(function(){
                if($(this).prop("checked")){
                    flag = true;
                }
            });
            if(flag){
                $(".select-delete").find("input[type='checkbox']").each(function(){
                    if($(this).prop("checked")){
                        verify_code_ids.push($(this).val());
                    }
                });
            }
            console.log(verify_code_ids);
            //删除的请求
            if (verify_code_ids.length == 0) {
                layer.msg("请选择您要删除的设备码！", {time: 800, icon: 2});
            } else {
                layer.confirm('确定要删除吗？', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    var url = "/Cktv/verify_code/deleteVerify_codeByVerify_code_id";
                    /*ajax.post(url,params,function(data){*/
                    $.ajax({
                        url: url,
                        data: {"verify_code_ids": verify_code_ids},
                        success: function (data) {
                            var result;
                            if ((typeof data) == 'String') {
                                result = JSON.parse(data);
                            } else if ((typeof data) == 'Object' || 'Array') {
                                result = eval(data);
                            }
                            if (result.success) {
                                if (checkAll) {
                                    $(".select-delete").parents("tr").remove();
                                    layer.msg("删除成功！", {time: 800, icon: 1});
                                } else if ($("input[type='checkbox']:checked").prop("checked")) {
                                    $("input[type='checkbox']:checked").parent().parent().remove();
                                    layer.msg("删除成功！", {time: 800, icon: 1});
                                }
                            }
                        }
                    });
                }, function () {

                })
            }
        });
    }
    //搜索功能实现
    $(".button-design-search").click(function(){
        $(".code-unused").css("display","none");
        $(".code-used").css("display","none");
        var search_code=$(".input-design").val();
        console.log(search_code);
        $.ajax({
            url:"/Cktv/verify_code/selectVerify_codeByCode/"+search_code,
            type:'post',
            success:function(data){
                console.log(data);
                var data = [data];
                console.log(data);
                var myTemplate = Handlebars.compile($("#code-list").html());
                $("#table-list").html(myTemplate(data));
            },
        });
    })

});
