package com.test.phone;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by linpeng123l on 2016/6/21.
 * lp
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml","classpath:spring-mybatis.xml","classpath:spring-mvc.xml"})
//当然 你可以声明一个事务管理 每个单元测试都进行事务回滚 无论成功与否
@TransactionConfiguration(defaultRollback = true)
//记得要在XML文件中声明事务哦~~~我是采用注解的方式
@Transactional
public class TestClientKickOff {

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

    private JSONObject client_kickoffParam(){
        JSONObject data = new JSONObject();
        data.put("req_id","11");
        data.put("method","client_kickoff");
        JSONObject params = new JSONObject();
        params.put("device_did","new_device");
        params.put("mod","dsplayer");
        data.put("params",params);
        return data;
    }

    @Test
    public void openSignIn() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/open/signin/15656002187/123456").param("data",client_kickoffParam().toString()))
                .andExpect(status().isOk()).andExpect(forwardedUrl("/phoneDevice/client_kickoff/15656002187/123456")).andReturn();
    }

    @Test
    public void client_kickoff() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("/phoneDevice/client_kickoff/15656002187/123456").param("data",client_kickoffParam().toString()))
                .andExpect(status().isOk()).andReturn();
        String str = mvcResult.getResponse().getContentAsString();
        JSONObject resJson = JSONObject.parseObject(str);
        Assert.assertEquals("ok",resJson.getString("rst"));
    }


}
