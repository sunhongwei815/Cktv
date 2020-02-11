package com.cktv.serviceManagerImpl;

import com.alibaba.fastjson.JSONObject;
import com.cktv.config.Config;
import com.cktv.domain.Tpl;
import com.cktv.mapper.TplMapper;
import com.cktv.serviceManager.TplManager;
import com.cktv.util.exception.MessageException;
import com.cktv.util.file.CopyFileUtil;
import com.cktv.util.file.DeleteFileUtil;
import com.cktv.util.file.PptUtil;
import com.cktv.util.file.SaveFileUtil;
import com.cktv.util.html.VideoParaseUtil;
import com.cktv.util.newscreen_key.NewScreen_key;
import com.cktv.util.session.SessionUtils;
import com.cktv.util.string.StringUtil;
import com.cktv.util.velocity.VelocityTool;
import com.cktv.util.video.VideoInfo;
import com.cktv.util.zip.ZipUtils;
import com.trt.util.user.SessionUtil;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by hws on 2016/5/27.
 */
@Component
public class TplManagerImpl implements TplManager {
    @Autowired
    private TplMapper tplMapper;

    @Override
    public List<Tpl> selectAllTpls() {
        return tplMapper.selectAll();
    }

    @Override
    public void uploadAndSave(MultipartFile file, MultipartFile thumb, Tpl tpl) {
        //解压上传的*.zip文件
        CommonsMultipartFile cmf = (CommonsMultipartFile) file;
        DiskFileItem fi = (DiskFileItem) cmf.getFileItem();
        File f = fi.getStoreLocation();
        String []zipName=fi.getName().split("\\.");
        //判断模板的tpl_name是否唯一
        Tpl tempTpl=tplMapper.selectByTplName(zipName[0]);
        if(tempTpl!=null){
            throw new MessageException("模板重名，上传失败！");
        }
        try {
            ZipUtils.unzip(f, Config.TPL_SAVE_PATH+File.separator);//解压后保存的文件地址
        } catch (Exception e) {
            e.printStackTrace();
            throw  new MessageException("解压zip文件出错！");
        }
        String destName = thumb.getOriginalFilename();
        String destDirName = Config.TPL_SAVE_PATH + zipName[0] + File.separator;
        try {
            SaveFileUtil.saveFile(destDirName, destName, thumb.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            throw new MessageException("获取图片流出错");
        }
        String indexHtmlWebPath = Config.WEB_TPL_SAVE_PATH + zipName[0] + "/" + "animation/for-edit/index.html";
        String indexPath=destDirName+File.separator+"animation"+File.separator+"for-edit"+File.separator+"index.html";
        String videoWebPath= Config.WEB_TPL_SAVE_PATH + zipName[0] + "/" + "animation/for-edit/";   //视频基础路径
        String videoAbsoultePath=Config.TPL_SAVE_PATH+zipName[0]+File.separator + "animation"+File.separator+"for-edit"+File.separator;//视频存放的绝对基础路径。
        //保存到数据库
        Tpl tpl1 = new Tpl();
        tpl1.setTpl_name(zipName[0]);
        tpl1.setTpl_model(tpl.getTpl_model());
        tpl1.setTpl_business(0);
        tpl1.setIs_video(tpl.getIs_video());
        tpl1.setTpl_size(tpl.getTpl_size());
        tpl1.setTpl_address(File.separator + "src" + File.separator + "upload" + File.separator + "tpl" + File.separator +zipName[0]);
        tpl1.setDynamic_url(indexHtmlWebPath);
        tpl1.setUser_id(SessionUtils.getCurrentUser().getUser_id());//当前用户
        tpl1.setThumb_url(Config.WEB_TPL_SAVE_PATH + zipName[0] + "/" + destName);
        tpl1.setEdit_url(tpl.getEdit_url());
        tpl1.setEdit_address(tpl.getEdit_address());
        tpl1.setDuration(String.valueOf(10));
        if (tpl.getIs_video() == 1) {
            //如果模板含有视频，解析模板index.html中video的路径。
            String origin_url=VideoParaseUtil.getFirstVideoSrcFromFile(indexPath);
            String video_url=videoWebPath+origin_url;
            SaveFileUtil.saveHtml(indexPath,VideoParaseUtil.replaceVideoSrcFromFile(indexPath,video_url));
            tpl1.setVideo_source_url(video_url);
            tpl1.setTpl_x_coordinate(VideoParaseUtil.getFirstVideoXFromFile(indexPath));
            tpl1.setTpl_y_coordinate(VideoParaseUtil.getFirstVideoYFromFile(indexPath));
            tpl1.setTpl_width(VideoParaseUtil.getFirstVideoWidthFromFile(indexPath));
            tpl1.setTpl_height(VideoParaseUtil.getFirstVideoHeightFromFile(indexPath));
            //通过解析模板，求出模板时长
            String video_source=videoAbsoultePath+origin_url;
            //ffmegapp路径
            String ffmegapp_source=SessionUtil.getCurrentPath()+File.separator+"src"+File.separator+"util"+File.separator+"ffmpeg.exe";
            VideoInfo videoInfo=new VideoInfo(ffmegapp_source);
            try {
                videoInfo.getInfo(video_source);
                System.out.println(videoInfo.getMinutes());
                float videotime=videoInfo.getHours()*3600+videoInfo.getMinutes()*60+videoInfo.getSeconds();
                tpl1.setDuration(String.valueOf(videotime));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        tplMapper.insertTpl(tpl1);
    }

    @Override
    public void deleteTplByTpl_id(int tpl_id) {
        Tpl tpl = tplMapper.selectByPrimaryKey(tpl_id);
        String destDirName = SessionUtil.getCurrentPath() + File.separator + tpl.getTpl_address();
        DeleteFileUtil.deleteDirectory(destDirName);
        tplMapper.deleteTplByTpl_id(tpl_id);
    }

    @Override
    public List<Tpl> selectTplsByTpl_model(int tp1_model) {
        return tplMapper.selectTplsByTpl_model(tp1_model);
    }

    @Override
    public String download(int tpl_id) {
        Tpl tpl = tplMapper.selectByPrimaryKey(tpl_id);
        String zipname = tpl.getTpl_name() + ".zip"; //压缩后的文件名
        String zipAdress = "/Cktv/"+"src/" + "upload/" + "tpl/"  +"download/"+ zipname;//zipAdress就是压缩文件相对url
        String destDirName = SessionUtil.getCurrentPath()+ tpl.getTpl_address();//存放模板路径
        String aimDirname = SessionUtil.getCurrentPath() + File.separator+"src" + File.separator + "upload" + File.separator + "tpl" + File.separator+"download"+File.separator;
        ZipUtils.zipfiles(zipname, aimDirname, destDirName); //压缩destDirName文件夹
        return zipAdress;
    }

    @Override
    public String uploadAndSavePicture(MultipartFile picture,int tpl_id) {
        if (picture.isEmpty()) {
            throw new MessageException("请上传图片！");
        } else {
            String pictureName=picture.getOriginalFilename();
            Tpl tpl=tplMapper.selectByPrimaryKey(tpl_id);
            String tplName=tpl.getTpl_name();
            // 图片路径
            String pictureFolder=Config.TPL_SAVE_PATH+tplName+Config.ONLINE_UPLOAD_PICTURE+File.separator;
            String picturepath =pictureFolder+ pictureName;
            String webpath = Config.WEB_TPL_SAVE_PATH+tplName+Config.WEB_ONLINE_UPLOAD_PICTURE+pictureName;
            File fileFolder = new File(pictureFolder);
            if (!fileFolder.exists()) {
                fileFolder.mkdirs();
            }
            File file=new File(picturepath);
            try {
                picture.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
                throw new MessageException("添加图片失败！");
            }
            return webpath;
        }

    }

    @Override
    public Tpl saveTpl(Tpl tpl,MultipartFile thumb) {
        //判断模板的tpl_name是否唯一
        Tpl tempTpl=tplMapper.selectByTplName(tpl.getTpl_name());
        if(tempTpl!=null){
            throw new MessageException("模板重名，上传失败！");
        }
        if (tpl != null) {
            String tplName=tpl.getTpl_name();
            String tpldir = Config.TPL_SAVE_PATH + tplName+ File.separator; //tpl文件夹实际地址
            String tpl_web=Config.WEB_TPL_SAVE_PATH+tplName+"/";   //tpl背景图 web位置
            String tpl_url=Config.TPL_ADRESS+tplName;
            File files = new File(tpldir);
            if (!files.exists()) {
                files.mkdirs();
            }
            //保存图片：
            String thumbName = thumb.getOriginalFilename();
            String thumb_url=tpl_web+thumbName;  //图片数据库保存地址
            try {
                SaveFileUtil.saveFile(tpldir, thumbName, thumb.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                throw new MessageException("保存图片失败！");
            }
            tpl.setThumb_url(thumb_url);
            tpl.setTpl_address(tpl_url);
            tpl.setUser_id(SessionUtils.getCurrentUser().getUser_id());
            tpl.setDuration(String.valueOf(10));
            tplMapper.insertTpl(tpl);
            return tplMapper.selectByTplName(tplName);
        }
        return null;
    }

    @Override
    public void upLoadPPT(MultipartFile ppt, Tpl tpl) {
        //判断模板的tpl_name是否唯一
        Tpl tempTpl=tplMapper.selectByTplName(tpl.getTpl_name());
        if(tempTpl!=null){
            throw new MessageException("模板重名，上传失败！");
        }
        //图片保存的地址
        String save_adress=Config.TPL_SAVE_PATH+tpl.getTpl_name()+Config.ONLINE_UPLOAD_PICTURE;
        //图片网络访问地址
        String pictureSWebPath=Config.WEB_TPL_SAVE_PATH+tpl.getTpl_name()+Config.WEB_ONLINE_UPLOAD_PICTURE;
        //转化为file
        CommonsMultipartFile cmf = (CommonsMultipartFile) ppt;
        DiskFileItem fi = (DiskFileItem) cmf.getFileItem();
        File f = fi.getStoreLocation();
        String name=ppt.getOriginalFilename();
        //将ppt转化为图片
        List<String> allPicturesPath=PptUtil.ChangePptToPictures(f,name,save_adress,pictureSWebPath);
       //添加首尾两张图片
        int pageSize=allPicturesPath.size();
        /*if(pageSize!=0) {
            allPicturesPath.add(0, allPicturesPath.get(allPicturesPath.size() - 1));
            allPicturesPath.add(allPicturesPath.size(), allPicturesPath.get(1));
        }*/
        //制作html，并保存
        String htmlSavePath=Config.TPL_SAVE_PATH+tpl.getTpl_name()+File.separator+"animation"+File.separator+"for-edit"+File.separator;
        String htmlWebUrl=Config.WEB_TPL_SAVE_PATH+tpl.getTpl_name()+"/animation/for-edit/index.html";
        String vmPath="pages\\carousel\\carousel2.html";
        VelocityContext context=new VelocityContext();
        //添加图片地址
        context.put("list",allPicturesPath);
        //添加页数
        int []pageArray=new int[pageSize];
        for(int i=0;i<pageSize;i++){
            pageArray[i]=i;
        }
        context.put("size",pageArray);
        VelocityTool.generateHtmlByVelocity("index.html",htmlSavePath,vmPath,context);
        //添加数据到数据库
        tpl.setIs_video(2);
        tpl.setTpl_address(Config.TPL_ADRESS+tpl.getTpl_name());
        tpl.setThumb_url("/Cktv/src/upload/tpl/"+tpl.getTpl_name()+"/animation/for-edit/assets/1.jpeg");
        tpl.setDynamic_url(htmlWebUrl);
        tpl.setTpl_business(0);
        tpl.setDuration(String.valueOf(10));
        tpl.setUser_id(SessionUtils.getCurrentUser().getUser_id());
        tplMapper.insertTpl(tpl);
    }

    @Override
    public void saveTplHtml(JSONObject jsonObject, int tpl_id) {
        Tpl tpl = tplMapper.selectByPrimaryKey(tpl_id);
        if(tpl==null){
            throw new MessageException("不存在该模板！");
        }
        String tplName = tpl.getTpl_name();
        String html = jsonObject.get("html").toString();
        //保存index.html的物理路径
        String tplHtmlAdress = Config.TPL_SAVE_PATH + tplName + Config.ONLINE_TPL;
        //index.html访问的网络路径
        String web_tplAdress = Config.WEB_TPL_SAVE_PATH + tplName + Config.WEB_ONLINE_TPL + "index.html";
        //保存html
        SaveFileUtil.saveFile(tplHtmlAdress, "index.html", html);
        tpl.setDynamic_url(web_tplAdress);
        if (tpl.getIs_video() == 1) {
            //如果是视频模板，则需要复制视频到 index.html所在同一文件夹里,并且保存视频的相关信息
            String video_source_url = VideoParaseUtil.getFirstVideoSrcFromStr(html);
            String newVideoName = StringUtil.lastSubStringBySplit(video_source_url);//radio名字
            //视频拷贝
            String video_url = SessionUtil.getCurrentPath() + StringUtil.removeStringHeaderWithCktv(video_source_url);
            CopyFileUtil.copyFile(video_url, tplHtmlAdress + newVideoName, true);
            //数据库中保存新radio的路径
            String newVideoUrl = Config.WEB_TPL_SAVE_PATH + tplName + Config.WEB_ONLINE_TPL + newVideoName;
            tpl.setVideo_source_url(newVideoUrl);
            tpl.setTpl_x_coordinate(VideoParaseUtil.getFirstX_coordinateFromStr(html));
            tpl.setTpl_y_coordinate(VideoParaseUtil.getFirstY_coordinateFromStr(html));
            tpl.setTpl_width(VideoParaseUtil.getFirstWidthFromStr(html));
            tpl.setTpl_height(VideoParaseUtil.getFirstHeightFromStr(html));
            //保存模板时长
            String video_source = tplHtmlAdress + newVideoName;
            String ffmegapp_source = SessionUtil.getCurrentPath() + File.separator + "src" + File.separator + "util" + File.separator + "ffmpeg.exe";
            VideoInfo videoInfo = new VideoInfo(ffmegapp_source);
            try {
                videoInfo.getInfo(video_source);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println(videoInfo.getMinutes());
            float videotime=videoInfo.getHours()*3600+videoInfo.getMinutes()*60+videoInfo.getSeconds();
            //保存视频时长
            tpl.setDuration(String.valueOf(videotime));
        }
        tplMapper.updateByPrimaryKey(tpl);
    }

    @Override
    public Map<String, Object> selectTplsByPages(long startPage, long size) {
        long startNum=(startPage-1)*size;
        long sumNum=tplMapper.lengthOfAllTpl();
        List<Tpl> tpls=tplMapper.selectTplsByPages(startNum, size);
        Map<String,Object> map=new HashMap();
        map.put("sumNum",sumNum);
        map.put("tpls",tpls);
        return map;
    }


}

