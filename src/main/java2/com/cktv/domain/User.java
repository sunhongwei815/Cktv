package com.cktv.domain;

import com.cktv.mapper.TplMapper;
import com.trt.util.bean.BeanUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mgh on 2016/5/27.
 */
public class User {
    //用户类型，管理员（user_type=1）
    public final static long USER_TYPE_ADMIN = 1;

    public final static long USER_STATUS_USE = 1;

    public final static long USER_STATUS_STOP = 2;

    private long user_id;

    private long user_type;

    private String user_name;

    private String user_pwd;

    private String user_desc;

    private long part_id;

    private String security_key;

    private String add_date;

    private long user_status;

    private List<Tpl> tpluser_ids;

    private List<Device> devicedevice_dids;


    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getUser_type() {
        return user_type;
    }

    public void setUser_type(long user_type) {
        this.user_type = user_type;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_desc() {
        return user_desc;
    }

    public void setUser_desc(String user_desc) {
        this.user_desc = user_desc;
    }

    public long getPart_id() {
        return part_id;
    }

    public void setPart_id(long part_id) {
        this.part_id = part_id;
    }

    public String getSecurity_key() {
        return security_key;
    }

    public void setSecurity_key(String security_key) {
        this.security_key = security_key;
    }

    public static long getUserTypeAdmin() {
        return USER_TYPE_ADMIN;
    }

    public String getAdd_date() {
        return add_date;
    }

    public void setAdd_date(String add_date) {
        this.add_date = add_date;
    }

    public long getUser_status() {
        return user_status;
    }

    public void setUser_status(long user_status) {
        this.user_status = user_status;
    }

    public List<Tpl> getTpluser_ids() {
        return tpluser_ids;
    }

    public void setTpluser_ids(List<Tpl> tpluser_ids) {
        this.tpluser_ids = tpluser_ids;
    }

    public List<Device> getDevicedevice_dids() {
        return devicedevice_dids;
    }

    public void setDevicedevice_dids(List<Device> devicedevice_dids) {
        this.devicedevice_dids = devicedevice_dids;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (user_id != user.user_id) return false;
        if (user_type != user.user_type) return false;
        if (part_id != user.part_id) return false;
        if (user_status != user.user_status) return false;
        if (!user_name.equals(user.user_name)) return false;
        if (!user_pwd.equals(user.user_pwd)) return false;
        if (!user_desc.equals(user.user_desc)) return false;
        if (!security_key.equals(user.security_key)) return false;
        if (!add_date.equals(user.add_date)) return false;
        if (!tpluser_ids.equals(user.tpluser_ids)) return false;
        return devicedevice_dids.equals(user.devicedevice_dids);

    }

    @Override
    public int hashCode() {
        int result = (int) (user_id ^ (user_id >>> 32));
        result = 31 * result + (int) (user_type ^ (user_type >>> 32));
        result = 31 * result + user_name.hashCode();
        result = 31 * result + user_pwd.hashCode();
        result = 31 * result + user_desc.hashCode();
        result = 31 * result + (int) (part_id ^ (part_id >>> 32));
        result = 31 * result + security_key.hashCode();
        result = 31 * result + add_date.hashCode();
        result = 31 * result + (int) (user_status ^ (user_status >>> 32));
        result = 31 * result + tpluser_ids.hashCode();
        result = 31 * result + devicedevice_dids.hashCode();
        return result;
    }
}
