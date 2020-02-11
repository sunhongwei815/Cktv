package com.cktv.domain;

import com.cktv.mapper.UserMapper;
import com.cktv.mapper.Verify_codeMapper;
import com.trt.util.bean.BeanUtil;

import java.util.Date;

/**
 * Created by mgh on 2016/5/27.
 */
public class Device {
    //设备是否激活，设备激活（is_register=1）
    public final static long IS_REGISTER = 1;

    private String device_did;

    private String screen_name;

    private String resolution;

    private long status;

    private String screen_key;

    private long is_register;

    private Date verify_date;

    private long verify_code_id;

    private long user_id;

    private String app_name;

    private String app_version;

    private long run_status;

    private Verify_code deviceVerify_code;

    private User deviceUser;



    public String getDevice_did() {
        return device_did;
    }

    public void setDevice_did(String device_did) {
        this.device_did = device_did;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getScreen_key() {
        return screen_key;
    }

    public void setScreen_key(String screen_key) {
        this.screen_key = screen_key;
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

    public long getVerify_code_id() {
        return verify_code_id;
    }

    public void setVerify_code_id(long verify_code_id) {
        this.verify_code_id = verify_code_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public Verify_code getDeviceVerify_code() {
        return deviceVerify_code;
    }

    public void setDeviceVerify_code(Verify_code deviceVerify_code) {
        this.deviceVerify_code = deviceVerify_code;
    }

    public User getDeviceUser() {
        return deviceUser;
    }

    public void setDeviceUser(User deviceUser) {
        this.deviceUser = deviceUser;
    }

    public long getRun_status() {
        return run_status;
    }

    public void setRun_status(long run_status) {
        this.run_status = run_status;
    }

    public Verify_code loadDeviceVerify_code(){
        if(deviceVerify_code == null){
            if(verify_code_id == 0){
                deviceVerify_code = new Verify_code();
            }else{
                Verify_codeMapper verify_codeMapper = (Verify_codeMapper) BeanUtil.load("verify_codeMapper");
                deviceVerify_code = verify_codeMapper.selectByVerify_code_id(verify_code_id);
            }
        }
        return deviceVerify_code;
    }

    public User loadDeviceUser(){
        UserMapper userMapper = (UserMapper) BeanUtil.load("userMapper");
        if(user_id!=0) {
            deviceUser = userMapper.selectByUser_id(user_id);
        }
        return deviceUser;
    }
}
