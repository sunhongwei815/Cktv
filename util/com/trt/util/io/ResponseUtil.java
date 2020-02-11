package com.trt.util.io;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ResponseUtil {
	public static void print(String printStr) {
		try {
			HttpServletResponse response =((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
			// response.setCharacterEncoding("gbk");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			// 这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
			response.setCharacterEncoding("UTF-8");
			// response.setContentType("text/html;charset=GBK");
			PrintWriter out = response.getWriter();
			out.println(printStr);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void print(String printStr, String encoding) {
		try {
			HttpServletResponse response =((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
			// response.setCharacterEncoding("gbk");
			response.setHeader("Content-type", "text/html;charset=" + encoding);
			// 这句话的意思，是告诉servlet用UTF-8转码，而不是用默认的ISO8859
			response.setCharacterEncoding(encoding);
			// response.setContentType("text/html;charset=GBK");
			PrintWriter out = response.getWriter();
			out.println(printStr);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * public static void print(String printStr) throws IOException{
	 * HttpServletResponse response = ServletActionContext.getResponse();
	 * response.setCharacterEncoding("UTF-8");
	 * response.setContentType("text/html;charset=UTF-8;"); PrintWriter out =
	 * response.getWriter(); out.print(printStr); out.flush(); out.close(); }
	 */

	public static void print(JSONArray jsonArray) {
		print(jsonArray.toString());
	}

	public static void print(JSONObject jsonObject) {
		print(jsonObject.toString());
	}
}
