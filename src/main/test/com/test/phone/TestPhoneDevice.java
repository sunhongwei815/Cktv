package com.test.phone;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.*;
import java.net.Socket;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by mgh on 2016/6/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml","classpath:spring-mybatis.xml","classpath:spring-mvc.xml"})
//当然 你可以声明一个事务管理 每个单元测试都进行事务回滚 无论成功与否
@TransactionConfiguration(defaultRollback = true)
//记得要在XML文件中声明事务哦~~~我是采用注解的方式
@Transactional
public class TestPhoneDevice {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    @Before
    public void setup() {
        // webAppContextSetup 注意上面的static import
        // webAppContextSetup 构造的WEB容器可以添加fileter 但是不能添加listenCLASS
        // WebApplicationContext context =
        // ContextLoader.getCurrentWebApplicationContext();
        // 如果控制器包含如上方法 则会报空指针
        this.mockMvc = webAppContextSetup(this.wac).build();
    }

    private JSONObject get_is_registerParam(){
        JSONObject data = new JSONObject();
        data.put("req_id","11");
        data.put("method","get_is_register");

        JSONObject params = new JSONObject();
        params.put("device_did","new_device");

        JSONObject auth = new JSONObject();
        auth.put("auth_id","new_auth_id");
        auth.put("auth_token","new_auth_token");

        data.put("params",params);
        data.put("auth",auth);
        return data;
    }

    @org.junit.Test
    public void get_is_register () throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("/phoneDevice/get_is_register").param("data",get_is_registerParam().toString()))
                .andExpect(status().isOk()).andReturn();
        String str = mvcResult.getResponse().getContentAsString();
        JSONObject resJson = JSONObject.parseObject(str);
        Assert.assertEquals("ok",resJson.getString("rst"));
    }

    private JSONObject screen_info_uploadParam(){
        JSONObject data = new JSONObject();
        data.put("req_id","11");
        data.put("method","screen_info_upload");

        JSONObject params = new JSONObject();
        params.put("device_did","new_device");
        params.put("screen_name","new_screen_name");

        JSONObject app_info = new JSONObject();
        app_info.put("version","new_version");
        app_info.put("name","new_name");
        params.put("app_info",app_info);

        JSONObject display_info = new JSONObject();
        display_info.put("resolution","new_resolution");
        display_info.put("status","new_status");

        params.put("display_info",display_info);

        JSONObject auth = new JSONObject();
        auth.put("auth_id","new_auth_id");
        auth.put("auth_token","new_auth_token");

        data.put("params",params);
        data.put("auth",auth);
        return data;
    }

    @org.junit.Test
    public void screen_info_upload() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("/phoneDevice/screen_info_upload").param("data",screen_info_uploadParam().toString()))
                .andExpect(status().isOk()).andReturn();
        String str = mvcResult.getResponse().getContentAsString();
        JSONObject resJson = JSONObject.parseObject(str);
        Assert.assertEquals("ok",resJson.getString("rst"));
    }

    private JSONObject screen_registerParam(){
        JSONObject data = new JSONObject();
        data.put("req_id","11");
        data.put("method","screen_info_upload");

        JSONObject params = new JSONObject();
        params.put("device_did","new_device");
        params.put("screen_key","new_screen_name");
        params.put("verify_code","verify_code");

        JSONObject auth = new JSONObject();
        auth.put("auth_id","new_auth_id");
        auth.put("auth_token","new_auth_token");

        data.put("params",params);
        data.put("auth",auth);
        return data;
    }

    @org.junit.Test
    public void screen_register() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("/phoneDevice/screen_register").param("data",screen_registerParam().toString()))
                .andExpect(status().isOk()).andReturn();
        String str = mvcResult.getResponse().getContentAsString();
        JSONObject resJson = JSONObject.parseObject(str);
        Assert.assertEquals("ok",resJson.getString("rst"));
    }



}
