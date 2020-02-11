package com.cktv.util.string;

/**
 * Created by admin on 2016/4/18.
 */
public class StringUtil {
    private static String string="1234567890abcdefghijklmnopqlstuvwxyz";
    /**
     * 判断字符串是否为空
     * @param str
     * @return 是否为空
     * **/
    public static boolean isEmpty(String str){
        if(str == null || "".equals(str)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 如果字符串对象等于null转换非空""，否则不变
     * @param str
     * @return
     */
    public static String swapNull(String str){
        return str == null?"":str;
    }

    //随机生成一个16位的字符串
    public static String generateString(long length){
        String temp="";
        if(length!=0)
        {
            StringBuffer stringBuffer=new StringBuffer();
            int len=string.length();
            for(int i=0;i<length;i++){
                int rand=Math.round((float) Math.random()*len);
                if(rand==len){
                    rand=len-1;
                }
                stringBuffer.append(string.charAt(rand));
            }
            temp=stringBuffer.toString();
        }
        return temp;
    }
    /**
     * 去掉字符串的后两位，并且把它转化为整数
     * @param percent
     * @return
     */
    public static long removeLastTwoCharToInteger(String percent){
        int length=percent.length();
        String subString=percent.substring(0,length-2);
        System.out.println(subString);
        return Long.parseLong(subString.trim());
    }
    /**
     * 把以/Cktv/src开头的字符路径去掉/Cktv
     * @param /Cktv/src开头的路径
     * @return
     */
    public static String removeStringHeaderWithCktv(String string){
        return string.substring(5);
    }
    /**
     * 得到字符串中以“/”结尾的最后字符，如/Cktv/src/upload/video/b/b.mp4  得到b.mp4
     * @param /字符串
     * @return
     */
    public static String lastSubStringBySplit(String string){
        String []subString=string.split("/");
        return subString[subString.length-1];
    }
    /**
     * 去掉字符串的第一个字符
     * @param /字符串
     * @return
     */
    public static String removeFirstChar(String string){
        return string.substring(1);
    }
}

