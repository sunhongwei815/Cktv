package com.cktv.util.interceptor;

import com.cktv.controller.MyVelocityServlet;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 11835 on 2016/8/26.
 */
public class VelocityInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        if(requestUri.endsWith(".html")){
            request.setAttribute(MyVelocityServlet.FORWARD_PATH,request.getServletPath());
            request.getRequestDispatcher("/velocity").forward(request,response);
            return false;
        }
        return true;
    }
}
