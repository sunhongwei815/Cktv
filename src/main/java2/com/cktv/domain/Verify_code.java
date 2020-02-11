package com.cktv.domain;

import com.cktv.mapper.DeviceMapper;
import com.cktv.util.string.StringUtil;
import com.trt.util.bean.BeanUtil;

import java.util.Date;

/**
 * Created by mgh on 2016/5/28.
 */
public class Verify_code {
    //设备码是否使用 已使用(is_register = 1)
    public final static long IS_REGISRER = 1;

    private long verify_code_id;

    private String verify_code;

    private long is_register;

    private Date verify_date;

    private long verify_time;

    private String device_did;

    private Device verify_codeDevice;

    public long getVerify_code_id() {
        return verify_code_id;
    }

    public void setVerify_code_id(long verify_code_id) {
        this.verify_code_id = verify_code_id;
    }

    public String getVerify_code() {
        return verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code;
    }

    public long getIs_register() {
        return is_register;
    }

    public void setIs_register(long is_register) {
        this.is_register = is_register;
    }

    public Date getVerify_date() {
        return verify_date;
    }

    public void setVerify_date(Date verify_date) {
        this.verify_date = verify_date;
    }

    public long getVerify_time() {
        return verify_time;
    }

    public void setVerify_time(long verify_time) {
        this.verify_time = verify_time;
    }

    public Device getVerify_codeDevice() {
        return verify_codeDevice;
    }

    public void setVerify_codeDevice(Device verify_codeDevice) {
        this.verify_codeDevice = verify_codeDevice;
    }

    public Device loadVerifyDevice(){
        DeviceMapper deviceMapper = (DeviceMapper) BeanUtil.load("deviceMapper");
        if(!StringUtil.isEmpty(device_did)) {
            verify_codeDevice = deviceMapper.selectByPrimaryKey(device_did);
        }
        return verify_codeDevice;
    }
}
