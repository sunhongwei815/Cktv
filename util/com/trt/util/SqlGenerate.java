package com.trt.util;

import java.util.List;


public class SqlGenerate {

	public String type;
	private String queryStr;
	private List<Condition> conditions;
	
	public void addComdition(){
		
	}
	
	public String generateSql(){
		StringBuffer sql = new StringBuffer();
		sql.append(queryStr);
		for(Condition condition : conditions){
			sql.append(condition.getSql());
		}
		return sql.toString();  
	}
	
	
	public class Condition{
		private String conditionStr;
		private Object value;
		private String name;
		private Object nullValue;
		public String getConditionStr() {
			return conditionStr;
		}
		public void setConditionStr(String conditionStr) {
			this.conditionStr = conditionStr;
		}
		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSql(){
			if(value==null||value.equals(nullValue)){
				return "";
			}else{
				return conditionStr.replaceAll(name,value.toString());
			}
		}
	}
}
