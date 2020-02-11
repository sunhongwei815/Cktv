package com.trt.tree.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class TreeModeFormatUtil {
	
	/**
	 * 将treeMode转换成JsonArray对象[{...}]
	 * 由于前台只能显示子节点树
	 * @param treeMode
	 * @return
	 */
	public static JSONArray toJsonArray(TreeMode treeMode,boolean needChecked){
		JSONArray root = new JSONArray();
		JsonConfig config = new JsonConfig();
		if(!needChecked){
			config.registerPropertyExclusions(TreeMode.class, new String[]{"checked"});
		}
		root.add(JSONObject.fromObject(treeMode,config));
		return root;
	}

}
