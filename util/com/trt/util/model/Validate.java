package com.trt.util.model;

public class Validate {
	private String msg;
	private boolean legal;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	public boolean isLegal() {
		return legal;
	}
	public void setLegal(boolean legal) {
		this.legal = legal;
	}
	public Validate(String msg, boolean legal) {
		super();
		this.msg = msg;
		this.legal = legal;
	}
	
	
}
