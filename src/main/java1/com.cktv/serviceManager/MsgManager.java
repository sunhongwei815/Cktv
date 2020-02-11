package com.cktv.serviceManager;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by 11835 on 2016/7/21.
 */
public interface MsgManager {
    //开关机控制
    public void device_powerAndShutdown(String device_did,int status);
    public void devices_powerAndShutdown(JSONObject jsonObject);
}
