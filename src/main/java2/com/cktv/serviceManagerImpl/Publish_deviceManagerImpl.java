package com.cktv.serviceManagerImpl;

import com.cktv.domain.Publish_device;
import com.cktv.mapper.Device_sche_vMapper;
import com.cktv.mapper.Publish_deviceMapper;
import com.cktv.serviceManager.Device_sche_vManager;
import com.cktv.serviceManager.Publish_deviceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by hws on 2016/6/3.
 */
@Component
public class Publish_deviceManagerImpl implements Publish_deviceManager {

    @Autowired
    private Publish_deviceMapper publish_deviceMapper;

    @Autowired
    private Device_sche_vMapper device_sche_vMapper;
    @Override
    public void deletePublish_deviceByPublish_device(long publish_id,String device_did) {
        String[] device_dids=new String[1];
        device_dids[0]=device_did;
        device_sche_vMapper.updateSche_vByDevice_dids(new Date(),device_dids);
        publish_deviceMapper.deletePublish_deviceByPublish_device(publish_id,device_did);
    }

    @Override
    public void insertPublish_device(List<Publish_device> publish_devices) {
        for(int i=0;i<publish_devices.size();i++) {
            publish_deviceMapper.insertPublish_device(publish_devices.get(i));
            String device_did=publish_devices.get(i).getDevice_did();
            device_sche_vMapper.updateSche_vByDevice_dids(new Date(),device_did);
        }
    }
}
