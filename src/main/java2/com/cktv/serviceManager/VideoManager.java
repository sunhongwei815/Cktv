package com.cktv.serviceManager;

import com.cktv.domain.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by hws on 16/6/25.
 */
public interface VideoManager {
    Map<String,Object> selectAllVideos(int offset, int limit);

    void insertVideo(MultipartFile file);

    void deleteVideoByVideo_id(int video_id);

    void deleteVideos(int[] videos);
}
