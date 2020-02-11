package com.cktv.util.video;

/**
 * Created by hws on 16/6/25.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/**
 * @author Bashan
 * FFMPEG homepage http://ffmpeg.org/about.html
 * By Google Get first and last thumb of a video using Java and FFMpeg
 * From http://www.codereye.com/2010/05/get-first-and-last-thumb-of-video-using.html
 */

public class VideoThumbTaker
{
    protected String ffmpegApp;

    public VideoThumbTaker(String ffmpegApp)
    {
        this.ffmpegApp = ffmpegApp;
    }

    @SuppressWarnings("unused")
    /****
     * 获取指定时间内的图片
     * @param videoFilename:视频路径
     * @param thumbFilename:图片保存路径
     * @param width:图片长
     * @param height:图片宽
     * @param hour:指定时
     * @param min:指定分
     * @param sec:指定秒
     * @throws IOException
     * @throws InterruptedException
     */
    public void getThumb(String videoFilename, String thumbFilename, int width,
                         int height, int hour, int min, float sec) throws IOException,
            InterruptedException
    {
        ProcessBuilder processBuilder = new ProcessBuilder(ffmpegApp, "-y",
                "-i", videoFilename, "-vframes", "1", "-ss", hour + ":" + min
                + ":" + sec, "-f", "mjpeg", "-s", width + "*" + height,
                "-an", thumbFilename);

        Process process = processBuilder.start();

            InputStream stderr = process.getErrorStream();
        InputStreamReader isr = new InputStreamReader(stderr);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null)
            ;
        process.waitFor();

        if(br != null)
            br.close();
        if(isr != null)
            isr.close();
        if(stderr != null)
            stderr.close();
    }

//    public static void main(String[] args)
//    {
//        VideoThumbTaker videoThumbTaker = new VideoThumbTaker("F:/ffmpeg/ffmpeg.exe");
//        try
//        {
//            videoThumbTaker.getThumb("F:\\Git\\hwsStorage\\artifacts\\Cktv_war_exploded\\src\\video\\a.mp4", "F:\\Git\\hwsStorage\\artifacts\\Cktv_war_exploded\\src\\video\\thumb\\thumbTest.png",    800, 600, 0, 0, 9);
//            System.out.println("over");
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
}
