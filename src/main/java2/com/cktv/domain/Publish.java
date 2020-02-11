package com.cktv.domain;

import com.cktv.mapper.Publish_deviceMapper;
import com.cktv.mapper.Publish_tplMapper;
import com.cktv.serviceManager.Publish_tplManager;
import com.trt.util.bean.BeanUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hws on 2016/5/27.
 */
public class Publish {


    private long publish_id;
    private Date publish_time;

    private Date update_time;
    private String start_time;

    private List<Publish_tpl> publish_tpls;
    private List<Publish_device> publish_devices;

    private long sceh_v;
    private String end_time;
    private long user_id;
    private long duration;
    private long tpl_count;
    private long publish_status;
    private String publish_name;
    //发布屏幕模式  1表示横屏，2表示竖屏
    private int publish_screen_mode;
    //发布适配设备  1表示单屏，2表示多屏
    private int publish_adapter_device;

    public int getPublish_screen_mode() {
        return publish_screen_mode;
    }

    public void setPublish_screen_mode(int publish_screen_mode) {
        this.publish_screen_mode = publish_screen_mode;
    }

    public int getPublish_adapter_device() {
        return publish_adapter_device;
    }

    public void setPublish_adapter_device(int publish_adapter_device) {
        this.publish_adapter_device = publish_adapter_device;
    }

    public long getSceh_v() {
        return sceh_v;
    }

    public void setSceh_v(long sceh_v) {
        this.sceh_v = sceh_v;
    }

    public List<Publish_device> getPublish_devices() {
        return publish_devices;
    }

    public void setPublish_devices(List<Publish_device> publish_devices) {
        this.publish_devices = publish_devices;
    }

    public List<Publish_tpl> getPublish_tpls() {
        return publish_tpls;
    }

    public void setPublish_tpls(List<Publish_tpl> publish_tpls) {
        this.publish_tpls = publish_tpls;
    }






    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public long getPublish_id() {
        return publish_id;
    }

    public void setPublish_id(long publish_id) {
        this.publish_id = publish_id;
    }

    public Date getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(Date publish_time) {
        this.publish_time = publish_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getTpl_count() {
        return tpl_count;
    }

    public void setTpl_count(long tpl_count) {
        this.tpl_count = tpl_count;
    }

    public long getPublish_status() {
        return publish_status;
    }

    public void setPublish_status(long publish_status) {
        this.publish_status = publish_status;
    }

    public String getPublish_name() {
        return publish_name;
    }

    public void setPublish_name(String publish_name) {
        this.publish_name = publish_name;
    }

    public List<Publish_tpl> loadPublish_tpls(){
        if(publish_tpls==null){
            if (publish_id==0){
                publish_tpls=new ArrayList<>();
            }else {
                Publish_tplMapper publish_tplMapper=(Publish_tplMapper) BeanUtil.load("publish_tplMapper");
                publish_tpls=publish_tplMapper.selectPublish_tplsByPublish_id(publish_id);
            }
        }
        return publish_tpls;
    }
    public List<Publish_device> loadPublish_devices(){
        if(publish_devices==null){
            if (publish_id==0){
                publish_devices=new ArrayList<>();
            }else {
                Publish_deviceMapper publish_deviceMapper=(Publish_deviceMapper) BeanUtil.load("publish_deviceMapper");
                publish_devices=publish_deviceMapper.selectPublish_devicesByPublish_id(publish_id);
            }
        }
        return publish_devices;
    }




}
