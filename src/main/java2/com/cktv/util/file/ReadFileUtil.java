package com.cktv.util.file;

import com.cktv.util.exception.MessageException;

import java.io.*;

/**
 * Created by hws on 16/7/15.
 */
public class ReadFileUtil {

    public static String readStrFile(String filePath){
        return readStrFile(new File(filePath));
    }

    public static String readStrFile(File filePath){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            StringBuilder retStr = new StringBuilder("");
            String str;
            while((str = bufferedReader.readLine())!=null){
               // retStr.append(str).append("/r/n");
                retStr.append(str);
            }
            return retStr.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new MessageException("文件不存在");
        }
    }

}
