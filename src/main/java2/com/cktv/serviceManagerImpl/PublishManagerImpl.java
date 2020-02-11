package com.cktv.serviceManagerImpl;

import com.cktv.domain.Publish;
import com.cktv.domain.Publish_tpl;
import com.cktv.mapper.*;
import com.cktv.serviceManager.Device_sche_vManager;
import com.cktv.serviceManager.PublishManager;
import com.cktv.util.exception.MessageException;
import com.cktv.util.file.DeleteFileUtil;
import com.cktv.util.session.SessionUtils;
import com.trt.util.user.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.File;
import java.util.*;

/**
 * Created by hws on 2016/5/27.
 */
@Component
public class PublishManagerImpl implements PublishManager {

        @Autowired
        private PublishMapper publishMapper;

        @Autowired
        private Publish_deviceMapper publish_deviceMapper;

        @Autowired
        private Device_sche_vMapper device_sche_vMapper;

        @Autowired
        private Publish_tplMapper publish_tplMapper;

        @Autowired
        private Device_sche_vManager device_sche_vManager;

        @Autowired
        private DeviceMapper deviceMapper;




        public void insertPublish(Publish publish) {
                if (publish_nameIsExisted(publish.getPublish_name())) {
                        throw new MessageException("专辑名已存在");
                }else {
                        publish.setUser_id(SessionUtils.getCurrentUser().getUser_id());
                        publish.setPublish_time(new Date());
                        publish.setStart_time("0:00");
                        publish.setEnd_time("24:00");
                        int screen_mode=publish.getPublish_screen_mode();
                        publish.setPublish_status(2);
                        publishMapper.insertPublish(publish);
                }
        }
       //创建的时候判断
        public boolean publish_nameIsExisted(String publish_name) {
                long user_id=SessionUtils.getCurrentUser().getUser_id();
                List<String> all_user_publish_name = publishMapper.select_all_user_publish_names(user_id);
                for (int i = 0; i < all_user_publish_name.size(); i++) {
                        if (publish_name.equals(all_user_publish_name.get(i))) {
                                return true;
                        }
                }
                return false;
        }
        //修改的时候判断
        public boolean publish_nameIsExisted(String publish_name,long publish_id) {

                Publish publish=publishMapper.selectByPrimaryKey(publish_id);
                String name=publish.getPublish_name();
                if(publish_name.equals(name)){
                        return false;
                }else {
                        long user_id=SessionUtils.getCurrentUser().getUser_id();
                        List<String> all_user_publish_name = publishMapper.select_all_user_publish_names(user_id);
                        for (int i = 0; i < all_user_publish_name.size(); i++) {
                                if (publish_name.equals(all_user_publish_name.get(i))) {
                                        return true;
                                }
                        }
                }
                return false;
             }
        @Override
        public Publish selectPublishByPublish_id(long publish_id) {
                Publish publish = publishMapper.selectByPrimaryKey(publish_id);
                publish.loadPublish_devices();
                for (int i = 0; i < publish.getPublish_devices().size(); i++) {
                        publish.getPublish_devices().get(i).loadDevicedevice_did();
                }
                publish.loadPublish_tpls();
                publish.setTpl_count(publish.getPublish_tpls().size());

                return publish;
        }


        @Override
        public void updateByPublish_id(Publish publish) {
              publishMapper.updateByPrimaryKey(publish);
        }

        @Override
        public List<Publish> selectAll() {
                return publishMapper.selectAll();
        }

        public void deletePublishByPublish_id(long publish_id) {
                Publish publish = publishMapper.selectByPrimaryKey(publish_id);
                publish_tplMapper.deletePublish_tplsByPublish_id(publish_id);
                publish_deviceMapper.deletePublish_deviceByPublish_id(publish_id);

                String destDirName = SessionUtil.getCurrentPath()+ File.separator+"src"+File.separator+"upload"+File.separator+"publish-tpl"+File.separator + SessionUtils.getCurrentUser().getUser_name() + File.separator+ publish.getPublish_name() + publish.getPublish_id();
                DeleteFileUtil.deleteDirectory(destDirName);
                device_sche_vManager.updateSche_vByPublish_id(publish_id);
                publishMapper.deleteByPrimaryKey(publish_id);
        }

        @Override
        public void updatePublish(long publish_id, String publish_name,int public_screen_mode) {
                if (publish_nameIsExisted(publish_name,publish_id)) {
                        throw new MessageException("专辑名已存在");
                } else {
                        int public_screen_mode1=public_screen_mode;
                        Publish publish = publishMapper.selectByPrimaryKey(publish_id);
                        publish.setPublish_name(publish_name);
                        publish.setPublish_screen_mode(public_screen_mode);
                        publish.setUpdate_time(new Date());
                        publishMapper.updateByPrimaryKey(publish);
                        device_sche_vManager.updateSche_vByPublish_id(publish_id);
                }
        }

        @Override
        public int checkPublicModeByPublic_id(long public_id) {
                Publish publish=publishMapper.selectByPrimaryKey(public_id);
                int public_screen_mode=publish.getPublish_screen_mode();
                return public_screen_mode;
        }

        @Override
        public List<Publish> selectUserPublishs() {
                long auth_id = SessionUtils.getCurrentUser().getUser_id();
                List<Publish> publishs = publishMapper.selectPublishsByUser_id(auth_id);
                for (int i = 0; i < publishs.size(); i++) {
                        Publish publish = publishs.get(i);
                        publish.loadPublish_devices();
                        for (int x = 0; x < publish.getPublish_devices().size(); x++) {
                                publish.getPublish_devices().get(x).loadDevicedevice_did();
                        }
                        publish.loadPublish_tpls();
                        publish.setTpl_count(publish.getPublish_tpls().size());
                }
                return publishs;
        }

        public void updatePublish_statusBypublish_id(long publish_id, long publish_status) {
                if(publish_status==1){
                        Publish publish=publishMapper.selectByPrimaryKey(publish_id);
                        if(publish.getStart_time()==null){
                                String start_time="0:00";
                                publishMapper.updateStart_time(start_time,publish_id);
                        }if(publish.getEnd_time()==null){
                                String end_time="24:00";
                                publishMapper.updateEnd_time(end_time,publish_id);
                        }
                }

                publishMapper.updatePublish_statusBypublish_id(publish_id, publish_status);
                String[] device_dids = publish_deviceMapper.selectDevice_didsByPublish_id(publish_id);
                if(device_dids!=null&&device_dids.length!=0) {
                        Date update_time = new Date();
                        device_sche_vMapper.updateSche_vByDevice_dids(update_time, device_dids);
                }

        }




        public Publish selectPublishByPublish_name(String publish_name) {
                return publishMapper.selectPublishByPublish_name(publish_name);
        }

        public List<Publish> selectOnPlayingPublishsByDevice_did(String device_did) throws MessageException{
                long is_register=deviceMapper.selectByPrimaryKey(device_did).getIs_register();
                if(is_register==1) {
                        String[] publish_ids = publish_deviceMapper.selectPublish_idsByDevice_did(device_did);
                        if (publish_ids == null || publish_ids.length == 0) {
                                return new ArrayList<>();
                        }
                        List<Publish> publishs = publishMapper.selectOnPlayingPublishsByPublish_ids(publish_ids);
                        for (int i = 0; i < publishs.size(); i++) {
                                Publish publish = publishs.get(i);
                                publish.loadPublish_tpls();
                        }
                        return publishs;
                }
                else{
                        return new ArrayList<>();
                }
        }

        @Override
        public void updateStart_timeEnd_time(long publish_id,String start_time, String end_time) {
                List<Publish_tpl> publish_tpls=publish_tplMapper.selectPublish_tplsByPublish_id(publish_id);
                for(int i=0;i<publish_tpls.size();i++){
                        Publish_tpl publish_tpl=publish_tpls.get(i);
                        publish_tplMapper.updateStart_timeEnd_time(publish_tpl.getPublish_tpl_id(),start_time,end_time);
                }
                publishMapper.updateStart_timeEnd_time(publish_id,start_time,end_time);
                device_sche_vManager.updateSche_vByPublish_id(publish_id);
        }

        public Map<String,Object> selectUserPublishsByPage(int offset,int limmit) {
                Map<String,Object> map=new HashMap<>();
                offset=(offset-1)*limmit;
                long auth_id = SessionUtils.getCurrentUser().getUser_id();
                List<Publish> publishs = publishMapper.selectPublishsByUser_idByPage(auth_id,offset,limmit);
                for (int i = 0; i < publishs.size(); i++) {
                        Publish publish = publishs.get(i);
                        publish.loadPublish_devices();
                        for (int x = 0; x < publish.getPublish_devices().size(); x++) {
                                publish.getPublish_devices().get(x).loadDevicedevice_did();
                        }
                        publish.loadPublish_tpls();
                        publish.setTpl_count(publish.getPublish_tpls().size());
                }
                int count =publishMapper.selectPublishscountByUser_id(auth_id);
                int sumpage=count/limmit+1;

                map.put("publishs",publishs);
                map.put("sumpage",sumpage);
                return map;

        }

        @Override
        public Map<String,Object> selectPublishsByScreen_mode(int offset,int limmit,int publish_screen_mode) {
                Map<String,Object> map=new HashMap<>();
                offset=(offset-1)*limmit;
                long auth_id = SessionUtils.getCurrentUser().getUser_id();
                List<Publish> publishs = publishMapper.selectPublishsByScreen_mode(auth_id,offset,limmit,publish_screen_mode);
                for (int i = 0; i < publishs.size(); i++) {
                        Publish publish = publishs.get(i);
                        publish.loadPublish_devices();
                        for (int x = 0; x < publish.getPublish_devices().size(); x++) {
                                publish.getPublish_devices().get(x).loadDevicedevice_did();
                        }
                        publish.loadPublish_tpls();
                        publish.setTpl_count(publish.getPublish_tpls().size());
                }
                int count =publishMapper.selectPublishscountByUser_idAndScreen_mode(auth_id,publish_screen_mode);
                int sumpage=count/limmit+1;

                map.put("publishs",publishs);
                map.put("sumpage",sumpage);
                return map;
        }
}
