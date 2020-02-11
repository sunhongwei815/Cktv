package com.cktv.serviceManagerImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cktv.inter.Msg;
import com.cktv.server.ConnectService;
import com.cktv.serviceManager.DeviceManager;
import com.cktv.store.MsgStore;
import com.cktv.util.exception.MessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cktv.serviceManager.MsgManager;

import java.util.Iterator;

/**
 * Created by 11835 on 2016/7/21.
 */
@Component
public class MsgManagerImpl implements MsgManager {
    @Autowired
    private DeviceManager deviceManager;

    @Override
    public void device_powerAndShutdown(String device_did, int status) {
        //int clientStatus=ConnectService.connectServiceHashMap.get(device_did).getStatus();
        //long clientStatus=deviceManager.selectByPrimaryKey(device_did).getRun_status();
        if(status==1){
                Msg msg=new Msg();
                msg.setDevice_did(device_did);
                msg.setControl_type("power");
                MsgStore.addMsg(msg);
        }else {
                Msg msg=new Msg();
                msg.setDevice_did(device_did);
                msg.setControl_type("shutdown");
                MsgStore.addMsg(msg);
        }
    }

    @Override
    public void devices_powerAndShutdown(JSONObject jsonObject) {
        int status = jsonObject.getInteger("status");
        JSONArray device_dids = jsonObject.getJSONArray("array");
        if(device_dids.isEmpty()){
            throw new MessageException("您选择的设备为空，请选择设备！");
        }
        if (status == 2) {
            for (Iterator iterator = device_dids.iterator(); iterator.hasNext(); ) {
                Msg msg = new Msg();
                msg.setDevice_did((String) iterator.next());
                msg.setControl_type("shutdown");
                MsgStore.addMsg(msg);
            }
        }else{
            for (Iterator iterator = device_dids.iterator(); iterator.hasNext(); ) {
                Msg msg = new Msg();
                msg.setDevice_did((String) iterator.next());
                msg.setControl_type("power");
                MsgStore.addMsg(msg);
            }
        }
    }
}
