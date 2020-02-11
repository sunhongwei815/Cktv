/**
 * Created by ssl on 16/6/8.
 */
$(function () {
    var url = "/Cktv/phonePublish/get_ds_player_schedule/" + 1 + "/" + 2 + "/" +3
    var params = {
        auth_id:1,
        auth_token:123
    };
    ajax.post(url,params,function (data) {
        var getData;
        getData = JSON.parse(data);

    })
});