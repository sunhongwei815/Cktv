package com.trt.util.user;

import java.security.MessageDigest;

public class PassWordUtil {

	public static String encrypt(String str) throws RuntimeException{
		str = reverseStr(str);
		str = md5(str);
		str = sort(str);
		str = md5(str);
		return str;
	}
	
	private static String sort(String str){
		byte[] bs = str.getBytes();
		byte[] retBs = new byte[bs.length];
		for(int i = 0;i<bs.length;i++){
			byte b = bs[i];
			retBs[i] = (byte) (i+b+'l'+'p');
		}
		return new String(retBs);
	}
	
	private static String reverseStr(String str) throws RuntimeException{
		byte[] bs = str.getBytes();
		byte[] retBs = new byte[bs.length];
		for(int i = 0;i<bs.length;i++){
			byte b = bs[(i+'l')%(bs.length-i)];
			bs[(i+'l')%(bs.length-i)] = bs[bs.length-i-1];
			retBs[i] = b;
		}
		return new String(retBs);
	}
	
	public static String md5(String str) throws RuntimeException{
		try{
	        byte [] buf = str.getBytes();
	        MessageDigest md5 = MessageDigest.getInstance("MD5");
	        md5.update(buf);
	        byte [] tmp = md5.digest();
	        StringBuilder encryptStr = new StringBuilder();
	        for (byte b:tmp) {
	        	encryptStr.append(Integer.toHexString(b&0xff));
	        }
			return encryptStr.toString();
		}catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
	}
}
