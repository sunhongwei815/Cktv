package com.cktv.mapper;

import com.cktv.domain.Publish_tpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hws on 2016/5/27.
 */
@Component
public interface Publish_tplMapper {
    long insertPublish_tpl(Publish_tpl publishTpl);

    Publish_tpl selectByPrimaryKey(long publish_tpl_id);

    void updateByPrimaryKey(Publish_tpl publishTpl);

    List<Publish_tpl> selectAll ();

    List<Publish_tpl> selectPublish_tplsByPublish_id(long publish_id);

    void deletePublish_tplsByPublish_id(long publish_id);

    void updateByPrimaryKeySelective(Publish_tpl publish_tpl);

    void deleteByPrimaryKey(long publish_tpl_id);

    long selectCountPublish_tplsByPublish_id(long publish_id);

    void updatePublish_tplPlay_order(@Param("publish_tpl_id")long publish_id,@Param("play_order")long play_order);

    void updatePublish_tplDuration(@Param("publish_tpl_id")long publish_id,@Param("duration")String duration);

    void updateStart_timeEnd_time(@Param("publish_tpl_id") long publish_tpl_id,@Param("start_time") String start_time,@Param("end_time") String end_time);
}
