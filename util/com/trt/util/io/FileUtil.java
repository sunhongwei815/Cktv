package com.trt.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sun.misc.BASE64Encoder;

public class FileUtil {
	public static TreeMode FileToTreeMode(File file){
		if(file==null) return null;
		TreeMode tm = new TreeMode();
		tm.setFilePath(file.getName());
		if(file.isDirectory()){			
			List<TreeMode> children = new ArrayList<TreeMode>();
			tm.setChildren(children);
			File[] files = file.listFiles();		
			for(int i = 0; i < files.length; i++){
				children.add(FileUtil.FileToTreeMode(files[i]));
			}
		}
		return tm;
	}
	/**
	 * 得到专家打印的图片格式化数据
	 * @param filePath 文件路径
	 * @return 不存在时返回默认图片
	 */
	public static String getPrintImageStr(String filePath) {
		InputStream in = null;
		byte[] data = null;
		try {
			in = new FileInputStream(filePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return "iVBORw0KGgoAAAANSUhEUgAAAT8AAAByCAYAAAAlF9atAAABj0lEQVR4nO3UMQEAIAzAMMCjiYKenXfbwHEnOkAgAnmBySZH5BkfkCS";
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}
}
