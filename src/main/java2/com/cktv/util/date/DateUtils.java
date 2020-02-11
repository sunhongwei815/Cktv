package com.cktv.util.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mgh on 2016/6/3.
 */
public class DateUtils {
    public static String getNowDate(){
        String temp_str = "";
        Date dt = new Date();
        //最后的aa表示"上午"或"下午"  HH表示24小时制   hh为12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        temp_str = sdf.format(dt);
        return temp_str;
    }
}
