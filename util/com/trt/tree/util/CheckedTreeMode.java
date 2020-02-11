package com.trt.tree.util;

import java.io.Serializable;

public class CheckedTreeMode extends TreeMode implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Boolean checked;
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
}
