package com.cktv.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cktv.serviceManager.MsgManager;

import java.util.Map;

/**
 * Created by 11835 on 2016/7/21.
 */
@Controller
@RequestMapping(value = "/Msg")
@ResponseBody
public class MsgController extends BaseController {
    @Autowired
    private MsgManager msgManager;

    //对于一台设备控制接口
    @RequestMapping(value = "/device_powerAndShutdown/{device_did}/{status}")
    @ResponseBody
    public Map<String,Object> device_powerAndShutdown(@PathVariable("device_did") String device_did,@PathVariable("status") int status){
        msgManager.device_powerAndShutdown(device_did,status);
        return generateSuccessMsg("控制成功！");
    }
    //多台设备控制
    @RequestMapping(value="devices_powerAndShutdown")
    @ResponseBody
    public Map<String,Object>devices_powerAndShutdown(@RequestBody JSONObject jsonObject){
        msgManager.devices_powerAndShutdown(jsonObject);
        return generateSuccessMsg("控制成功！") ;
    }
}
