package com.cktv.util.interceptor;

import com.alibaba.fastjson.JSON;
import com.cktv.util.session.SessionUtils;
import com.cktv.util.token.TokenUtils;
import com.cktv.controller.MyVelocityServlet;

import com.trt.util.user.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jsf on 16/4/27..
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        System.out.println(requestUri);
        if (requestUri.contains("phone")) {
            return true;
        }
        if (requestUri.contains("/login.html") ||requestUri.contains("/velocity") || requestUri.contains("userLogin") || requestUri.contains("testPage")) {
            return true;
        }
        /*String data = request.getParameter("data");
        if (data != null) {
            JSONObject jsonObject = JSONObject.fromObject(data);
            JSONObject authJsonObject = jsonObject.getJSONObject("auth");
            String auth_token = authJsonObject.getString("auth_token");
            if (TokenUtils.getUser(auth_token) !=null){
                return true;
            }else {
                response.setContentType("application/json;charset=UTF-8");
                Map<String,Object> map = new HashMap<>();
                map.put("rst","error");
                map.put("req_id",jsonObject.getString("req_id"));
                map.put("req_method",jsonObject.getString("method"));
                map.put("error_code",1001);
                map.put("error_msg","设备还未登录，请登录！");
                response.getWriter().write(JSON.toJSONString(map));
                return false;
            }
        }
*/

        //判断用户是否登录
        if (SessionUtil.getSession() != null && SessionUtil.getSession().getAttribute("user") != null) {
            return true;
        } else {
            if(requestUri.contains("pages")){
                //保存本请求，用于登陆后自动跳转
                //SessionUtils.bindSession("redirectUrl",requestUri);
                response.sendRedirect("/Cktv/pages/user-login/login.html");
                return false;
            }else {
                response.setContentType("application/json;charset=UTF-8");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("msg", "您还没有登录,请登录");
                map.put("success", false);
                response.getWriter().write(JSON.toJSONString(map));
                return false;
            }
        }
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

}
