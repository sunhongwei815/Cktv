package com.cktv.controller;

import com.cktv.domain.Device;
import com.cktv.domain.Verify_code;
import com.cktv.serviceManager.Verify_codeManager;
import com.fasterxml.jackson.databind.deser.Deserializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by mgh on 2016/6/3.
 */
@Controller
@RequestMapping(value="/verify_code")
public class Verify_codeController extends BaseController {
    @Autowired
    private Verify_codeManager verify_codeManager;

    //生成number条长度为length的激活码
    @RequestMapping(value = "/generateActive_codes/{number}/{length}")
    @ResponseBody
    public Map<String,Object> generateActive_codes(@PathVariable("number") long number, @PathVariable("length") long length)
    {
        if(number==0){
            return generateFailureMsg("生成失败，请确认生成的个数！");
        }
        verify_codeManager.generateVerify_codes(number,length);
        return generateSuccessMsg("成功生成数据");
    }

    //查询所有的设备码
    @RequestMapping(value="/selectVerify_codeByIs_register/{is_register}/{pageNow}/{pageSize}")
    @ResponseBody
    public Map<String,Object> selectVerify_codeByIs_register(@PathVariable long is_register,@PathVariable long pageNow,@PathVariable long pageSize){
        return verify_codeManager.selectVerify_codeByIs_register(is_register,pageNow,pageSize);
    }

    @RequestMapping(value = "/deleteVerify_codeByVerify_code_id")
    @ResponseBody
    public Map<String,Object> deleteVerify_codeByVerify_code_id(@RequestParam ("verify_code_ids[]") List<Long> verify_code_ids){
        verify_codeManager.deleteVerify_codeByVerify_code_id(verify_code_ids);
        return generateSuccessMsg("删除成功！");
    }
    //按照设备码名称查询
    @RequestMapping(value = "/selectVerify_codeByCode/{verify_code}")
    @ResponseBody
    public Verify_code selectVerify_codeByCode(@PathVariable(value = "verify_code") String verify_code){
        return verify_codeManager.selectByPrimaryKey(verify_code);
    }

}
