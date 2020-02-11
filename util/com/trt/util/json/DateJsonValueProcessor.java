package com.trt.util.json;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DateJsonValueProcessor implements JsonValueProcessor{  
	private String format = "yyyy-MM-dd HH:mm:ss";  

	public DateJsonValueProcessor(){
	}

	public DateJsonValueProcessor(String format){
		this.format = format;
	}

	@Override
	public Object processArrayValue(Object value, JsonConfig jsonConfig){
		String[] obj = {};
		if (value instanceof Date[]){
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date[] dates = (Date[]) value;
			obj = new String[dates.length];
			for (int i = 0; i < dates.length; i++){
				obj[i] = sf.format(dates[i]);
			}
		}
		return obj;
	}

	@Override
	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig){
		if (value instanceof Date){
			String str = new SimpleDateFormat(format).format((Date) value);
			return str;
		}
		return value;
	}

	public String getFormat(){
		return format;
	}
	public void setFormat(String format){
		this.format = format;
	}
}  