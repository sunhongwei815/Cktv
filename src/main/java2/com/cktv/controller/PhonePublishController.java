package com.cktv.controller;

import com.cktv.domain.Publish;
import com.cktv.domain.Publish_tpl;
import com.cktv.serviceManager.Device_sche_vManager;
import com.cktv.serviceManager.PublishManager;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hws on 2016/5/27.
 */
@Controller
@RequestMapping("/phonePublish")
public class PhonePublishController {
    @Autowired
    private PublishManager publishManager;
    @Autowired
    private Device_sche_vManager device_sche_vManager;



    @RequestMapping("/get_ds_player_schedule")
    @ResponseBody
    public Map<String,Object> get_ds_player_schedule(String data){
        JSONObject jsonObject= JSONObject.fromObject(data);
        JSONObject params=jsonObject.getJSONObject("params");/*从json对象中取出json对象 params*/
        String device_did=params.getString("device_did");/*从params中取出device_did*/
        long req_id=jsonObject.getInt("req_id");
        List<Publish> publishs= publishManager.selectOnPlayingPublishsByDevice_did(device_did);/*将查出的发布转化为模板并封装到json对象中*/
        List<Publish_tpl> publish_tpls=new ArrayList<>();
        for(int i=0;i<publishs.size();i++){
            Publish publish=publishs.get(i);
            for(int x=0;x<publish.getPublish_tpls().size();x++){
                publish_tpls.add(publish.getPublish_tpls().get(x));
            }
        }
        Map<String,Object> tplmap=new HashMap<>();
        tplmap.put("tpls",publish_tpls);
        long sche_v=device_sche_vManager.selectSche_vByDevice_did(device_did);
        tplmap.put("sche_v",sche_v);
        tplmap.put("tpl_count",publish_tpls.size());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("req_id",req_id);
        map.put("data",tplmap);
        map.put("rst","ok");
        map.put("method","get_ds_player_schedule");
        return map;
    }


    @RequestMapping("/get_player_schedule_version")
    @ResponseBody
        public Map<String,Object> get_player_schedule_version(String data){
        JSONObject jsonObject= JSONObject.fromObject(data);
        JSONObject params=jsonObject.getJSONObject("params");
        String device_did=params.getString("device_did");
        long req_id=jsonObject.getInt("req_id");
        long sche_v= device_sche_vManager.selectSche_vByDevice_did(device_did);
        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("sche_v",sche_v);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("req_id",req_id);
        map.put("data",jsonObject1);
        map.put("rst","ok");
        map.put("method","get_player_schedule_version");
        return map;
    }
}
