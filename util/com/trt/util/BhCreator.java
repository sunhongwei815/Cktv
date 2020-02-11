package com.trt.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BhCreator {
	public  static String createByDateAndSeq(String sequenceStr){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String date=df.format(new Date());
		StringBuffer bh=new StringBuffer(date);
		for(int i=0;i<5-sequenceStr.length();i++){
			bh.append("0");
		}
		bh.append(sequenceStr);
		return bh.toString();
	}
}
