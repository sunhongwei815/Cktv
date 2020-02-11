package com.trt.tree.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//modified:增加一个深度复制方法。
public class TreeModeStore implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//所有的treeMode数据
	public List<TreeMode> treeModes = new ArrayList<TreeMode>();
	Map<Long, TreeMode> treeModeMap = new HashMap<Long, TreeMode>();
	boolean handleFlag = false;	//处理标识：标识是否已处理过treeModes的父子关系

	//同时向treeModes和treeModeMap添加一个treeMode
	public void addTreeMode(TreeMode treeMode){
		treeModes.add(treeMode);
		treeModeMap.put(treeMode.getId(), treeMode);
		handleFlag = false;	//修改过treeModes后需要重新处理其父子关系
	}
	
	//处理treeModes的父子关系
	public void handleTreeModes() {
		//先判断treeModes是否被初始化
		if(treeModes == null || treeModes.size() == 0){
			throw new RuntimeException("请传入TreeMode的list集合");
		}
		//存储新建的treeMode
		List<TreeMode> tempTreeModes = new ArrayList<TreeMode>();
		for(TreeMode treeMode : treeModes){
			//考虑到根节点的情况
			if(treeMode.getPid() == -1){
				continue;
			}
			
			//如果没有相应的父TreeMode,则创建
			if(treeModeMap.get(treeMode.getPid()) == null){
				TreeMode pTreeMode = new TreeMode();
				pTreeMode.setId(treeMode.getPid());
				pTreeMode.setPid(-1);
				treeModeMap.put(pTreeMode.getId(), pTreeMode);
				tempTreeModes.add(pTreeMode);
			}
			//添加相应的子TreeMode
			TreeMode treePMode = treeModeMap.get(treeMode.getPid());
			treePMode.addChild(treeMode);
			treePMode.setLeaf(false);
		}
		//添加新增的TreeMode集合
		treeModes.addAll(tempTreeModes);
		//已处理
		handleFlag = true;
		
	}
	//判断是否已处理好treeModes的父子关系
	public void judgeHandled() {
		if(handleFlag)
			return;
		else 
			this.handleTreeModes();
	}
	
	/**
	 * 通过父树id获取子树TreeMode集合
	 * @param fiddlerPTreeid
	 * @return List<TreeMode>
	 * @
	 */
	public List<TreeMode> getChildrenByPTreeId(long fiddlerPTreeid) {
		judgeHandled();
		for(TreeMode treeMode : treeModes){
			if(treeMode.getId()==fiddlerPTreeid){
				return treeMode.getChildren();
			}
		}
		return null;
	}
	
	/**
	 * 通过treeid获取对应的TreeMode
	 * @param treeid
	 * @return TreeMode
	 * @
	 */
	public TreeMode getTreeModeByTreeId(long treeid) {
		judgeHandled();
		for(TreeMode treeMode : treeModes){
			if(treeMode.getId()==treeid){
				return treeMode;
			}
		}
		return null;
	}
	
	//返回处理好父子关系的TreeModes
	public List<TreeMode> getHandledTreeModes() {
		judgeHandled();
		return treeModes;
	}
	
	//深度复制
	public Object deepClone() {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(this);
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bis);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("深度复制出错："+e.getMessage(),e);
		}

	}
}
