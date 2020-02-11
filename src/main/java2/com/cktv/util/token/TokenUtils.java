package com.cktv.util.token;

import com.cktv.domain.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mgh on 2016/6/14.
 */
public class TokenUtils {
    public static Map<String,String> device_didMap = new HashMap<>();
    public static Map<String,User> authMap = new HashMap<>();

    public static void setAuthToken(String authToken,User user){
        authMap.put(authToken,user);
    }

    public static User getUser(String authToken){
        return authMap.get(authToken);
    }

    public static void setDevice_didToken(String authToken,String device_did){
        device_didMap.put(authToken,device_did);
    }

    public static String getDevice_didToken(String authToken){
        return device_didMap.get(authToken);
    }

}
