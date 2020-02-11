package com.cktv.serviceManagerImpl;

import com.cktv.config.Config;
import com.cktv.domain.Video;
import com.cktv.mapper.VideoMapper;
import com.cktv.serviceManager.VideoManager;
import com.cktv.util.file.DeleteFileUtil;
import com.cktv.util.file.SaveFileUtil;
import com.cktv.util.video.VideoFirstThumbTaker;
import com.cktv.util.video.VideoInfo;
import com.cktv.util.video.VideoThumbTaker;
import com.trt.util.user.SessionUtil;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hws on 16/6/25.
 */
@Component
public class VideoManagerImpl implements VideoManager{
    @Autowired
    private VideoMapper videoMapper;
    @Override
    public Map<String,Object> selectAllVideos(int offset, int limit) {
        Map map=new HashMap();
        int count=videoMapper.selectVideosCount()/limit+1;
        offset=(offset-1)*limit;
        List<Video> videos=videoMapper.seleteAllVideosByPage(offset,limit);
        map.put("sumPage",count);
        map.put("videos",videos);
        return map;
    }

    @Override
    public void insertVideo(MultipartFile file) {
        Video video=new Video();


        String video_name=file.getOriginalFilename();

        String fileName[]=video_name.split("\\.");
        String destDir= Config.VIDEO_SAVE_PATH +fileName[0]+ File.separator;
        try {
            SaveFileUtil.saveFile(destDir,video_name,file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        VideoInfo videoInfo = new VideoInfo(SessionUtil.getCurrentPath()+File.separator+"src"+File.separator+"util"+File.separator+"ffmpeg.exe");
        try {
            videoInfo.getInfo(destDir+video_name);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String video_time_length= String.valueOf(videoInfo.getHours()+":"+videoInfo.getMinutes()+":"+videoInfo.getSeconds());
        VideoThumbTaker videoThumbTaker=new VideoThumbTaker(SessionUtil.getCurrentPath()+File.separator+"src"+File.separator+"util"+File.separator+"ffmpeg.exe");
        String video_thumbDestDir=destDir+fileName[0]+".png";
        try {
            videoThumbTaker.getThumb(destDir+video_name,video_thumbDestDir,800,600,0,0,9);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        video.setVideo_thumb_url(Config.WEB_VIDEO_SAVE_PATH+fileName[0]+"/"+fileName[0]+".png");
        video.setVideo_time_length(video_time_length);
        video.setVideo_url(Config.WEB_VIDEO_SAVE_PATH+fileName[0]+"/"+video_name);
        video.setUpload_time(new Date());
        video.setVideo_name(video_name);

        videoMapper.insertVideo(video);

    }

    @Override
    public void deleteVideoByVideo_id(int video_id) {
        Video video=videoMapper.selectByPrimaryKey(video_id);
        String fileName[]=video.getVideo_name().split("\\.");
        String destDir=Config.VIDEO_SAVE_PATH+fileName[0];
        DeleteFileUtil.deleteDirectory(destDir);

        videoMapper.deleteVideoByVideo_id(video_id);

    }

    @Override
    public void deleteVideos(int[] videos) {
        for(int i=0;i<videos.length;i++){
            Video video=videoMapper.selectByPrimaryKey(videos[i]);
            String fileName[]=video.getVideo_name().split("\\.");
            String destDir=Config.VIDEO_SAVE_PATH+fileName[0];
            DeleteFileUtil.deleteDirectory(destDir);

        }

        videoMapper.deleteVideos(videos);
    }
}
