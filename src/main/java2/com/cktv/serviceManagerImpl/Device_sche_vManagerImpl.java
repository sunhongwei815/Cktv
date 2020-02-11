package com.cktv.serviceManagerImpl;

import com.cktv.domain.Device_sche_v;
import com.cktv.mapper.Device_sche_vMapper;
import com.cktv.mapper.PublishMapper;
import com.cktv.mapper.Publish_deviceMapper;
import com.cktv.serviceManager.Device_sche_vManager;
import com.cktv.util.exception.MessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by hws on 2016/5/27.
 */
@Component
public class Device_sche_vManagerImpl implements Device_sche_vManager {

    @Autowired

    private Device_sche_vMapper device_sche_vMapper;

    @Autowired
    private  PublishMapper publishMapper;

    @Autowired
    private Publish_deviceMapper publish_deviceMapper;


    @Override
    public void insertDeviceScheV(Device_sche_v device_sche_v) {
        device_sche_vMapper.insertDevice_sche_v(device_sche_v);
    }

    @Override
    public long selectSche_vByDevice_did(String device_did ) {
        return device_sche_vMapper.selectSche_vByDevice_did(device_did);
    }

    @Override
    public void updateSche_vByPublish_id(long publish_id) {
        if (publishMapper.selectByPrimaryKey(publish_id).getPublish_status() == 1) {
            String[] device_dids = publish_deviceMapper.selectDevice_didsByPublish_id(publish_id);
            if (device_dids != null && device_dids.length != 0) {
                device_sche_vMapper.updateSche_vByDevice_dids(new Date(), device_dids);
            }
        }
    }
    @Override
    public void deleteDevice_sche_vsByDevice_dids(List<String> device_dids) {
        device_sche_vMapper.deleteDevice_sche_vsByDevice_dids(device_dids);
    }

}
