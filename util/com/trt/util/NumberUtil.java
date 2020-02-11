package com.trt.util;

import java.util.Random;

public class NumberUtil {
	
	/**
	 * 生成随机数
	 * @param 随机数长度
	 * @return 随机数
	 */
	public static int createRandomNo(int length){
		int[] array = {0,1,2,3,4,5,6,7,8,9};
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		int result = 0;
		for(int i = 0; i < length; i++){
			result = result * 10 + array[i];
		}
		System.out.println(result);
		return result;
	}
}
