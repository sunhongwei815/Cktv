package com.cktv.serviceManager;

import com.alibaba.fastjson.JSONObject;
import com.cktv.domain.Tpl;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by hws on 2016/5/27.
 */
public interface TplManager {

    List<Tpl> selectAllTpls();

    void uploadAndSave(MultipartFile file,MultipartFile thumb, Tpl tpl);

    void deleteTplByTpl_id(int tpl_id);

    List<Tpl> selectTplsByTpl_model(int tp1_model);

    String download(int tpl_id);

    String uploadAndSavePicture(MultipartFile picture,int tpl_id);

    Tpl saveTpl(Tpl tpl,MultipartFile thumb);

    void upLoadPPT(MultipartFile ppt,Tpl tpl);//上传ppt,返回解析后图片的地址

    void saveTplHtml(JSONObject jsonObject,int tpl_id);

    Map<String,Object> selectTplsByPages(long startPage, long size);

}
