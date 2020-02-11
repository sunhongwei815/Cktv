/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package com.trt.util.jz;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

public class TextUtil{

	public TextUtil(){
	}

	/**
	 * 格式化赋值
	 * @param i 总体位数
	 * @param j 设置值
	 * @return
	 */
	public static final String format(int ws, int value){
		StringBuffer stringbuffer = new StringBuffer();
		String strValue = String.valueOf(value);
		int syws = ws - strValue.length();
		if(syws > 0) {
			for(int i = 0; i < syws; i++) {
				stringbuffer.append("0");
			}
			stringbuffer.append(strValue);
		} else if(syws <= 0) {
			stringbuffer.append(strValue.substring(-syws));
		}
		return stringbuffer.toString();
	}

	public static final String format(long ws, long value) {
		StringBuffer stringbuffer = new StringBuffer();
		String strValue = String.valueOf(value);
		long syws = ws - strValue.length();
		if(syws > 0) {
			for(int i = 0; i < syws; i++) {
				stringbuffer.append("0");
			}
			stringbuffer.append(strValue);
		} else if(syws <= 0) {
			stringbuffer.append(strValue.substring((int)-syws));
		}
		return stringbuffer.toString();
	}

	public static final String rformat(int ws, String value){
		StringBuffer stringbuffer = new StringBuffer();
		int syws = ws - value.length();
		if(syws > 0) {
			stringbuffer.append(value);
			for(int i = 0; i < syws; i++){
				stringbuffer.append("0");
			}
		} else if(syws <= 0){
			stringbuffer.append(value.substring(0, ws));
		}
		return stringbuffer.toString();
	}

	public static final String rformat(int ws, String value, String appendValue){
		StringBuffer stringbuffer = new StringBuffer();
		int syws = ws - value.length();
		if(syws > 0){
			stringbuffer.append(value);
			for(int k = 0; k < syws; k++){
				stringbuffer.append(appendValue);
			}
		} else if(syws <= 0){
			stringbuffer.append(value.substring(0, ws));
		}
		return stringbuffer.toString();
	}

	/**
	 * 首字母转大写
	 * @param s
	 * @return
	 */
	public static final String initCap(String s){
		String s1 = s.toLowerCase();
		return (new StringBuilder()).append(s1.substring(0, 1).toUpperCase()).append(s1.substring(1, s1.length())).toString();
	}

	public static final String xreplace(String str, String replacedStr, String replaceStr) {
		StringBuffer stringbuffer = new StringBuffer();
		int i = replacedStr.length();
		int j;
		while((j = str.indexOf(replacedStr)) != -1) {
			stringbuffer.append(str.substring(0, j));
			stringbuffer.append(replaceStr);
			str = str.substring(j + i);
		}
		stringbuffer.append(str);
		return stringbuffer.toString();
	}

	/**
	 * 移除空格
	 * @param str
	 * @return
	 */
	public static String removeBlank(String str) {
		if(str == null || "".equals(str)) {
			return str;
		} else {
			str = xreplace(str, "\t", "");
			str = xreplace(str, "\n", "");
			str.replaceAll(" ", "");
			return str;
		}
	}

	public static final String decimalFormat(String str, String numberStr) {
		if(str == null || "".equals(str) || null == numberStr || "".equals(numberStr)){
			return numberStr;
		}
		DecimalFormat decimalformat = new DecimalFormat(str);
		double d1 = Double.parseDouble(numberStr);
		String returnValue = decimalformat.format(d1);
		if(returnValue.startsWith(".")){
			returnValue = (new StringBuilder()).append("0").append(returnValue).toString();
		} else if(returnValue.startsWith("-.")){
			returnValue = (new StringBuilder()).append("-0").append(returnValue.substring(1, returnValue.length())).toString();
		}
		return returnValue;
	}

	public static final String decimalFormat(String s, double d1) {
		if(s == null || "".equals(s)){
			return String.valueOf(d1);
		}
		d1 += 9.9999999999999994E-012D;
		DecimalFormat decimalformat = new DecimalFormat(s);
		String s1 = decimalformat.format(d1);
		if(s1.startsWith(".")){
			s1 = (new StringBuilder()).append("0").append(s1).toString();
		}else if(s1.startsWith("-.")){
			s1 = (new StringBuilder()).append("-0").append(s1.substring(1, s1.length())).toString();
		}
		return s1;
	}

	public static final double decimalFormatDouble(String s, double d1) {
		if(s == null || "".equals(s)){
			return d1;
		}
		d1 += 9.9999999999999994E-012D;
		DecimalFormat decimalformat = new DecimalFormat(s);
		Object obj = new Double(0.0D);
		try {
			obj = decimalformat.parse(decimalformat.format(d1));
		} catch(ParseException parseexception) {
			parseexception.printStackTrace();
		}
		return ((Number) (obj)).doubleValue();
	}

	public static final double decimalParse(String s, String s1) {
		if(s == null || "".equals(s) || null == s1 || "".equals(s1)){
			return 0.0D;
		}
		DecimalFormat decimalformat = new DecimalFormat(s);
		Object obj = new Double(0.0D);
		try {
			obj = decimalformat.parse(s1);
		} catch(ParseException parseexception) {
			parseexception.printStackTrace();
		}
		return ((Number) (obj)).doubleValue();
	}

	public static String toBigAmt(Double double1) {
		return toBigAmt(double1.doubleValue());
	}

	public static String toBigAmt(double d1) {
		String s = b.format(d1);
		String s1 = "";
		String s2 = "";
		boolean flag = false;
		if(s.startsWith("-")) {
			flag = true;
			s = s.substring(1);
		}
		int i;
		if((i = s.indexOf('.')) != -1) {
			s2 = s.substring(0, i);
			s1 = s.substring(i + 1);
		} else {
			s2 = s;
		}
		if(s2.length() > 16){
			throw new InternalError("The amount is too big.");
		}
		String s3 = b(s2);
		String s4 = a(s1);
		String s5 = null;
		a.debug(s1);
		if(s4.length() == 0 && s3.length() != 0){
			s5 = (new StringBuilder()).append(s3).append("元整").toString();
		}else if(s4.length() == 0 && s3.length() == 0){
			s5 = (new StringBuilder()).append(s3).append("零元").toString();
		} else if(s4.length() != 0 && s3.length() != 0) {
			if(s1.startsWith("0"))
				s4 = (new StringBuilder()).append("零").append(s4).toString();
			s5 = (new StringBuilder()).append(s3).append("元").append(s4).toString();
		} else {
			s5 = s4;
		}
		if(flag){
			s5 = (new StringBuilder()).append("负").append(s5).toString();
		}
		return s5;
	}

	private static String a(String s) {
		String s1 = "";
		for(int i = 0; i < s.length() && i < 3; i++) {
			int j;
			if((j = Integer.parseInt(s.substring(i, i + 1))) != 0){
				s1 = (new StringBuilder()).append(s1).append("零壹贰叁肆伍陆柒捌玖".substring(j, j + 1)).append("角分厘".substring(i, i + 1)).toString();
			}
		}
		return s1;
	}

	
	private static String b(String s) {
		String s1 = "";
		int i = s.length() / 4;
		if(s.length() % 4 != 0){
			i++;
		}
		for(int j = i; j >= 1; j--) {
			String s3 = a(s, j);
			s1 = (new StringBuilder()).append(s1).append(c(s3)).toString();
			s1 = d(s1);
			if(j <= 1){
				continue;
			}
			if(c(s3).equals("零零零零")){
				s1 = (new StringBuilder()).append(s1).append("零").toString();
			}else{
				s1 = (new StringBuilder()).append(s1).append("仟万亿兆".substring(j - 1, j)).toString();
			}
		}
		return s1;
	}

	private static String a(String s, int i) {
		String s1;
		if(s.length() <= i * 4){
			s1 = s.substring(0, s.length() - (i - 1) * 4);
		}else{
			s1 = s.substring(s.length() - i * 4, s.length() - (i - 1) * 4);
		}
		return s1;
	}

	private static String c(String s) {
		String s1 = "";
		for(int i = 0; i < s.length(); i++) {
			String s2 = s.substring(i, i + 1);
			int j = Integer.parseInt(s2);
			if(j == 0) {
				if(i != s.length()){
					s1 = (new StringBuilder()).append(s1).append("零").toString();
				}
				continue;
			}
			s1 = (new StringBuilder()).append(s1).append("零壹贰叁肆伍陆柒捌玖".substring(j, j + 1)).toString();
			if(i != s.length() - 1){
				s1 = (new StringBuilder()).append(s1).append("仟佰拾个".substring((i + 4) - s.length(), ((i + 4) - s.length()) + 1)).toString();
			}
		}
		return s1;
	}

	private static String d(String s) {
		String s2 = s.substring(0, 1);
		String s1 = s2;
		for(int i = 1; i < s.length(); i++) {
			String s3 = s.substring(i, i + 1);
			if(!s3.equals("零") || !s2.equals("零")){
				s1 = (new StringBuilder()).append(s1).append(s3).toString();
			}
			s2 = s3;
		}
		if(s1.substring(s1.length() - 1, s1.length()).equals("零")){
			s1 = s1.substring(0, s1.length() - 1);
		}
		return s1;
	}
	public static String vo2str(Object obj) {
		StringBuffer stringbuffer = new StringBuffer();
		String s = null;
		PropertyDescriptor apropertydescriptor[] = PropertyUtils.getPropertyDescriptors(obj);
		try {
			for(int i = 0; i < apropertydescriptor.length; i++) {
				String s1 = apropertydescriptor[i].getName();
				if("class".equals(s1)) {
					s = PropertyUtils.getProperty(obj, s1).toString();
				} else {
					stringbuffer.append(" ");
					stringbuffer.append(s1);
					stringbuffer.append(":");
					if("java.util.List".equals(apropertydescriptor[i].getPropertyType().getName())) {
						List<?> list = (List<?>)PropertyUtils.getProperty(obj, s1);
						if(list != null) {
							stringbuffer.append("[");
							for(int j = 0; j < list.size(); j++) {
								stringbuffer.append(vo2str(list.get(j)));
								stringbuffer.append("; ");
							}
							stringbuffer.deleteCharAt(stringbuffer.length() - 1);
							stringbuffer.deleteCharAt(stringbuffer.length() - 1);
							stringbuffer.append("]");
						} else {
							stringbuffer.append("null");
						}
					} else {
						stringbuffer.append(PropertyUtils.getProperty(obj, s1));
					}
				}
			}

		} catch(IllegalAccessException illegalaccessexception) {
			illegalaccessexception.printStackTrace();
		} catch(InvocationTargetException invocationtargetexception) {
			invocationtargetexception.printStackTrace();
		} catch(NoSuchMethodException nosuchmethodexception) {
			nosuchmethodexception.printStackTrace();
		}
		return (new StringBuilder()).append(s.substring(6)).append(stringbuffer.toString()).toString();
	}

	public static String filter(String s) {
		StringBuffer stringbuffer = new StringBuffer();
		for(int i = 0; i < s.length(); i++) {
			char c1 = s.charAt(i);
			if(c1 != '\n' && c1 != '\r')
				stringbuffer.append(s.subSequence(i, i + 1));
		}
		return stringbuffer.toString();
	}

	public static String selectformat(String s, String s1) {
		if(s == null || null == s1)
			return null;
		String as[] = s.split(";");
		String s2 = "";
		for(int i = 0; i < as.length; i++) {
			if(null == as[i] || "".equals(as[i])){
				continue;
			}
			String as1[] = as[i].split(":");
			if(!s1.equals(as1[0])){
				continue;
			}
			s2 = as1[1];
			break;
		}
		return s2;
	}

	public static String gb2asc(String str) {
		char ac[] = str.toCharArray();
		StringBuffer stringbuffer = new StringBuffer();
		for(int i = 0; i < ac.length; i++) {
			byte abyte0[] = String.valueOf(ac[i]).getBytes();
			if(abyte0.length == 1) {
				stringbuffer.append(ac[i]);
				continue;
			} 
			if(abyte0.length == 2) {
				stringbuffer.append("%");
				stringbuffer.append(-abyte0[0]);
				stringbuffer.append("%");
				stringbuffer.append(-abyte0[1]);
			}
		}
		return stringbuffer.toString();
	}

	public static String asc2gb(String str) {
		if(str.indexOf("%") == -1){
			return str;
		}
		String as[] = str.split("%");
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append(as[0]);
		byte abyte0[] = {
				1, 1
		};
		for(int i = 1; i < as.length; i++) {
			if(i % 2 == 1) {
				abyte0[0] = Byte.parseByte((new StringBuilder()).append("-").append(as[i]).toString());
				continue;
			}
			if(as[i].length() > 2) {
				stringbuffer.append(as[i].substring(2));
				abyte0[1] = Byte.parseByte((new StringBuilder()).append("-").append(as[i].substring(0, 2)).toString());
			} else {
				abyte0[1] = Byte.parseByte((new StringBuilder()).append("-").append(as[i]).toString());
			}
			stringbuffer.append(new String(abyte0));
		}

		return stringbuffer.toString();
	}

	public static boolean checkGb2312(String str) {
		boolean flag = false;
		byte abyte0[] = str.getBytes();
		int i = 0;
		do {
			if(i >= abyte0.length){
				break;
			}
			if(abyte0[i] < 0) {
				flag = true;
				break;
			}
			i++;
		} while(true);
		return flag;
	}

	public static boolean checkEnglish(String str) {
		boolean flag = false;
		byte abyte0[] = str.getBytes();
		int i = 0;
		do {
			if(i >= abyte0.length){
				break;
			}
			if(abyte0[i] >= 65 && abyte0[i] <= 122) {
				flag = true;
				break;
			}
			i++;
		} while(true);
		return flag;
	}

	public static boolean checkNumber(String numberStr) {
		boolean flag = true;
		byte abyte0[] = numberStr.getBytes();
		int i = 0;
		do {
			if(i >= abyte0.length){
				break;
			}
			if(abyte0[i] < 48 || abyte0[i] > 57) {
				flag = false;
				break;
			}
			i++;
		} while(true);
		return flag;
	}

	public static String transXmlString(String s) {
		String s1 = s;
		s1 = s1.replaceAll("&", "&amp;");
		s1 = s1.replaceAll(">", "&gt;");
		s1 = s1.replaceAll("<", "&lt;");
		return s1;
	}

	private static Logger a = Logger.getLogger("");
	private static final NumberFormat b = new DecimalFormat("#0.###");

}