package com.cktv.serviceManagerImpl;

import com.cktv.domain.User;
import com.cktv.mapper.UserMapper;
import com.cktv.serviceManager.UserManager;
import com.cktv.util.date.DateUtils;
import com.cktv.util.exception.MessageException;
import com.cktv.util.session.SessionUtils;
import com.cktv.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mgh on 2016/5/29.
 */
@Component
public class UserManagerImpl implements UserManager{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User userLogin(User user1) {
        if(StringUtil.isEmpty(user1.getUser_name()) || StringUtil.isEmpty(user1.getUser_pwd())){
            throw new MessageException("请您正确填写您的用户名和密码！！！");
        } else{
            User user = userMapper.userLogin(user1.getUser_name(),user1.getUser_pwd());
            if(user == null){
                throw new MessageException("您输入的用户不存在，请重新输入用户名和密码！");
            } else if(!user1.getUser_pwd().equals(user.getUser_pwd())){
                throw new MessageException("您输入的密码不正确，请重新输入密码！");
            }
            SessionUtils.bindSession("user",user);
            return user;
        }
    }

    public User deviceLogin(String username,String password){
        return userMapper.userLogin(username,password);
    }

    @Override
    public Map<String,Object> insertUser(User user) {
        user.setAdd_date(DateUtils.getNowDate());
        Map<String,Object> map = new HashMap<>();
        if(user !=null){
            user.setUser_status(User.USER_STATUS_USE);
            if(userMapper.selectByUser_name(user.getUser_name())!=null){
                map.put("success",false);
                map.put("msg","用户已存在！");
            }else{
                map.put("success",true);
                userMapper.insertUser(user);
                map.put("msg","添加成功！");
            }
        }else{
            map.put("success",false);
            map.put("msg","用户数据错误！");
        }
        return map;
    }

    @Override
    public void deleteUserByUser_id(long user_id) {
        userMapper.deleteUserByUser_id(user_id);
    }

    @Override
    public void updateUser_statusByUser_id(long user_status, long user_id) {
        if(user_status == 1){
            userMapper.updateUser_statusByUser_id(User.USER_STATUS_STOP,user_id);
        }else{
            userMapper.updateUser_statusByUser_id(User.USER_STATUS_USE,user_id);
        }
    }

    @Override
    public void userLayout() {
        SessionUtils.logoutSession("user");
    }

    @Override
    public Map<String,Object> selectUsersByUser_status(long pageNow,long pageSize,long user_status) {
        long count = (pageNow-1)*pageSize;
        List<User> users = null;
        Map<String,Object> map = new HashMap<>();
        users = userMapper.selectUsersByUser_status(pageSize,count,user_status);
        Long length=userMapper.lengthOfUsersByUser_Status(user_status);
        map.put("users",users);
        map.put("lengthOfUser",length);
        return map;
    }


    @Override
    public User selectuserByUser_id(long user_id) {
        return userMapper.selectByUser_id(user_id);
    }
}
