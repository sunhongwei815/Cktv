package com.cktv.controller;

import com.cktv.domain.Publish;
import com.cktv.serviceManager.PublishManager;
import com.cktv.serviceManager.Publish_tplManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Created by hws on 2016/5/27.
 */
@RequestMapping("/publish")
@Controller
public class PublishController extends BaseController {
    @Autowired
    private PublishManager publishManager;


    @RequestMapping("/insertPublish")
    @ResponseBody
    public Map<String, Object> insertPublish(@RequestBody Publish publish) {
        publishManager.insertPublish(publish);
        return generateSuccessMsg("插入成功");
    }

    @RequestMapping("/deletePublishByPublish_id/{publish_id}")
    @ResponseBody
    public Map<String, Object> deletePublishByPublish_id(@PathVariable long publish_id) {
        publishManager.deletePublishByPublish_id(publish_id);
        return generateSuccessMsg("删除成功");
    }

    @RequestMapping("/updatePublish/{publish_id}/{publish_name}/{public_screen_mode}")
    @ResponseBody
    public Map<String, Object> updatePublish(@PathVariable long publish_id, @PathVariable String publish_name, @PathVariable int public_screen_mode) {
        publishManager.updatePublish(publish_id, publish_name, public_screen_mode);
        return generateSuccessMsg("修改成功");
    }

    @RequestMapping("/publishAddPage/{publish_id}")
    public ModelAndView publishAddPage(@PathVariable long publish_id) {
        Publish publish = publishManager.selectPublishByPublish_id(publish_id);
        ModelAndView modelAndView = new ModelAndView("/display-manage/add-info");
        modelAndView.addObject("publish", publish);
        return modelAndView;
    }

    @RequestMapping("/selectUserPublishsByPage/{offset}/{limit}")
    @ResponseBody
    public Map<String, Object> selectUserPublishsByPage(@PathVariable("offset") int offset, @PathVariable("limit") int limit) {

        Map<String, Object> map = publishManager.selectUserPublishsByPage(offset, limit);
        return map;
    }

    //分页显示Screen_mode的发布数据
    @RequestMapping("/selectPublishsByScreen_mode/{offset}/{limit}/{publish_screen_mode}")
    @ResponseBody
    public Map<String, Object> selectPublishsByScreen_mode(@PathVariable("offset") int offset, @PathVariable("limit") int limit, @PathVariable("publish_screen_mode") int publish_screen_mode) {
        return publishManager.selectPublishsByScreen_mode(offset, limit, publish_screen_mode);
    }

    @RequestMapping("/updatePublish_statusAndSche_v/{publish_id}/{type}")
    @ResponseBody
    public Map<String, Object> updatePublish_statusAndSche_v(@PathVariable long publish_id, @PathVariable String type) {
        if (type.equals("startPublish")) {
            publishManager.updatePublish_statusBypublish_id(publish_id, 1);
            return generateSuccessMsg("投放成功");
        } else if (type.equals("pausePublish")) {
            publishManager.updatePublish_statusBypublish_id(publish_id, 2);
            return generateSuccessMsg("暂停投放成功");
        }
        return null;
    }

    @RequestMapping("/selectPublishByPublish_name/{publish_name}")
    public ModelAndView selectPublishByPublish_name(@PathVariable String publish_name) {
        long publish_id = publishManager.selectPublishByPublish_name(publish_name).getPublish_id();
        ModelAndView modelAndView = new ModelAndView("redirect:/publish/publishAddPage/" + publish_id);
        return modelAndView;
    }

    @RequestMapping("/updateStart_timeEnd_time/{publish_id}/{start_time}/{end_time}")
    @ResponseBody
    public Map<String, Object> updateStart_timeEnd_time(@PathVariable long publish_id, @PathVariable String start_time, @PathVariable String end_time) {
        publishManager.updateStart_timeEnd_time(publish_id, start_time, end_time);
        return generateSuccessMsg("设置时间成功");
    }

}
