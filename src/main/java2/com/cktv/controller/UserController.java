package com.cktv.controller;

import com.cktv.config.Config;
import com.cktv.domain.User;
import com.cktv.serviceManager.UserManager;
import com.cktv.util.session.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mgh on 2016/5/29.
 */

@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController{

    @Autowired
    private UserManager userManager;

    @RequestMapping(value="/insertUser")
    @ResponseBody
    public Map<String,Object>insertUser(@RequestBody User user){
        return userManager.insertUser(user);
    }

    @RequestMapping(value="/userLogin",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> userLogin(@RequestBody User user1){
        User user = userManager.userLogin(user1);
        Map<String,Object> map = new HashMap<>();
        //判断是否从原先界面跳转过来
       // Object url= SessionUtils.getCurrentObject("redirectUrl");
        if (user.getUser_type() == User.USER_TYPE_ADMIN) {
           // if (url == null) {
            map.put("redirect_url", Config.REDIRECT_URL_MANAGE);
           // } else {
             //   map.put("redirect_url", url);
           //}
        } else {
           // if (url == null) {
            map.put("redirect_url", Config.REDIRECT_URL_USER);
           // } else {
            //    map.put("redirect_url", url);
           // }
        }
        map.put("user",user);
        map.put("success",true);
        map.put("msg","登录成功！");
        return map;
    }

    //展示user_name的接口
    @RequestMapping(value="/showUsername",method = RequestMethod.POST)
    @ResponseBody
    public User showUsername(){
        return SessionUtils.getCurrentUser();
    }

    //显示分页用户
    @RequestMapping(value="/selectUsersByUser_status/{pageNow}/{pageSize}/{user_status}")
    @ResponseBody
    public Map<String,Object> selectUsersByUser_status(@PathVariable("pageNow") long pageNow,@PathVariable("pageSize") long pagesize,@PathVariable("user_status") long user_status){
        return userManager.selectUsersByUser_status(pageNow,pagesize,user_status);
    }

    //停用与启用用户
    @RequestMapping(value="/updateUser_statusByUser_id/{user_status}/{user_id}")
    @ResponseBody
    public Map<String,Object> updateUser_statusByUser_id(@PathVariable("user_status") long user_status,@PathVariable("user_id") long user_id){
        userManager.updateUser_statusByUser_id(user_status,user_id);
        return generateSuccessMsg("更新成功！！");
    }

    //删除用户
    @RequestMapping(value="/deleteUserByUser_id/{user_id}")
    @ResponseBody
    public Map<String,Object> deleteUserByUser_id(@PathVariable("user_id") long user_id){
        userManager.deleteUserByUser_id(user_id);
        return generateSuccessMsg("删除成功！");
    }
    //用户注销
    @RequestMapping(value = "/userLayout")
    @ResponseBody
    public ModelAndView userLayout(){
        userManager.userLayout();
        ModelAndView modelAndView=new ModelAndView("/user-login/login");
        return modelAndView;
    }





}
