package com.cktv.mapper;

import com.cktv.domain.Publish;
import com.cktv.domain.Publish_tpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hws on 2016/5/27.
 */
@Component
public interface PublishMapper {
    void insertPublish(Publish publish);

    Publish selectByPrimaryKey(long publish_id);

    void updateByPrimaryKey(Publish publish);

    List<Publish> selectAll ();

    void deleteByPrimaryKey(long publish_id);

    List<String> select_all_user_publish_names(long user_id);

    List<Publish> selectPublishsByUser_id(long user_id);

    long updatePublish_statusBypublish_id(@Param("publish_id")long publish_id, @Param("publish_status")long publish_status);

    Publish selectPublishByPublish_name(String publish_name);

    List<Publish> selectOnPlayingPublishsByPublish_ids(String[] publish_ids);

    void updateStart_timeEnd_time(@Param("publish_id") long publish_id,@Param("start_time") String start_time,@Param("end_time") String end_time);

    void updateStart_time(@Param("start_time") String start_time,@Param("publish_id")long publish_id);

    void updateEnd_time(@Param("end_time") String end_time,@Param("publish_id")long publish_id);

    int selectPublishscountByUser_id(long user_id);
    //显示选定的publish_screen_mode发布的个数
    int selectPublishscountByUser_idAndScreen_mode(@Param("user_id")long user_id,@Param("publish_screen_mode")int publish_screen_mode );

    List<Publish> selectPublishsByUser_idByPage(@Param("user_id") long user_id,@Param("offset")int offset,@Param("limit")int limit);
    //通过screen_mode来查询发布
    List<Publish> selectPublishsByScreen_mode(@Param("user_id")long user_id,@Param("offset")int offset,@Param("limit")int limit,@Param("publish_screen_mode")int publish_screen_mode);
}
