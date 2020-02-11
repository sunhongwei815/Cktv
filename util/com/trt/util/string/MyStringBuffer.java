package com.trt.util.string;

public class MyStringBuffer{
	private StringBuffer stringBuffer;

	public MyStringBuffer(StringBuffer stringBuffer) {
		super();
		this.stringBuffer = stringBuffer;
	}
	
	public MyStringBuffer(String string) {
		super();
		this.stringBuffer = new StringBuffer(string);
	}
	
	/**
	 * 向字符串添加字符并换行
	 * @param appendStr 添加的字符串
	 * @return
	 */
	public MyStringBuffer appendLn(String appendStr){
		stringBuffer.append(appendStr+"\r\n");
		return this;
	}
	/**
	 * 向字符串添加字符并换行
	 * @param appendStr 添加的字符串
	 * @return
	 */
	public MyStringBuffer appendLn(Object appendStr){
		stringBuffer.append(appendStr.toString()+"\r\n");
		return this;
	}
	
	/**
	 * 向字符串添加字符
	 * @param appendStr 添加的字符串
	 * @return
	 */
	public MyStringBuffer append(String appendStr){
		stringBuffer.append(appendStr);
		return this;
	}
	
	/**
	 * 向字符串添加字符
	 * @param appendStr 添加的字符串
	 * @return
	 */
	public MyStringBuffer append(Object appendStr){
		stringBuffer.append(appendStr.toString());
		return this;
	}

	/**
	 * 向字符串添加换行符
	 * @param lineNums 换几行
	 * @return
	 */
	public MyStringBuffer addBlankLines(int lineNums){
		for(int i=0;i<lineNums;i++){
			appendLn("");
		}
		return this;
	}
	
	@Override
	public String toString() {
		return stringBuffer.toString();
	}

	
}


