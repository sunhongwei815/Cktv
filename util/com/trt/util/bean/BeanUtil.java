package com.trt.util.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.trt.util.user.SessionUtil;

public class BeanUtil {
	private static ApplicationContext appContext;
	public static Object load(String name){
		if(appContext==null){
			appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(SessionUtil.getSession().getServletContext());
		} 
		return appContext.getBean(name);
	}
	public static Object load2(String name){
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		return wac.getBean(name);
	}
}
