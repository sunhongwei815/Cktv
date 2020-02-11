/**
 * Created by gyh on 2016/8/18.
 */
$(document).ready(function(){

    $('#mode-name').blur(function(){
        var mode_name = $.trim($(this).val());
        if(mode_name === ''){
            layer.tips('请输入模板名称','#mode-name',{
                tips: [2,'#000']
            });
            $(this).focus();
        }else{

        }
    });
    
    $('#ppt').change(function(){
        var file_ppt = $(this).val();
        var file_ppt_arr = file_ppt.split('.');
        var postfix = file_ppt_arr[file_ppt_arr.length-1];
        if(file_ppt === ''){
            layer.tips('请选择ppt或pptx文件','#ppt',{
                tips: [2,'#000']
            });
        }
        if((postfix !== 'ppt') && (postfix !== 'pptx')){
            layer.tips('请选择ppt或pptx类型文件','#ppt',{
                tips: [2,'#000']
            });
        }
    });
    
    $('#confirm').mousedown(function(){
        var mode_name = $('#mode-name').val();
        var file_img = $('#ppt').val();
        var file_img_arr = file_img.split('.');
        var postfix = file_img_arr[file_img_arr.length-1];
        if((postfix !== 'ppt') && (postfix !== 'pptx')){
            layer.tips('请选择ppt或pptx类型文件','#ppt',{
                tips: [2,'#000']
            });
        }else if(mode_name === ''){
            layer.tips('请输入模板名称','#mode-name',{
                tips: [2,'#000']
            });
            $(this).focus();
        }else if(file_img === ''){
            layer.tips('请选择ppt文件','#ppt',{
                tips: [2,'#000']
            });
        }else{
            layer.msg('正在上传...');
            var url = '/Cktv/tpl/upLoadPPT';
            var paramData = new FormData($('#ppt-form')[0]);
            $.ajax({
                url: url,
                data: paramData,
                type: 'post',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function(getData){
                    if(getData.success){
                        location.href = '/Cktv/pages/display-manage/publish-list.html';
                    }else{
                        layer.msg(Data.msg);
                    }
                },
                error: function(){
                    layer.msg('出错！');
                }
            })
        }
    });
});
function checkppt() {
    var mode_name = $('#mode-name').val();
    var file_img = $('#ppt').val();
    if(mode_name == ''){
        layer.tips('请输入模板名称','#mode-name',{
            tips: [2,'#000']
        });
        $(this).focus();
        return false;
    }else if(file_img == ''){
        layer.tips('请选择ppt文件','#ppt',{
            tips: [2,'#000']
        });
        return false;
    }else{
        return true;
    }
}
