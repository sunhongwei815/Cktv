package com.cktv.serviceManager;

import com.cktv.domain.User;

import java.util.List;
import java.util.Map;

/**
 * Created by mgh on 2016/5/29.
 */
public interface UserManager {
    User userLogin(User user);

    Map<String,Object> selectUsersByUser_status(long pageNow, long pageSize,long user_status);

    User selectuserByUser_id(long user_id);

    User deviceLogin(String username,String password);

    Map<String,Object> insertUser(User user);

    void deleteUserByUser_id(long user_id);

    void updateUser_statusByUser_id(long user_status,long user_id);

    void userLayout();
}
