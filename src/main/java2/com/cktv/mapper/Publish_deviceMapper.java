package com.cktv.mapper;

import com.cktv.domain.Publish_device;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hws on 2016/5/28.
 */
@Component
public interface Publish_deviceMapper {
    void insertPublish_device(Publish_device publish_device);

    Publish_device selectByPrimaryKey(long publish_device_id);

    void updateByPrimaryKey(Publish_device publish_device);

    List<Publish_device> selectAll ();

    List<Publish_device> selectPublish_devicesByPublish_id(long publish_id);

    String[] selectDevice_didsByPublish_id(long publish_id);

    String[] selectPublish_idsByDevice_did(String device_did);

    long deletePublish_deviceByPublish_device (@Param("publish_id")long publish_id, @Param("device_did")String device_did);

    void deletePublish_deviceByPublish_id(long publish_id);

}
