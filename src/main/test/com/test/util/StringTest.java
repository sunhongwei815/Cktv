package com.test.util;


import com.cktv.util.string.StringUtil;
import org.junit.Test;

/**
 * Created by 11835 on 2016/8/3.
 */
public class StringTest {
    @Test
    public void test(){
        String adress="/cktv/src/upload/a.mp4";
        System.out.println(StringUtil.removeStringHeaderWithCktv(adress));
    }
    @Test
    public void test1(){
        String adress="/cktv/src/upload/a.mp4";
        System.out.println(StringUtil.lastSubStringBySplit(adress));
    }
}
