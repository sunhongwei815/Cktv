package com.trt.util;

import java.io.UnsupportedEncodingException;

final public class ChangeCodeUtil {
	public static String changeCode(String string){

		try {
			if(string!=null){
				byte[] temp1=string.getBytes("ISO-8859-1");
				String compstring1=new String(temp1, "ISO-8859-1");
			    if(compstring1.equals(string)){
					string= new String(string.getBytes("ISO-8859-1"),"utf-8");
				}
				
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return string;
	}
	public class TT{}
}