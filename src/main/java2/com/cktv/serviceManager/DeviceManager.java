package com.cktv.serviceManager;

import com.alibaba.fastjson.JSONObject;
import com.cktv.domain.Device;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mgh on 2016/6/2.
 */
public interface DeviceManager {
    List<Device> selectDevicesByUser_id(long user_id);

    Map<String,Object> selectDevicesOfUser(long user_id,long userDevicePageNow,long userDevicePageSize);

    Map<String, Object> selectIs_registerDevicesOfUser(long user_id,long is_register,long userIs_registerDevicePageNow,long userIs_registerDevicePageSize);

    Device selectByPrimaryKey(String device_did);

    List<Device> selectAllDevices();

    void insertDevice(Device device);

    void updatestatusByDevice_did( List<String> device_dids,String status);

    long checkDeviceIs_register(String device_did);

    void registerDevice(String verify_code,String device_did);

    void registerDevice(String device_did);

    boolean isRegister(String device_did);

    void updateScreen_nameByDevice_did(String device_did,String screen_name);

    void updateByPrimaryKey(Device device);

    void deleteDevicesByUserId(List<String> device_dids, long user_id);

    Map<String,Object> selectDevicesByScreenName(String screen_name,long startpage,long size);

    Map<String,Object> selectDevicesByRunStatus(long run_status,long startpage,long size);

}
