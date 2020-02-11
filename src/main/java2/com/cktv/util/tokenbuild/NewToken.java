package com.cktv.util.tokenbuild;

import java.util.Random;

/**
 * Created by mgh on 2016/6/8.
 */
public class NewToken {
    public static String getRandomString(long length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(10);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
