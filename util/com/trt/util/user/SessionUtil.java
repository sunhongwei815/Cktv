package com.trt.util.user;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



public class SessionUtil {

	/**
	 * 得到session对象
	 * @return
	 * @throws SessionNotExitException 
	 */
	public static HttpSession getSession(){
		ServletRequestAttributes attr = (ServletRequestAttributes)  RequestContextHolder.currentRequestAttributes(); 
		HttpSession session = attr.getRequest().getSession();
		if(session==null){
			throw new SessionNotExitException();
		}
		return session;
	}
	public static void bindSession(String str,Object obj){
		getSession().setAttribute(str, obj);
	}
	public static void logoutSession(String str){
		getSession().setAttribute(str, null);
	}
	public static String getCurrentPath(){
		return getSession().getServletContext().getRealPath("/");
	}
	public static File getWebappPath(){
		File file = new File(getCurrentPath());
		return file.getParentFile();
	}
	public static class SessionNotExitException extends RuntimeException{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}
}
