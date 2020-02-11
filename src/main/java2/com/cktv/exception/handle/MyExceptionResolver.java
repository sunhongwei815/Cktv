package com.cktv.exception.handle;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.cktv.util.exception.MessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jsf
 * @ClassName: MyExceptionResolver
 * @Description: TODO(这里用一句话描述这个类的作用)
 */
public class MyExceptionResolver extends SimpleMappingExceptionResolver {

    static Logger logger = LoggerFactory.getLogger(MyExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,HttpServletResponse response, Object handler, Exception e) {
        e.printStackTrace();
        HandlerMethod method = (HandlerMethod) handler;
        ResponseBody body = method.getMethodAnnotation(ResponseBody.class);
        ModelAndView modelAndView = new ModelAndView();
        //判断有没有@ResponseBody的注解没有的话调用父方法
        if (body == null) {
            modelAndView = new ModelAndView("error");
            if (e instanceof MessageException) {
                modelAndView.addObject("error_code",((MessageException)e).getError_code());
                modelAndView.addObject("msg", ((MessageException) e).getMsg());
				logger.error("[error]{}\\r\\n[messageexception]",e,((MessageException) e).getMsg());
            } else {
                modelAndView.addObject("error_code", 9999);
                modelAndView.addObject("msg", "服务器异常");
                logger.error("[error]{}\\r\\n[exception]",e,e.getMessage());
            }
            modelAndView.addObject("success", Boolean.FALSE);
            e.printStackTrace();
            return modelAndView;
        } else {
            //或者使用view视图返回
            FastJsonJsonView view = new FastJsonJsonView();
            Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("success", Boolean.FALSE);
            if (e instanceof MessageException) {
                attributes.put("error_code",((MessageException)e).getError_code());
                attributes.put("msg", ((MessageException) e).getMsg());
                logger.error("[error]{}\\r\\n[messageexception]",e,((MessageException) e).getMsg());
            } else {
                attributes.put("error_code", 9999);
                attributes.put("msg", "服务器异常");
                logger.error("[error]{}\\r\\n[exception]",e,e.getMessage());
            }
            view.setAttributesMap(attributes);
            modelAndView.setView(view);
            return modelAndView;
        }
    }

}
