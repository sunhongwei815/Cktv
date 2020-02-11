package com.cktv.serviceManagerImpl;

import com.alibaba.fastjson.JSONObject;
import com.cktv.config.Config;
import com.cktv.domain.Publish;
import com.cktv.domain.Publish_tpl;
import com.cktv.domain.Tpl;
import com.cktv.mapper.*;
import com.cktv.serviceManager.Device_sche_vManager;
import com.cktv.serviceManager.Publish_tplManager;
import com.cktv.util.file.CopyFileUtil;
import com.cktv.util.file.DeleteFileUtil;
import com.cktv.util.file.SaveFileUtil;
import com.cktv.util.html.VideoParaseUtil;
import com.cktv.util.session.SessionUtils;
import com.cktv.util.string.StringUtil;
import com.trt.util.user.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by hws on 2016/5/27.
 */
@Component
public class Publish_tplManagerImpl implements Publish_tplManager {
    @Autowired
    private TplMapper tplMapper;

    @Autowired
    private PublishMapper publishMapper;

    @Autowired
    private Publish_tplMapper publish_tplMapper;

    @Autowired
    private Publish_deviceMapper publish_deviceMapper;

    @Autowired
    private Device_sche_vManager device_sche_vManager;


    @Override
    public void insertPublish_tpls(List<Publish_tpl> publish_tpls) {
        if (publish_tpls == null || publish_tpls.size() == 0) {
            return;
        }
        for (int i = 0; i < publish_tpls.size(); i++) {
            Publish_tpl publish_tpl = publish_tpls.get(i);
            Tpl srcTpl = tplMapper.selectByPrimaryKey(publish_tpl.getSource_tpl_id());
            ;
            Publish publish = publishMapper.selectByPrimaryKey(publish_tpl.getPublish_id());
            publish_tpl.setPlay_order(publish_tplMapper.selectCountPublish_tplsByPublish_id(publish_tpl.getPublish_id()) + 1);
            publish_tpl.setTpl_name(srcTpl.getTpl_name());
            publish_tpl.setThumb_url(srcTpl.getThumb_url());
            publish_tpl.setTpl_model(srcTpl.getTpl_model());
            publish_tpl.setTpl_business(srcTpl.getTpl_business());
            publish_tpl.setTpl_size(srcTpl.getTpl_size());
            publish_tpl.setIs_video(srcTpl.getIs_video());
            publish_tpl.setTpl_x_coordinate(srcTpl.getTpl_x_coordinate());
            publish_tpl.setTpl_y_coordinate(srcTpl.getTpl_y_coordinate());
            publish_tpl.setTpl_width(srcTpl.getTpl_width());
            publish_tpl.setTpl_height(srcTpl.getTpl_height());
            publish_tpl.setVideo_source_url(srcTpl.getVideo_source_url());
            publish_tpl.setStart_time(publish.getStart_time());
            publish_tpl.setEnd_time(publish.getEnd_time());
            publish_tpl.setDuration(srcTpl.getDuration());
            publish_tpl.setUser_id(SessionUtils.getCurrentUser().getUser_id());
            publish_tplMapper.insertPublish_tpl(publish_tpl);
            String publish_name = publishMapper.selectByPrimaryKey(publish_tpl.getPublish_id()).getPublish_name();
            String tpl_name = publish_tpl.getTpl_name();
            String srcDir = srcTpl.getTpl_address().replace("\\", File.separator);
            String destDynamic_url = srcTpl.getDynamic_url();
            String a[] = destDynamic_url.split("/animation");
            String destDir = File.separator + "src" + File.separator + "upload" + File.separator + "publish-tpl" + File.separator + SessionUtils.getCurrentUser().getUser_name() + File.separator + publish_name + publish_tpl.getPublish_id() + File.separator + tpl_name + publish_tpl.getPublish_tpl_id() + File.separator;
            String webDestDir = Config.WEB_PUBLISH_TPL_SAVE_PATH + SessionUtils.getCurrentUser().getUser_name() + "/" + publish_name + publish_tpl.getPublish_id() + "/" + tpl_name + publish_tpl.getPublish_tpl_id() + "/";
            String srcDirName = SessionUtil.getCurrentPath() + srcDir;
            String destDirName = SessionUtil.getCurrentPath().replace("\\", "/") + File.separator + destDir;
            CopyFileUtil.copyDirectory(srcDirName, destDirName, true);
            publish_tpl.setTpl_address(destDir);
            publish_tpl.setDynamic_url(webDestDir + "animation" + a[1]);
            publish_tplMapper.updateByPrimaryKeySelective(publish_tpl);

        }
        long publish_id = publish_tplMapper.selectByPrimaryKey(publish_tpls.get(0).getPublish_tpl_id()).getPublish_id();
        if (publish_id != 0) {
            device_sche_vManager.updateSche_vByPublish_id(publish_id);

        }


    }

    @Override
    public void deletePublish_tplByPublish_tpl_id(long publish_tpl_id) {
        Publish_tpl publish_tpl = publish_tplMapper.selectByPrimaryKey(publish_tpl_id);
        long publish_id = publish_tpl.getPublish_id();
        long play_order = publish_tpl.getPlay_order();
        List<Publish_tpl> publish_tpls = publish_tplMapper.selectPublish_tplsByPublish_id(publish_id);
        for (int i = 0; i < publish_tpls.size(); i++) {
            Publish_tpl publish_tpl2 = publish_tpls.get(i);
            long play_order2 = publish_tpl2.getPlay_order();
            if (play_order2 > play_order) {
                play_order2 = play_order2 - 1;
                publish_tplMapper.updatePublish_tplPlay_order(publish_tpl2.getPublish_tpl_id(), play_order2);
            }
        }
        String destDirName = SessionUtil.getCurrentPath() + publish_tpl.getTpl_address();
        publish_tplMapper.deleteByPrimaryKey(publish_tpl_id);
        DeleteFileUtil.deleteDirectory(destDirName);
        device_sche_vManager.updateSche_vByPublish_id(publish_id);
    }

    @Override
    public Publish_tpl selectPublish_tplByPublish_tpl_id(long publish_tpl_id) {
        return publish_tplMapper.selectByPrimaryKey(publish_tpl_id);
    }

    @Override
    public void updatePublish_tplContent(JSONObject jsonObject) {
        Publish_tpl publish_tpl = publish_tplMapper.selectByPrimaryKey(jsonObject.getLong("publish_tpl_id"));
        //目标文件的地址
        String destDir = publish_tpl.getTpl_address();
        String destDirName = SessionUtil.getCurrentPath() + File.separator + destDir + "animation" + File.separator + "for-edit" + File.separator;

        //保存修改的html
        SaveFileUtil.saveFile(destDirName, "index.html", jsonObject.get("html").toString());
        if (publish_tpl.getIs_video() == 1) {
            //video_source_url:替换视频的uri;
            // newVideoName:替换视频的名字
            String video_source_url = VideoParaseUtil.getFirstVideoSrcFromStr(jsonObject.get("html").toString());
            String newVideoName = StringUtil.lastSubStringBySplit(video_source_url);
            //视频拷贝
            String video_url = SessionUtil.getCurrentPath() + StringUtil.removeStringHeaderWithCktv(video_source_url);
            CopyFileUtil.copyFile(video_url, destDirName + StringUtil.lastSubStringBySplit(newVideoName), true);
            //拷贝的新视频的地址
            String newCopyVideoUrl = ("/Cktv" + destDir + "animation/for-edit/" + newVideoName).replace("\\", "/");
            publish_tpl.setVideo_source_url(newCopyVideoUrl);
        }
        publish_tplMapper.updateByPrimaryKey(publish_tpl);
        device_sche_vManager.updateSche_vByPublish_id(publish_tpl.getPublish_id());
    }

    @Override
    public void updatePublish_tplPlay_orderAndDuration(List<Publish_tpl> publish_tpls) {
        for (int i = 0; i < publish_tpls.size(); i++) {
            Publish_tpl publish_tpl = publish_tpls.get(i);
            publish_tplMapper.updatePublish_tplPlay_order(publish_tpl.getPublish_tpl_id(), publish_tpl.getPlay_order());
            //视频模板不需要时长
            Publish_tpl temp = publish_tplMapper.selectByPrimaryKey(publish_tpl.getPublish_tpl_id());
            if (temp.getIs_video() == 2) {
                publish_tplMapper.updatePublish_tplDuration(publish_tpl.getPublish_tpl_id(), publish_tpl.getDuration());
            }
        }
        if (publish_tpls.size() != 0) {
            long publish_id = publish_tplMapper.selectByPrimaryKey(publish_tpls.get(0).getPublish_tpl_id()).getPublish_id();
            device_sche_vManager.updateSche_vByPublish_id(publish_id);
        }

    }

    @Override
    public String uploadPublish_tplImg(int publish_tpl_id, MultipartFile file) {
        String tpl_address = publish_tplMapper.selectByPrimaryKey(publish_tpl_id).getTpl_address();
        String destDir = SessionUtil.getCurrentPath() + File.separator + tpl_address + File.separator + "animation" + File.separator + "for-edit" + File.separator + "assets";
        try {
            SaveFileUtil.saveFile(destDir, file.getOriginalFilename(), file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filePath = "assets/" + file.getOriginalFilename();
        return filePath;

    }
}