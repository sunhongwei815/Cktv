/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package com.trt.util.model;


public class PosResult{

	private String posaction;
	private String zhuangt;
	private String zhuangtxx;
	private int hangs;
	private int lies;
	private String posaction_ret;
	public PosResult(){
		posaction = "";
		zhuangt = "";
		zhuangtxx = "";
		hangs = 0;
		lies = 0;
		posaction_ret = "";
	}

	public int getHangs(){
		return hangs;
	}

	public void setHangs(int hangs){
		this.hangs = hangs;
	}

	public int getLies(){
		return lies;
	}

	public void setLies(int lies){
		this.lies = lies;
	}

	public String getPosaction(){
		return posaction;
	}

	public void setPosaction(String posaction){
		this.posaction = posaction;
	}

	public String getPosaction_ret(){
		return posaction_ret;
	}

	public void setPosaction_ret(String posaction_ret){
		this.posaction_ret = posaction_ret;
	}

	public String getZhuangt(){
		return zhuangt;
	}

	public void setZhuangt(String s){
		zhuangt = s;
	}

	public String getZhuangtxx(){
		return zhuangtxx;
	}

	public void setZhuangtxx(String zhuangtxx){
		this.zhuangtxx = zhuangtxx;
	}

	public String posfilehead(){
		char c1 = '\n';
		char c2 = '\003';
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append(posaction);
		stringbuffer.append(c2);
		stringbuffer.append(zhuangt);
		stringbuffer.append(c2);
		stringbuffer.append(zhuangtxx);
		stringbuffer.append(c2);
		stringbuffer.append(String.valueOf(hangs));
		stringbuffer.append(c2);
		stringbuffer.append(String.valueOf(lies));
		stringbuffer.append(c2);
		stringbuffer.append(posaction_ret);
		stringbuffer.append(c2);
		stringbuffer.append(c1);
		return stringbuffer.toString();
	}

}
