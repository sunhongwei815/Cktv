package com.trt.util;

public class ObjectDefaultValueSetUtil {
	public static Boolean falseDefaultValueSet(Boolean boolean1){
		return boolean1 == null?false:boolean1;
		
	}
	
	public static Boolean trueDefaultValueSet(Boolean boolean1){
		return boolean1 == null?true:boolean1;
		
	}
	
	/**
	 * @note str为空对象或者空字符串给一个默认值，反之即为它本身
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static String strDefaultValueSet(String str, String defaultValue){
		return (str == null || "".equals(str))?defaultValue:str;
		
	}

	public static Integer intDefaultValueSet(Integer value,Integer defaultValue){
		return value==null?defaultValue:value; 
	}
}
