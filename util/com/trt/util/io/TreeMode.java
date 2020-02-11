package com.trt.util.io;

import java.util.List;

public class TreeMode{	
	private String filePath;
	private List<TreeMode> children;

	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public List<TreeMode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeMode> children) {
		this.children = children;
	}
	
}
