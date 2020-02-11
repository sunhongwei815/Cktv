package com.cktv.controller;

import com.alibaba.fastjson.JSONObject;
import com.cktv.domain.Device;
import com.cktv.serviceManager.DeviceManager;
import com.cktv.util.session.SessionUtils;
import com.trt.util.user.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Created by mgh on 2016/6/2.
 */
@Controller
@RequestMapping("/device")
public class DeviceController extends BaseController{

    @Autowired
    private DeviceManager deviceManager;

    //添加设备
    @RequestMapping(value="/insertDevices")
    @ResponseBody
    public Map<String,Object> insertDevices(@RequestBody Device device){
        deviceManager.insertDevice(device);
        return generateSuccessMsg("添加成功！");
    }

    @RequestMapping(value="/controlDevice")
    @ResponseBody
    //控制设备
    public Map<String,Object> controlDevice(@RequestParam("device_dids[]") List<String> device_dids, @RequestParam String control_type){
        if("play".equals(control_type)){
            deviceManager.updatestatusByDevice_did(device_dids,"1");
        }else if("pause".equals(control_type)){
            deviceManager.updatestatusByDevice_did(device_dids,"0");
        }else if("close".equals(control_type)){
            deviceManager.updatestatusByDevice_did(device_dids,"0");
        }else if("break".equals(control_type)){
            deviceManager.updatestatusByDevice_did(device_dids,"1");
        }
        return generateSuccessMsg("修改状态成功！");
    }

    //所有设备
    @RequestMapping(value="/devicePage")
    public ModelAndView devicePage() {
        List<Device> devices = deviceManager.selectAllDevices();
        ModelAndView modelAndView = new ModelAndView("card-manage/append-card1.0");
        modelAndView.addObject("devices", devices);
        return modelAndView;
    }
    @RequestMapping(value="/selectAllDevices")
    @ResponseBody
    public List<Device> selectAllDevices() {
       return deviceManager.selectAllDevices();
    }

    //用户设备
    @RequestMapping(value="/selectUserDevices")
    @ResponseBody
    public List<Device> selectUserDevices(){
        return deviceManager.selectDevicesByUser_id(SessionUtils.getCurrentUser().getUser_id());
    }

    //用户设备的管理分页展示
    @RequestMapping(value="/selectCurrentUserDevices/{userDevicePageNow}/{userDevicePageSize}")
    @ResponseBody
    public Map<String,Object> selectCurrentUserDevices(@PathVariable("userDevicePageNow") long userDevicePageNow,@PathVariable("userDevicePageSize") long userDevicePageSize){
        return deviceManager.selectDevicesOfUser(SessionUtils.getCurrentUser().getUser_id(),userDevicePageNow,userDevicePageSize);
    }

    //用户设备分页显示
    @RequestMapping(value="/selectDevicesOfUser/{user_id}/{userDevicePageNow}/{userDevicePageSize}")
    @ResponseBody
    public Map<String,Object> selectDevicesOfUser(@PathVariable long user_id,@PathVariable long userDevicePageNow,@PathVariable long userDevicePageSize){
        return deviceManager.selectDevicesOfUser(user_id,userDevicePageNow,userDevicePageSize);
    }

    //用户设备是否激活查询展示
    @RequestMapping(value="/selectIs_registerDevicesOfUser/{user_id}/{is_register}/{userIs_registerDevicePageNow}/{userIs_registerDevicePageSize}")
    @ResponseBody
    public Map<String,Object> selectIs_registerDevicesOfUser(@PathVariable long user_id,@PathVariable long is_register,@PathVariable long userIs_registerDevicePageNow,@PathVariable long userIs_registerDevicePageSize){
        return deviceManager.selectIs_registerDevicesOfUser(user_id,is_register,userIs_registerDevicePageNow,userIs_registerDevicePageSize);
    }

    //检测设备状态
    @RequestMapping(value="/checkDeviceStatus/{device_did}")
    @ResponseBody
    public Map<String,Object> checkDeviceStatus(@PathVariable("device_did") String device_did){
        long status = deviceManager.checkDeviceIs_register(device_did);
        if(status == 1){
            return generateSuccessMsg("设备已激活！");
        }else{
            return generateFailureMsg("设备未激活！");
        }
    }

    //注册设备
    @RequestMapping(value = "/registerDevice/{verify_code}/{device_did}")
    @ResponseBody
    public Map<String,Object>registerDevice(@PathVariable("verify_code") String verify_code, @PathVariable("device_did") String device_did){
        deviceManager.registerDevice(verify_code,device_did);
        return generateSuccessMsg("可以注册！");
    }

    //查询所有设备、设备码和用户
    @RequestMapping(value="/deviceVerify_codeUser")
    @ResponseBody
    public List<Device> deviceVerify_codeUser(){
        List<Device> devices = deviceManager.selectAllDevices();
        for(int i = 0;i<devices.size();i++){
            devices.get(i).loadDeviceUser();
            devices.get(i).loadDeviceVerify_code();
        }
        return devices;
    }

    //修改设备名称
    @RequestMapping(value="/updateScreen_nameByDevice_did/{device_did}/{screen_name}")
    @ResponseBody
    public Map<String,Object> updateScreen_nameByDevice_did(@PathVariable("device_did") String device_did,@PathVariable("screen_name") String screen_name){
        deviceManager.updateScreen_nameByDevice_did(device_did,screen_name);
        return generateSuccessMsg("修改成功！");
    }
    //设备控制接口
    @RequestMapping(value = "/deviceDetails/{device_did}")
    @ResponseBody
    public ModelAndView deviceDetails(@PathVariable("device_did") String device_did){
        Device device=deviceManager.selectByPrimaryKey(device_did);
        ModelAndView modelAndView=new ModelAndView("/device-manage/screen-detail1.0");
        modelAndView.addObject(device);
        return modelAndView;
    }

    //删除设备
    @RequestMapping(value = "/deviceDevicesByUserId")
    @ResponseBody

    public Map<String,Object>deviceDevicesByUserId(@RequestBody List<String> device_dids){
        deviceManager.deleteDevicesByUserId(device_dids, SessionUtils.getCurrentUser().getUser_id());
        return generateSuccessMsg("修改成功！");
    }

    //根据屏幕名称查询
    @RequestMapping(value = "/selectDevicesByScreenName/{screen_name}/{startpage}/{size}")
    @ResponseBody
    public Map<String,Object>selectDevicesByScreenName(@PathVariable("screen_name")String screen_name,@PathVariable("startpage")long startpage,@PathVariable("size")long size ){
        return deviceManager.selectDevicesByScreenName(screen_name, startpage, size);
    }

   //根据运行状态去查询用户设备
    @RequestMapping(value = "/selectDevicesByRunStatus/{run_status}/{startpage}/{size}")
    @ResponseBody
    public Map<String,Object> selectDevicesByRunStatus(@PathVariable("run_status") long run_status,@PathVariable("startpage")long startpage,@PathVariable("size") long size){
        return deviceManager.selectDevicesByRunStatus(run_status,startpage,size);
    }
}
