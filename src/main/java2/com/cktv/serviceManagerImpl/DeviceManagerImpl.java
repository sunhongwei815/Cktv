package com.cktv.serviceManagerImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cktv.domain.Device;
import com.cktv.domain.Device_sche_v;
import com.cktv.domain.User;
import com.cktv.domain.Verify_code;
import com.cktv.mapper.DeviceMapper;
import com.cktv.mapper.Verify_codeMapper;
import com.cktv.serviceManager.DeviceManager;
import com.cktv.serviceManager.Device_sche_vManager;
import com.cktv.util.exception.MessageException;
import com.cktv.util.session.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by mgh on 2016/6/2.
 */

@Component
public class DeviceManagerImpl implements DeviceManager {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private Device_sche_vManager device_sche_vManager;

    @Autowired
    private Verify_codeMapper verify_codeMapper;


    @Override
    public List<Device> selectDevicesByUser_id(long user_id) {
        List<Device> devices = new ArrayList<Device>();
        devices = deviceMapper.selectDevicesByUser_id(user_id);
        return devices;
    }

    @Override
    public Map<String, Object> selectDevicesOfUser(long user_id, long userDevicePageNow, long userDevicePageSize) {
        long count = (userDevicePageNow - 1) * userDevicePageSize;
        List<Device> devices = deviceMapper.selectDevicesOfUser(user_id, count, userDevicePageSize);
        long lengthOfUserDevice = deviceMapper.selectLengthOfUserDevice(user_id);

        Map<String, Object> map = new HashMap<>();

        map.put("devices", devices);
        map.put("lengthOfUserDevice", lengthOfUserDevice);

        return map;
    }

    @Override
    public Map<String, Object> selectIs_registerDevicesOfUser(long user_id, long is_register, long userIs_registerDevicePageNow, long userIs_registerDevicePageSize) {
        long count = (userIs_registerDevicePageNow - 1) * userIs_registerDevicePageSize;
        Map<String, Object> map = new HashMap<>();

        if (is_register == 1) {
            List<Device> devices = deviceMapper.selectIs_registerDevicesOfUser(user_id, is_register, count, userIs_registerDevicePageSize);
            long lengthOfUserIs_registerDevice = deviceMapper.selectLengthOfUserIs_registerDevice(user_id, is_register);

            map.put("devices", devices);
            map.put("lengthOfUserIs_registerDevice", lengthOfUserIs_registerDevice);
        } else {
            List<Device> devices = deviceMapper.selectIs_registerDevicesOfUser(user_id, is_register, count, userIs_registerDevicePageSize);
            long lengthOfUserIs_registerDevice = deviceMapper.selectLengthOfUserIs_registerDevice(user_id, is_register);

            map.put("devices", devices);
            map.put("lengthOfUserIs_registerDevice", lengthOfUserIs_registerDevice);
        }

        return map;
    }

    @Override
    public Device selectByPrimaryKey(String device_did) {
        Device device = new Device();
        device = deviceMapper.selectByPrimaryKey(device_did);
        return device;
    }

    @Override
    public List<Device> selectAllDevices() {
        List<Device> devices = new ArrayList<Device>();
        devices = deviceMapper.selectAllDevices();
        for (int i = 0; i < devices.size(); i++) {
            devices.get(i).loadDeviceVerify_code();
        }
        return devices;
    }

    @Override
    public void insertDevice(Device device) {
        deviceMapper.insertDevice(device);
    }

    @Override
    public void updatestatusByDevice_did(List<String> device_dids, String status) {
        deviceMapper.updatestatusByDevice_did(device_dids, status);
    }

    @Override
    public long checkDeviceIs_register(String device_did) {
        Device device = deviceMapper.selectByPrimaryKey(device_did);
        if (device != null) {
            return device.getIs_register();
        } else {
            throw new MessageException("您的设备为空，请注册！");
        }
    }

    @Override
    public void registerDevice(String verify_code, String device_did) {
        Device device = deviceMapper.selectByPrimaryKey(device_did);
        if (device != null && device.getIs_register() == 1) {
            throw new MessageException("您的设备已经被激活，无须再激活！");
        }
        Verify_code verify_code1 = verify_codeMapper.selectByPrimaryKey(verify_code);
        if (verify_code1.getVerify_code() == null) {
            throw new MessageException("您的激活码输入有误，请正确输入！");
        } else if (verify_code1.getIs_register() == 1) {
            throw new MessageException("您输入的激活码已经被使用，请重新输入！");
        } else {
            //修改激活码的状态
            verify_codeMapper.updateVerify_codeIs_register(verify_code1.getVerify_code_id(), 1);
            if (device == null) {
                User user = SessionUtils.getCurrentUser();
                device = new Device();
                device.setUser_id(user.getUser_id());
                //修改设备状态
                device.setIs_register(1);
                device.setDevice_did(device_did);
                device.setVerify_code_id(verify_code1.getVerify_code_id());
                deviceMapper.insertDevice(device);
                //更新版本号为1
                Device_sche_v device_sche_v = new Device_sche_v();
                device_sche_v.setDevice_did(device_did);
                device_sche_v.setSche_v(1);
                device_sche_vManager.insertDeviceScheV(device_sche_v);
//                //更新public-device表
//                PublishDevice publishDevice=new PublishDevice();
//                publishDevice.setDevice_did(device_id);
//                publishDevice.setPublish_id(1);
//                publishDeviceMapper.insert(publishDevice);
            } else {
                device.setIs_register(1);
                device.setVerify_code_id(verify_code1.getVerify_code_id());
                deviceMapper.updateByPrimaryKey(device);
                //跟新版本号为1
                Device_sche_v device_sche_v = new Device_sche_v();
                device_sche_v.setDevice_did(device_did);
                device_sche_v.setSche_v(1);
                device_sche_vManager.insertDeviceScheV(device_sche_v);
//                //更新public-device表
//                PublishDevice publishDevice=new PublishDevice();
//                publishDevice.setDevice_did(device_id);
//                publishDevice.setPublish_id(1);
//                publishDeviceMapper.insert(publishDevice);
            }
        }
    }

    @Override
    public void registerDevice(String device_did) {
        Device device = deviceMapper.selectByPrimaryKey(device_did);
        if (device != null && device.getIs_register() == Device.IS_REGISTER) {
            throw new MessageException("您的设备已经被激活，无须再激活！");
        }
        if (device == null) {
            User user = SessionUtils.getCurrentUser();
            device = new Device();
            device.setUser_id(user.getUser_id());
            //修改设备状态
            device.setIs_register(Device.IS_REGISTER);
            device.setDevice_did(device_did);
            device.setVerify_code_id(0);
            deviceMapper.insertDevice(device);
            //更新版本号为1
            Device_sche_v device_sche_v = new Device_sche_v();
            device_sche_v.setDevice_did(device_did);
            device_sche_v.setSche_v(1);
            device_sche_vManager.insertDeviceScheV(device_sche_v);
        } else {
            device.setIs_register(Device.IS_REGISTER);
            device.setVerify_code_id(0);
            deviceMapper.updateByPrimaryKey(device);
            //跟新版本号为1
            Device_sche_v device_sche_v = new Device_sche_v();
            device_sche_v.setDevice_did(device_did);
            device_sche_v.setSche_v(1);
            device_sche_vManager.insertDeviceScheV(device_sche_v);
        }
    }

    @Override
    public boolean isRegister(String device_did) {
        Device device = deviceMapper.selectByPrimaryKey(device_did);
        if (device == null || device.getIs_register() != Device.IS_REGISTER) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void updateScreen_nameByDevice_did(String device_did, String screen_name) {
        deviceMapper.updateScreen_nameByDevice_did(device_did, screen_name);
    }

    @Override
    public void updateByPrimaryKey(Device device) {
        deviceMapper.updateByPrimaryKey(device);
    }

    @Override
    public void deleteDevicesByUserId(List<String> device_dids, long user_id) {
        deviceMapper.deleteDevice_didByUserId(device_dids, user_id);
        device_sche_vManager.deleteDevice_sche_vsByDevice_dids(device_dids);
    }

    @Override
    public Map<String, Object> selectDevicesByScreenName(String screen_name, long startpage, long size) {
        long count = (startpage - 1) * size;
        long sumPages = deviceMapper.lengthOfUserDeviceByScreenName(SessionUtils.getCurrentUser().getUser_id(), screen_name);
        List<Device> devices = deviceMapper.selectDevicesByScreenName(SessionUtils.getCurrentUser().getUser_id(),screen_name, count, size);
        Map<String, Object> map = new HashMap<>();
        map.put("devices", devices);
        map.put("sumPages", sumPages);
        return map;
    }

    @Override
    public Map<String, Object> selectDevicesByRunStatus(long run_status,long startpage,long size) {
        long count = (startpage - 1) * size;
        long sumPages = deviceMapper.lengthOfUserDeviceByRunStatus(SessionUtils.getCurrentUser().getUser_id(), run_status);
        List<Device> devices = deviceMapper.selectDevicesByRunStatus(SessionUtils.getCurrentUser().getUser_id(),run_status, count, size);
        Map<String, Object> map = new HashMap<>();
        map.put("devices", devices);
        map.put("sumPages", sumPages);
        return map;
    }

}
