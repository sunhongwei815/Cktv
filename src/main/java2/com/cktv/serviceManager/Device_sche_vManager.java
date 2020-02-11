package com.cktv.serviceManager;

import com.cktv.domain.Device_sche_v;

import java.util.List;

/**
 * Created by hws on 2016/5/27.
 */
public interface Device_sche_vManager {
    void insertDeviceScheV(Device_sche_v device_sche_v);

    long selectSche_vByDevice_did(String device_did);

    void updateSche_vByPublish_id(long publish_id);
    public void deleteDevice_sche_vsByDevice_dids(List<String> device_dids);
}
