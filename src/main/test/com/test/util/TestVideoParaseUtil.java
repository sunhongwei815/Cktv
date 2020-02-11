package com.test.util;

import com.cktv.util.html.VideoParaseUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by hws on 16/7/15.
 */
public class TestVideoParaseUtil {


    @Test
    public void test(){
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<video src=\"a.mp4\" controls=\"controls\" height=\"100%\" width=\"100%\" autoplay=\"autoplay\"></video>\n" +
                "</body>\n" +
                "</html>";

        String src=  VideoParaseUtil.getFirstVideoSrcFromStr(html);
        Assert.assertEquals(src,"a.mp4");
    }

}
