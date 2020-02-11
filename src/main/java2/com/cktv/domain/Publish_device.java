package com.cktv.domain;

import com.cktv.mapper.DeviceMapper;
import com.trt.util.bean.BeanUtil;
import com.trt.util.string.StringUtil;

/**
 * Created by hws on 2016/5/28.
 */
public class Publish_device {
    private long publish_device_id;

    private long publish_id;

    private String device_did;

    private Device devicedevice_did;

    public Device getDevicedevice_did() {
        return devicedevice_did;
    }

    public void setDevicedevice_did(Device devicedevice_did) {
        this.devicedevice_did = devicedevice_did;
    }

    public long getPublish_device_id() {
        return publish_device_id;
    }

    public void setPublish_device_id(long publish_device_id) {
        this.publish_device_id = publish_device_id;
    }

    public long getPublish_id() {
        return publish_id;
    }

    public void setPublish_id(long publish_id) {
        this.publish_id = publish_id;
    }

    public String getDevice_did() {
        return device_did;
    }

    public void setDevice_did(String device_did) {
        this.device_did = device_did;
    }



    public Device loadDevicedevice_did(){

        if(devicedevice_did==null) {
            if (device_did==null) {
                devicedevice_did = new Device();
            }else{
                DeviceMapper deviceMapper=(DeviceMapper) BeanUtil.load("deviceMapper");
                devicedevice_did= deviceMapper.selectByPrimaryKey(device_did);
            }
        }
        return devicedevice_did;
    }


}
