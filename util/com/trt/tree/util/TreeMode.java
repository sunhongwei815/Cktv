package com.trt.tree.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TreeMode implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String text;
	private long id;
	private long pid;
	private String url;
	private boolean leaf;
	private long restypeid;
	private String sjname;
	private Object object;
	private List<TreeMode> children;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setSjname(String sjname) {
		this.sjname = sjname;
	}
	public String getSjname() {
		return sjname;
	}
	public List<TreeMode> getChildren() {
		return children;
	}
	public void setChildren(List<TreeMode> children) {
		this.children = children;
	}
	public void addChild(TreeMode child) {
		if(children==null){
			children = new ArrayList<TreeMode>();
		}
		children.add(child);
	}

	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public long getRestypeid() {
		return restypeid;
	}
	public void setRestypeid(long restypeid) {
		this.restypeid = restypeid;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	@Override
	public String toString() {
		return "TreeMode [text=" + text + ", id=" + id + ", pid=" + pid
				+ ", url=" + url + ", leaf=" + leaf + ", restypeid="
				+ restypeid + ", sjname=" + sjname + ", object=" + object
				+ ", children=" + children + "]";
	}
}
