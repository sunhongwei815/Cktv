package com.cktv.serviceManager;



import com.cktv.domain.Publish;
import com.cktv.domain.Publish_device;

import java.util.List;

/**
 * Created by hws on 2016/4/18.
 */
public interface Publish_deviceManager {
    void deletePublish_deviceByPublish_device(long publish_id,String device_did);

    void insertPublish_device(List<Publish_device> publish_devices);



}
