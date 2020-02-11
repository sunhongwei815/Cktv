package com.cktv.mapper;

import com.cktv.domain.Video;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hws on 16/6/24.
 */
@Component
public interface VideoMapper {
    int selectVideosCount();
    void insertVideo(Video video);
    void deleteVideoByVideo_id(int video_id);
    List<Video> seleteAllVideosByPage(@Param("offset")int offset,@Param("limit")int limit);
    void deleteVideos(int[] videos);
    Video selectByPrimaryKey(int video_id);
}
