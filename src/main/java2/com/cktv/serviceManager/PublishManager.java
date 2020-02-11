package com.cktv.serviceManager;

import com.cktv.domain.Publish;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by hws on 2016/5/27.
 */
public interface PublishManager {
    void insertPublish(Publish publish);

    Publish selectPublishByPublish_id(long publish_id);

    void updateByPublish_id(Publish publish);

    List<Publish> selectAll ();

    void deletePublishByPublish_id(long publish_id);

    void updatePublish(long publish_id,String publish_name,int public_screen_mode);
    int checkPublicModeByPublic_id(long public_id);

    List<Publish> selectUserPublishs();

    void updatePublish_statusBypublish_id(long publish_id,long publish_status);

    Publish selectPublishByPublish_name(String publish_name);

    List<Publish> selectOnPlayingPublishsByDevice_did(String device_did);

    void updateStart_timeEnd_time(long publish_id,String start_time,String end_time);

    Map<String,Object> selectUserPublishsByPage(int offset, int limit);

    Map<String,Object> selectPublishsByScreen_mode(int offset,int limit,int publish_screen_mode);
}
