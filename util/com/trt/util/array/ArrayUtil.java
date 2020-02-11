package com.trt.util.array;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.trt.tree.util.TreeMode;

public class ArrayUtil {
	
	public static String toStringWithSeparator(List<Long> objects,String separator){
		if(objects==null||objects.size()==0){
			return "";
		}
		StringBuffer returnStr = new StringBuffer("");
		for(int i = 0;i<objects.size()-1;i++){
			returnStr.append("'"+objects.get(i)+"'"+separator);
		}
		returnStr.append("'"+objects.get(objects.size()-1)+"'");
		return returnStr.toString();
	}

	/**
	 * 将treeMode集合的id转换成in语句类型
	 * @param treeModes 传入treeMode集合
	 * @return ('1','2')类型字符串
	 */
	public static String treeModeIdsToString(List<TreeMode> treeModes){
		if(treeModes==null){
			return "";
		}
		StringBuffer returnStr = new StringBuffer("");
		for(int i = 0;i<treeModes.size()-1;i++){
			returnStr.append("'"+treeModes.get(i).getId()+"',");
		}
		returnStr.append("'"+treeModes.get(treeModes.size()-1).getId()+"'");
		return returnStr.toString();
	}
	/**
	 * 中间以,分离为了查询方便（in）
	 * @return
	 */
	public static String listToStr(List<Long> values){
		System.out.println(values+" ===");
		if(CollectionUtils.isEmpty(values)){
			return "";
		}
		StringBuffer returnStr = new StringBuffer();
		if(values != null){
			for(int i = 0;i<values.size()-1;i++){
				returnStr.append(values.get(i)+",");
			}
			returnStr.append(values.get(values.size()-1));
		}
		return returnStr.toString();
	}
	
	/**
	 * 中间以,分离为了查询方便（in）
	 * @return
	 */
	public static String arrayToStr(long[] values){
		StringBuffer returnStr = new StringBuffer();
		if(values != null){
			for(int i = 0;i<values.length-1;i++){
				returnStr.append(values[i]+",");
			}
			returnStr.append(values[values.length-1]);
		}
		return returnStr.toString();
	}
	
	public static void assignValue(int[] objects,int start,int limit,int value){
		System.out.println(start+" "+limit);
		for(int i = start;i<start+limit;i++){
			objects[i] = value;
		}
	}

	public static Object arrayToStr(Long[] array) {
		StringBuffer returnStr = new StringBuffer();
		if(array != null){
			for(int i = 0;i<array.length-1;i++){
				returnStr.append(array[i]+",");
			}
			returnStr.append(array[array.length-1]);
		}
		return returnStr.toString();
	}
}
