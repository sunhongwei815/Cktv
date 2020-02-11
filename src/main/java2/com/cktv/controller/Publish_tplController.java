package com.cktv.controller;

import com.alibaba.fastjson.JSONObject;
import com.cktv.domain.Publish_tpl;
import com.cktv.serviceManager.Publish_tplManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hws on 2016/5/28.
 */
@Controller
@RequestMapping("/publish_tpl")
public class Publish_tplController extends BaseController{
    @Autowired
    private Publish_tplManager publish_tplManager;

    @RequestMapping("/insertPublish_tpls")
    @ResponseBody
    public Map<String,Object> insertPublish_tpls(@RequestBody List<Publish_tpl> publish_tpls){
        publish_tplManager.insertPublish_tpls(publish_tpls);
        return generateSuccessMsg("添加成功");
    }

    @RequestMapping("/deletePublish_tplByPublish_tpl_id/{publish_tpl_id}")
    @ResponseBody
    public Map<String,Object> deletePublish_tplByPublish_tpl_id(@PathVariable long publish_tpl_id){
        publish_tplManager.deletePublish_tplByPublish_tpl_id(publish_tpl_id);
        return generateSuccessMsg("删除成功");
    }

    @RequestMapping(value = "/publish_tplEdit/{publish_id}/{publish_tpl_id}")
    public ModelAndView publish_tplEdit(@PathVariable("publish_id") long publish_id, @PathVariable("publish_tpl_id") long publish_tpl_id) {
        Publish_tpl publish_tpl = publish_tplManager.selectPublish_tplByPublish_tpl_id(publish_tpl_id);
        ModelAndView modelAndView = new ModelAndView("/card-manage/edit-card");
        modelAndView.addObject("publish_tpl", publish_tpl);
        modelAndView.addObject("publish_id", publish_id);
        return modelAndView;
    }
    @RequestMapping("/updatePublish_tplContent")
    @ResponseBody
    public Map<String, Object> updatePublish_tplContent(@RequestBody JSONObject jsonObject) {
        publish_tplManager.updatePublish_tplContent(jsonObject);
        return generateSuccessMsg("更新页面成功");
    }

    //更新播放顺序和时长
    @RequestMapping("/updatePublish_tplPlay_order")
    @ResponseBody
    public Map<String,Object> updatePublish_tplPlay_order(@RequestBody List<Publish_tpl> publish_tpls){
        publish_tplManager.updatePublish_tplPlay_orderAndDuration(publish_tpls);
        return generateSuccessMsg("更新播放顺序和时长成功");

    }


    @RequestMapping("/uploadPublish_tplImg")
    @ResponseBody
    public Map<String,Object> uploadPublish_tplImg (@RequestParam(value="publish_tpl_id",required = false) int publish_tpl_id,@RequestParam(value="file",required = false)MultipartFile file){
        String filePath= publish_tplManager.uploadPublish_tplImg(publish_tpl_id,file);
        Map<String,Object> map = generateSuccessMsg("上传成功");
        map.put("filePath",filePath);
        return map;
    }




}
