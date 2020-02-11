package com.cktv.controller;

import com.cktv.domain.Publish_device;
import com.cktv.serviceManager.Publish_deviceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by hws on 2016/6/3.
 */
@RequestMapping("/publish_device")
@Controller
public class Publish_deviceController extends BaseController {
    @Autowired
    private Publish_deviceManager publish_deviceManager;

    @RequestMapping("/insertPublish_device")
    @ResponseBody
    public Map<String, Object> insertPublish_device(@RequestBody List<Publish_device> publish_devices){
        publish_deviceManager.insertPublish_device(publish_devices);
        return generateSuccessMsg("添加成功");
    }

    @RequestMapping("/deletePublish_deviceByPublish_device/{publish_id}/{device_did}")
    @ResponseBody
    public Map<String,Object> deletePublish_deviceByPublish_device(@PathVariable long publish_id, @PathVariable String device_did){
        publish_deviceManager.deletePublish_deviceByPublish_device(publish_id,device_did);
        return generateSuccessMsg("删除成功");
    }

}
