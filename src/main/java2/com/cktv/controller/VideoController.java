package com.cktv.controller;

import com.cktv.domain.Video;
import com.cktv.serviceManager.VideoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by hws on 16/6/25.
 */
@Controller
@RequestMapping("/video")
public class VideoController extends BaseController {
    @Autowired
    private VideoManager videoManager;

    @RequestMapping("/allVideos/{offset}/{limit}")
    @ResponseBody
    public Map<String,Object> selectAllVideos(@PathVariable("offset")int offset,@PathVariable("limit")int limit){
       Map<String,Object> map= videoManager.selectAllVideos(offset,limit);
        return map;
    }

    @RequestMapping("/uploadVideo")
    @ResponseBody
    public Map<String,Object> uploadVideo(@RequestParam(value = "file", required = false) MultipartFile file){
        videoManager.insertVideo(file);
        return generateSuccessMsg("上传视频成功");
    }

    @RequestMapping("/deleteVideoByVideo_id/{video_id}")
    @ResponseBody
    public Map<String,Object> deleteVideoByVideo_id(@PathVariable("video_id")int video_id){
        videoManager.deleteVideoByVideo_id(video_id);
        return generateSuccessMsg("删除视频成功");
    }

    @RequestMapping("deleteVideos")
    @ResponseBody
    public Map<String,Object> deleteVideos( @RequestParam("video_ids[]") List<Integer> video_ids){
      int video_ids1[]=new int[video_ids.size()];
        for(int i=0;i<video_ids.size();i++){
            video_ids1[i]=video_ids.get(i);
        }
        videoManager.deleteVideos(video_ids1);
        return generateSuccessMsg("删除视频成功");
    }
}
