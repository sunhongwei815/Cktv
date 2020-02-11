package com.cktv.util.video;

/**
 * Created by hws on 16/6/25.
 */
/**
 * 得到最后一秒（也是最后一帧）图片
 */


import java.io.IOException;

/**
 * 得到最后一秒（也是最后一帧）图片
 */

public class VideoLastThumbTaker extends VideoThumbTaker
{
    public VideoLastThumbTaker(String ffmpegApp)
    {
        super(ffmpegApp);
    }

    public void getThumb(String videoFilename, String thumbFilename, int width,
                         int height) throws IOException, InterruptedException
    {
        VideoInfo videoInfo = new VideoInfo(ffmpegApp);
        videoInfo.getInfo(videoFilename);
        super.getThumb(videoFilename, thumbFilename, width, height,
                videoInfo.getHours(), videoInfo.getMinutes(),
                videoInfo.getSeconds() - 0.2f);
    }
}
