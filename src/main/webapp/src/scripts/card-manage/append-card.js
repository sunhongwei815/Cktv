/**
 * Created by ssl on 16/4/21.
 */

$(function () {
    /*页面加载时的默认状态*/
    var publish_screen_mode = $("#publish_screen_mode").attr("publish_screen_mode");
    if(publish_screen_mode == 1){
        $("#tplModel input[value='1']").prop('checked',true);
        $("#tplModel input[value='2']").prop('disabled',true);
    }else if(publish_screen_mode == 2){
        $("#tplModel input[value='2']").prop('checked',true);
        $("#tplModel input[value='1']").prop('disabled',true);
    }
    displayTemplate(publish_screen_mode);
    /*定义一个加载模板的函数*/
    function displayTemplate(publish_screen_mode) {
        var url = "/Cktv/tpl/selectTplsByTpl_model/"+publish_screen_mode;
        var params = {
            'tplSource': $('input[name=source_radio]:checked').val(),
            'tplModel': $('input[name=screen_type_radio]:checked').val(),
            'posterType': $('input[name=poster_type_radio]:checked').val(),
            'tplBusiness': $('input[name=industry_radio]:checked').val(),
            'tplScene': $('input[name=scene_radio]:checked').val()
        };
        ajax.post(url, params, function (getData) {
            /*
            *getData[Object
            * duration: 0
            * dynamic_url: "/Cktv/src/upload/tpl/model4/animation/for-edit/index.html"
            * edit_address: null
            * edit_url: null
            * is_video: 2
            * thumb_url: "/Cktv/src/upload/tpl/model4/bg.jpg"
            * tpl_address: "/src/upload/tpl/model4"
            * tpl_business: 0
            * tpl_height: 0
            * tpl_id: 502
            * tpl_model: 2
            * tpl_name: "gyh"
            * tpl_size: "1920*1080"
            * tpl_width: 0
            * tpl_x_coordinate: 0
            * tpl_y_coordinate: 0
            * user_id: 33
            * video_source_url: null]
            * */
            var cardTemplate = Handlebars.compile($('#card_template').html());
            $('#my_card_template').html(cardTemplate(getData));
            addclick();
            clickConfirm();
        })
    }
    /*点击模板的选中效果*/
    function addclick() {
        $('.card').on('click', function () {
            var select = $(this).find('.select');
            var status = select.attr('status');
            if (status == 0) {
                select.removeClass('hide').attr('status', '1');
            } else if (status == 1) {
                select.addClass('hide').attr('status', '0');
            }
        });
    }
    /*点击确认后跳转页面*/
    function clickConfirm() {
        $('#content_confirm').on('click', function () {
            var url = "/Cktv/publish_tpl/insertPublish_tpls";
            //用户选中多个模板，放在数组里
            var tplList = new Array();
            var publish_id = $('#publish_id').attr('publish_id');
            $('.tplSelect[status = 1]').each(function () {
                var tpl_id = $(this).attr('tpl_id');
                tplList.push({
                    source_tpl_id:parseInt(tpl_id),
                    publish_id:parseInt(publish_id)
                });
            });
            ajax.post(url, tplList, function (getData) {
                if (getData.success) {
                    location.href = "/Cktv/publish/publishAddPage/" + publish_id;
                }
            })
        });
    }
});
