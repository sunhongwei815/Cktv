package com.cktv.config;

import com.trt.util.user.SessionUtil;

import java.io.File;

/**
 * Created by linpeng123l on 16/5/6.
 * lp
 */
public class Config {

    //项目名称
    public final static String PROJECT_NAME = "Cktv";

    //上传模板存放路径
    private final static String WEB_UPLOAD_PATH="/"+PROJECT_NAME+"/src/upload/";

    private final static String UPLOAD_PATH= SessionUtil.getCurrentPath()+File.separator+"src" + File.separator+ "upload"+File.separator;

    public final static String WEB_TPL_SAVE_PATH = WEB_UPLOAD_PATH+"tpl/";

    public final static String TPL_ADRESS=File.separator + "src" + File.separator + "upload" + File.separator + "tpl" + File.separator;
    //在线模板上传地址\animation\for-edit\assets(包括上传的图片和ppt截图的图片)
    public final static String ONLINE_UPLOAD_PICTURE=File.separator+"animation"+File.separator+"for-edit"+File.separator+"assets"+File.separator;
    public final static String WEB_ONLINE_UPLOAD_PICTURE="/animation/for-edit/assets/";
    //在线制作模板网页保存的地址  index.html
    public final static String ONLINE_TPL= File.separator+"animation"+File.separator+"for-edit"+File.separator;
    public final static String WEB_ONLINE_TPL="/animation/for-edit/";

    public final static String TPL_SAVE_PATH = UPLOAD_PATH+"tpl"+ File.separator;

    //public final static String TPL_PICTURE_PATH=TPL_SAVE_PATH+"picture"+File.separator;

    public final static String WEB_TPL_PICTURE_PATH=WEB_TPL_SAVE_PATH+"picture/";

//    public final static String WEB_TPL_IMG_SAVE_PATH = WEB_UPLOAD_PATH + "tpl/tpl-thumb/" ;

//    public final static String TPL_IMG_SAVE_PATH = UPLOAD_PATH+File.separator+"tpl"+File.separator+"tpl-thumb"+File.separator;

    public final static String WEB_VIDEO_SAVE_PATH=WEB_UPLOAD_PATH+"video/";

    public final static String VIDEO_SAVE_PATH=UPLOAD_PATH+"video"+File.separator;

//    public final static String WEB_VIDEO_THUMB_SAVE_PATH=WEB_UPLOAD_PATH+"video/video-thumb/";

//    public final static String VIDEO_THUMB_SAVE_PATH=UPLOAD_PATH+"video"+File.separator+"video-thumb"+File.separator;

    public final static String WEB_PUBLISH_TPL_SAVE_PATH = WEB_UPLOAD_PATH+"publish-tpl/";

    public final static String PUBLISH_TPL_SAVE_PATH = UPLOAD_PATH+"publish-tpl"+ File.separator;

    public final static String REDIRECT_URL_MANAGE = "/" +PROJECT_NAME +"/"+"pages"+"/"+"system-manage"+"/"+"user-manage"+"/"+"user-manage.html";

    public final static String REDIRECT_URL_USER =  "/" + PROJECT_NAME + "/" +"pages"+"/"+"display-manage"+"/"+"publish-list.html";
}
