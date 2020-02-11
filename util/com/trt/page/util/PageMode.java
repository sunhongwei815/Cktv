package com.trt.page.util;

import java.util.List;

public class PageMode<T>{
	private long totalProperty;
	private List<T> result;
	public long getTotalProperty() {
		return totalProperty;
	}
	public void setTotalProperty(long totalProperty) {
		this.totalProperty = totalProperty;
	}
	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}
	
}
