package com.trt.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCompareUtil {
	/**
	 * 
	 * @param lshLastDate
	 * @param currentDate
	 * @return 是否同一天
	 */
	public static boolean isDaySame(Date lshLastDate,Date currentDate){
		SimpleDateFormat lshLastDateFormat = new SimpleDateFormat("yyyyMMdd");
		String lshLastDateString = lshLastDateFormat.format(lshLastDate);
		String currentDateString = lshLastDateFormat.format(currentDate);
		if(lshLastDateString.equals(currentDateString))
			return true;
		else 
			return false;
	}
	/**
	 * 
	 * @param lshLastDate
	 * @param currentDate
	 * @return 是否同一月
	 */
	public static boolean isMonthSame(Date lshLastDate,Date currentDate){
		SimpleDateFormat lshLastDateFormat = new SimpleDateFormat("yyyyMM");
		String lshLastDateString = lshLastDateFormat.format(lshLastDate);
		String currentDateString = lshLastDateFormat.format(currentDate);
		if(lshLastDateString.equals(currentDateString))
			return true;
		else 
			return false;
	}
	/**
	 * 
	 * @param lshLastDate
	 * @param currentDate
	 * @return 是否同一年
	 */
	public static boolean isYearSame(Date lshLastDate,Date currentDate){
		SimpleDateFormat lshLastDateFormat = new SimpleDateFormat("yyyy");
		String lshLastDateString = lshLastDateFormat.format(lshLastDate);
		String currentDateString = lshLastDateFormat.format(currentDate);
		if(lshLastDateString.equals(currentDateString))
			return true;
		else 
			return false;
	}

}
