package com.cktv.util.newscreen_key;

import java.util.Random;

/**
 * Created by mgh on 2016/6/6.
 */
public class NewScreen_key {
    public static String getRandomString(long length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
