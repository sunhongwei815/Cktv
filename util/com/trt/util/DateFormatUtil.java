package com.trt.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 日期格式化类
 * @author linpeng123l
 *
 */
public class DateFormatUtil {

	/**
	 * 将日期转换为指定格式字符串
	 * @param pattern 日期格式化规则
	 * @param date 日期
	 * @return 格式化后的字符串
	 */
	public static String formatByPattern(String pattern,Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	/**
	 * 将日期转换为指定格式字符串
	 * @param pattern 日期格式化规则
	 * @param date 日期
	 * @return 格式化后的字符串
	 * @throws ParseException 
	 */
	public static Date getDate(String dateStr,String pattern) throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.parse(dateStr);
	}
	
	/**
	 * 格式化成yyyy-MM-dd格式的字符串
	 * @param date 格式化的日期
	 * @return  格式化后的字符串
	 */
	public static String formatYmd(Date date){
		return formatByPattern("yyyy-MM-dd", date);
	}
	/**
	 * 格式化成yyyy-MM-dd格式的字符串
	 * @param date 格式化的日期
	 * @return  格式化后的字符串
	 */
	public static String formatYmdHms(Date date){
		return formatByPattern("yyyy-MM-dd HH:mm:ss", date);
	}
}
