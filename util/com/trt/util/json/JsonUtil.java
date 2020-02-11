package com.trt.util.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

public class JsonUtil{

	public static JSONObject jsonObject(Object object){
		JsonConfig jsonConfig = new JsonConfig();  
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
		return JSONObject.fromObject(object,jsonConfig);
	}

	public static JSONArray jsonArray(Object object){
		JsonConfig jsonConfig = new JsonConfig();  
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
		
		return JSONArray.fromObject(object,jsonConfig);
	}
	
	/**
	 * 转换json数据-->Map集合
	 * @param s
	 * @return
	 */
	public static Map<String,Object> parserToMap(String jsonStr){  
		Map<String,Object> map=new HashMap<String,Object>();  
		JSONObject json=JSONObject.fromObject(jsonStr);  
		Iterator<?> keys=json.keys();  
		while(keys.hasNext()){  
			String key=(String) keys.next();  
			String value=json.get(key).toString();  
			if(value.startsWith("{")&&value.endsWith("}")){  
				map.put(key, parserToMap(value));  
			}else{  
				map.put(key, value);  
			}  

		}  
		return map;  
	}  
	public static Object toBean(JSONObject jsonObject, Class<?> beanClass){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"}));
		return JSONObject.toBean(jsonObject, beanClass);
	}
	public static List toList(JSONArray jsonArray, Class<?> beanClass){
		List list = new ArrayList<>();
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"}));
		if(jsonArray!=null&&jsonArray.size()==0){
			for(int i = 0;i<jsonArray.size();i++){
				list.add(toBean(jsonArray.getJSONObject(i), beanClass));
			}
		}
		return list;
	}
}
