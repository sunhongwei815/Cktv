package com.cktv.controller;

import com.alibaba.fastjson.JSONObject;
import com.cktv.domain.Tpl;
import com.cktv.serviceManager.PublishManager;
import com.cktv.serviceManager.TplManager;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Created by hws on 2016/5/28.
 */
@Controller
@RequestMapping("/tpl")
public class TplController extends BaseController {
    @Autowired
    private TplManager tplManager;
    @Autowired
    private PublishManager publishManager;

    @RequestMapping("/allTpls")
    @ResponseBody
    public List<Tpl> allTpls() {
        return tplManager.selectAllTpls();
    }
    //按分页显示模板
    @RequestMapping(value = "/selectTplsByPages/{startPage}/{size}")
    @ResponseBody
    public Map<String,Object>selectTplsByPages(@PathVariable("startPage")long startPage,@PathVariable("size") long size){
          return tplManager.selectTplsByPages(startPage, size);
    }
    @RequestMapping(value = "/selectTplsByTpl_model/{tp1_model}")
    @ResponseBody
    public List<Tpl> selectTplsByTpl_model(@PathVariable int tp1_model) {
        return tplManager.selectTplsByTpl_model(tp1_model);
    }

    @RequestMapping(value = "/tplsPage/{publish_id}")
    public ModelAndView tplsPage(@PathVariable long publish_id) {
        ModelAndView modelAndView = new ModelAndView("/card-manage/append-card1.0");
        int publish_screen_mode = publishManager.checkPublicModeByPublic_id(publish_id);
        modelAndView.addObject("publish_id", publish_id);
        modelAndView.addObject("publish_screen_mode", publish_screen_mode);
        return modelAndView;
    }

    @RequestMapping("/allSystemTpls")
    public ModelAndView allSystemTpls() {
        ModelAndView model = new ModelAndView("/system-manage/system-manage");
        List<Tpl> tpls = tplManager.selectAllTpls();
        model.addObject("tpls", tpls);
        return model;
    }

    @RequestMapping("deleteTplByTpl_id/{tpl_id}")
    @ResponseBody
    public Map<String, Object> deleteTplByTpl_id(@PathVariable("tpl_id") int tpl_id) {
        tplManager.deleteTplByTpl_id(tpl_id);
        return generateSuccessMsg("删除模板成功");
    }

    /**
     * 功能：上传zip模板文件
     *
     * @param file 上传的文件
     * @param tpl  方式一
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(Tpl tpl, @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "thumb", required = false) MultipartFile thumb) {
        tplManager.uploadAndSave(file, thumb, tpl);
        return generateSuccessMsg("上传成功");
    }

    /**
     * 功能：在线制作模板，保存模板表单基本信息（第二种模板）
     *
     * @param thumb 保存模板封面图
     * @param tpl   模板基本信息
     * @return
     */
    @RequestMapping("/saveTpl")
    @ResponseBody
    public Map<String,Object> saveTpl(Tpl tpl, @RequestParam(value = "thumb") MultipartFile thumb) {
        Tpl update_tpl = tplManager.saveTpl(tpl, thumb);
        Map map=generateSuccessMsg("添加成功！");
        map.put("tpl",update_tpl);
        return map;
    }

    @RequestMapping("/saveTplHtml/{tpl_id}")
    @ResponseBody
    public Map<String, Object> saveTplHtml(@RequestBody JSONObject jsonObject, @PathVariable(value = "tpl_id") int tpl_id) {
        tplManager.saveTplHtml(jsonObject, tpl_id);
        return generateSuccessMsg("修改成功！");
    }

    /**
     * 功能：在线制作模板的时候，上传图片
     *
     * @param picture 上传的图片
     * @param tpl_id  模板id
     * @return
     */
    @RequestMapping("/uploadPicture/{tpl_id}")
    @ResponseBody
    public Map<String, Object> uploadPicture(@RequestParam(value = "picture") MultipartFile picture, @PathVariable("tpl_id") int tpl_id) {
        String picturePath = tplManager.uploadAndSavePicture(picture, tpl_id);
        Map map = generateSuccessMsg("上传成功!");
        map.put("picturePath", picturePath);
        return map;
    }

    /**
     * 功能:上传ppt，制作ppt模板(第三种模板)
     *
     * @param ppt 上传ppt
     * @return
     */
    @RequestMapping(value = "/upLoadPPT")
    @ResponseBody
    public Map<String, Object> upLoadPPT(@RequestParam("ppt") MultipartFile ppt, Tpl tpl) {
        tplManager.upLoadPPT(ppt, tpl);
        return generateSuccessMsg("添加模板成功！");
    }

    /**
     * 功能：模板下载
     *
     * @param tpl_id 模板id
     * @return
     */
    @RequestMapping(value = "download/{tpl_id}")
    @ResponseBody
    public Map download(@PathVariable("tpl_id") int tpl_id) {
        String zipAdress = tplManager.download(tpl_id);
        Map map = generateSuccessMsg("成功");
        map.put("url", zipAdress);
        return map;
    }
}
