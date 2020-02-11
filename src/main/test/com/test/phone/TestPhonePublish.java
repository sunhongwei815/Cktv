package com.test.phone;

/**
 * Created by hws on 16/6/22.
 */

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml","classpath:spring-mybatis.xml","classpath:spring-mvc.xml"})
//当然 你可以声明一个事务管理 每个单元测试都进行事务回滚 无论成功与否
@TransactionConfiguration(defaultRollback = true)
//记得要在XML文件中声明事务哦~~~我是采用注解的方式
@Transactional
public class TestPhonePublish{

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

    private JSONObject get_ds_player_scheduleParam(){
        JSONObject data = new JSONObject();
        data.put("req_id","11");
        data.put("method","get_ds_player_schedule");
        JSONObject params = new JSONObject();
        params.put("device_did","CKDZ_484561f0918e96b7");
        data.put("params",params);
        return data;
    }

    @org.junit.Test
    public void apiv2() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/open/api/v2/1").param("data",get_ds_player_scheduleParam().toString()))
                .andExpect(status().isOk()).andExpect(forwardedUrl("/phonePublish/get_ds_player_schedule")).andReturn();
    }

    @org.junit.Test
    public void get_ds_player_schedule() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("/phonePublish/get_ds_player_schedule").param("data",get_ds_player_scheduleParam().toString()))
                .andExpect(status().isOk()).andReturn();
        String str = mvcResult.getResponse().getContentAsString();
        JSONObject resJson = JSONObject.parseObject(str);
        Assert.assertEquals("ok",resJson.getString("rst"));
    }


}

