//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cktv.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.log.Log;
import org.apache.velocity.tools.view.ServletUtils;
import org.apache.velocity.tools.view.VelocityView;
import org.apache.velocity.tools.view.VelocityViewServlet;

public class MyVelocityServlet extends VelocityViewServlet {

    /**
     * 用于前面的servlet或者拦截器指定跳转时的原地址
     */
    public final static String FORWARD_PATH = "forward_path";

    @Override
    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setAttribute("javax.servlet.include.servlet_path",request.getAttribute(FORWARD_PATH));
        super.doRequest(request, response);
    }

}
