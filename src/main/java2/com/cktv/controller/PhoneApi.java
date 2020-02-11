package com.cktv.controller;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by hws on 2016/6/8.
 */
@Controller
@RequestMapping("/open")
public class PhoneApi {


//    两个接口,一个处理client_kickoff,另一个处理其他请求
    @RequestMapping("/api/v2/{part_id}")
    public ModelAndView v2(@PathVariable long part_id ,String data){
//    用String类型接收参数
        JSONObject jsonObject= JSONObject.fromObject(data);
//        转化为json对象
        String method=jsonObject.getString("method");
//        匹配方法名
        ModelAndView modelAndView=new ModelAndView();
        if("get_ds_player_schedule".equals(method)){
            modelAndView.setViewName("forward:/phonePublish/get_ds_player_schedule");
        }
        else if("get_player_schedule_version".equals(method)){
            modelAndView.setViewName("forward:/phonePublish/get_player_schedule_version");
        }
        else if("get_is_register".equals(method)){
            modelAndView.setViewName("forward:/phoneDevice/get_is_register");
        }
        else if("screen_info_upload".equals(method)){
            modelAndView.setViewName("forward:/phoneDevice/screen_info_upload");
        }
        else if("screen_register".equals(method)){
            modelAndView.setViewName("forward:/phoneDevice/screen_register");
        }
        return modelAndView;
    }

    @RequestMapping("/signin/{part_id}/{security_key}")
    public ModelAndView signin(@PathVariable long part_id,@PathVariable
    String security_key,String data){
        JSONObject data2= JSONObject.fromObject(data);
        String method=data2.getString("method");
        ModelAndView modelAndView=new ModelAndView();
        if("client_kickoff".equals(method)){
            modelAndView.setViewName("forward:/phoneDevice/client_kickoff/"+part_id+"/"+security_key);
        }
        return modelAndView;
    }
}