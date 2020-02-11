package com.cktv.domain;

/**
 * Created by hws on 2016/5/27.
 */
public class Device_sche_v {
    private long device_sche_v_id;
    private long sche_v;

    public long getDevice_sche_v_id() {
        return device_sche_v_id;
    }

    public void setDevice_sche_v_id(long device_sche_v_id) {
        this.device_sche_v_id = device_sche_v_id;
    }

    public long getSche_v() {
        return sche_v;
    }

    public void setSche_v(long sche_v) {
        this.sche_v = sche_v;
    }

    public String getDevice_did() {
        return device_did;
    }

    public void setDevice_did(String device_did) {
        this.device_did = device_did;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    private String device_did;
    private String update_time;


}
