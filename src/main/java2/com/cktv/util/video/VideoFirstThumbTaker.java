package com.cktv.util.video;

/**
 * Created by hws on 16/6/25.
 */
import java.io.IOException;

/***
 *
 * 得到第一秒（也是第一帧）图片
 */
public class VideoFirstThumbTaker extends VideoThumbTaker
{
    public VideoFirstThumbTaker(String ffmpegApp)
    {
        super(ffmpegApp);
    }

    public void getThumb(String videoFilename, String thumbFilename, int width,
                         int height) throws IOException, InterruptedException
    {
        super.getThumb(videoFilename, thumbFilename, width, height, 0, 0, 1);
    }
}