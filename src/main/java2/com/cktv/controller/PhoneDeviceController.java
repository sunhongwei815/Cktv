package com.cktv.controller;

import com.cktv.domain.Device;
import com.cktv.domain.User;
import com.cktv.mapper.DeviceMapper;
import com.cktv.serviceManager.DeviceManager;
import com.cktv.serviceManager.UserManager;
import com.cktv.util.newscreen_key.NewScreen_key;
import com.cktv.util.session.SessionUtils;
import com.cktv.util.token.TokenUtils;
import com.cktv.util.tokenbuild.NewToken;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mgh on 2016/6/3.
 */
@Controller
@RequestMapping("/phoneDevice")
public class PhoneDeviceController extends BaseController {
    @Autowired
    private DeviceManager deviceManager;

    @Autowired
    private UserManager userManager;

    @RequestMapping(value = "/client_kickoff/{part_id}/{security_key}")
    @ResponseBody

    //第一次握手
    public Map<String, Object> client_kickoff(String data,@PathVariable("part_id") long part_id,@PathVariable("security_key") String security_key) {
        JSONObject jsonObject =JSONObject.fromObject(data);
        JSONObject paramsJsonObject = jsonObject.getJSONObject("params");
        User user = userManager.deviceLogin(part_id+"",security_key);

        String authTokenValue = NewToken.getRandomString(5);

        TokenUtils.setAuthToken(authTokenValue, user);
        TokenUtils.setDevice_didToken(authTokenValue, paramsJsonObject.getString("device_did"));


        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> dataAuth = new HashMap<String, Object>();

        dataAuth.put("auth_id", System.currentTimeMillis());
        dataAuth.put("auth_token", authTokenValue);

        map.put("data", dataAuth);
        map.put("rst", "ok");
        map.put("method","client_kickoff");
        map.put("req_id", jsonObject.getString("req_id"));

        return map;
    }

    //录入屏幕信息
    @RequestMapping(value = "/screen_info_upload")
    @ResponseBody
    public Map<String, Object> screen_info_upload(String data) {
        JSONObject jsonObject =JSONObject.fromObject(data);

        Device device = new Device();


        String screen_key = NewScreen_key.getRandomString(16);
        device.setScreen_key(screen_key);

        JSONObject paramsJsonObject = jsonObject.getJSONObject("params");

        device.setScreen_name(paramsJsonObject.getString("screen_name"));
        device.setDevice_did(paramsJsonObject.getString("device_did"));

        JSONObject app_infoJsonObject = paramsJsonObject.getJSONObject("app_info");
        device.setApp_version(app_infoJsonObject.getString("version"));
        device.setApp_name(app_infoJsonObject.getString("name"));

        JSONObject display_infoJsonObject = paramsJsonObject.getJSONObject("display_info");
        device.setStatus(display_infoJsonObject.getInt("status"));
        device.setResolution(display_infoJsonObject.getString("resolution"));

        device.setScreen_key(screen_key);
        device.setUser_id(TokenUtils.getUser(jsonObject.getJSONObject("auth").getString("auth_token")).getUser_id());


        //添加至数据库
        deviceManager.insertDevice(device);

        //返回的数据
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> dataScreen_key = new HashMap<String, Object>();

        dataScreen_key.put("screen_key", screen_key);

        map.put("req_id", jsonObject.getString("req_id"));
        map.put("data",dataScreen_key);

        map.put("data",dataScreen_key);
        map.put("rst","ok");
        map.put("method","screen_info_upload");

        return map;

    }

    //验证设备是否激活
    @RequestMapping(value = "/get_is_register")
    @ResponseBody
    public Map<String, Object> get_is_register(String data) {
        JSONObject jsonObject = JSONObject.fromObject(data);
        JSONObject paramsJsonObject = jsonObject.getJSONObject("params");

        JSONObject authJsonObject = jsonObject.getJSONObject("auth");
        String auth_token = authJsonObject.getString("auth_token");

        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> dataIs_register = new HashMap<String, Object>();

        dataIs_register.put("is_register",deviceManager.isRegister(paramsJsonObject.getString("device_did")));

        map.put("req_id", jsonObject.getString("req_id"));
        map.put("rst", "ok");
        map.put("data", dataIs_register);
        map.put("method","get_is_register");

        return map;
    }

    //注册屏幕
    @RequestMapping(value = "/screen_register")
    @ResponseBody
    public Map<String, Object> screen_register(String data) {
        JSONObject jsonObject = JSONObject.fromObject(data);
        JSONObject paramsJsonObject = jsonObject.getJSONObject("params");
        deviceManager.registerDevice(paramsJsonObject.getString("device_did"));

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("req_id", jsonObject.getString("req_id"));
        map.put("rst", "ok");
        map.put("method","screen_register");

        return map;

    }
}
