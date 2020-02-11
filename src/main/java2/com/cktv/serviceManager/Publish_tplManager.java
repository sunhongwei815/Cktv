package com.cktv.serviceManager;

import com.alibaba.fastjson.JSONObject;
import com.cktv.domain.Publish_tpl;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by hws on 2016/5/27.
 */
public interface Publish_tplManager {
    void insertPublish_tpls(List<Publish_tpl> publish_tpls);
    void deletePublish_tplByPublish_tpl_id(long publish_tpl_id);
    Publish_tpl selectPublish_tplByPublish_tpl_id(long publish_tpl_id);
    void updatePublish_tplContent(JSONObject jsonObject);
    void updatePublish_tplPlay_orderAndDuration(List<Publish_tpl> publish_tpls);
    String uploadPublish_tplImg(int publish_tpl_id, MultipartFile file);


}
