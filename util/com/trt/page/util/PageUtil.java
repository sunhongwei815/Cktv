package com.trt.page.util;

import com.trt.util.array.ArrayUtil;

public class PageUtil {

	public static int[] page(int pageNow,int pageSzie,int pageCount){
		int[] showItems = new int[pageCount];//显示数组0不显示，1显示白色，2表示选中，3表示...
		if(pageCount<=7){
			ArrayUtil.assignValue(showItems, 0, pageCount, 1);
		}else if(pageNow<6){
			ArrayUtil.assignValue(showItems, 0, 6, 1);
			showItems[6]= 3;
		}else if(pageNow==6){
			ArrayUtil.assignValue(showItems, 0, 7, 1);
			showItems[7]= 3;
		}else if(pageNow>pageCount-5){
			ArrayUtil.assignValue(showItems, pageCount-6, 6, 1);
			showItems[pageCount-7]= 3;
		}else if(pageNow==pageCount-5){
			ArrayUtil.assignValue(showItems, pageCount-7, 7, 1);
			showItems[pageCount-8]= 3;
		}else{
			ArrayUtil.assignValue(showItems, pageNow-3, 6, 1);
			showItems[pageNow-4]= 3;
			showItems[pageNow+3]= 3;
		}
		showItems[pageCount-1] = 1;
		showItems[0] = 1;
		showItems[pageNow-1] = 2;
		return showItems;
	}

}
