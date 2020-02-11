package com.test.user;

import com.alibaba.fastjson.JSONObject;
import com.cktv.domain.User;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by mgh on 2016/7/6.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring.xml","classpath:spring-mybatis.xml","classpath:spring-mvc.xml"})
//当然 你可以声明一个事务管理 每个单元测试都进行事务回滚 无论成功与否
@TransactionConfiguration(defaultRollback = true)
//记得要在XML文件中声明事务哦~~~我是采用注解的方式
@Transactional
public class TestUser {

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

    private JSONObject insertUserParam(){
        JSONObject data = new JSONObject();
        User user = new User();
        user.setUser_name("leopold");
        user.setUser_pwd("123456");
        data.put("user",user);
        return data;
    }

    @Test
    public void insertUser() throws Exception {
        System.out.println(insertUserParam().toString());
        MvcResult mvcResult = this.mockMvc.perform(get("/user/insertUser").param("data",insertUserParam().toString())).andReturn();
    }

}
