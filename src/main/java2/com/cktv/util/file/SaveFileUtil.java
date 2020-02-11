package com.cktv.util.file;

import com.cktv.util.exception.MessageException;

import java.io.*;

/**
 * Created by hws on 2016/5/11.
 */
public class SaveFileUtil {

    public static void saveHtml(String filePath,String content){
        //System.out.println(content);
        String newStr="";
        try {
             newStr = new String(content.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //System.out.println(newStr);
        File newsFileRoot = new File(filePath);
        if (!newsFileRoot.getParentFile().exists()) {
            newsFileRoot.getParentFile().mkdirs();
        }
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter osw=new OutputStreamWriter(fos,"UTF-8");
            osw.write(newStr);
            osw.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException("保存html出错！");
        }



    }

    public static void saveFile(String filePath, String content) {
        try {
            saveFile(filePath,content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new MessageException("不支持UTF-8编码");
        }
    }
    public static void saveFile(String newsRootPath, String filename, String content) {
        saveFile(newsRootPath+File.separator+filename,content);
    }
    public static void saveFile(String newsRootPath, String filename, byte[] contents) {
        saveFile(newsRootPath+File.separator+filename,contents);
    }
    public static void saveFile(String filePath, byte[] contents) {
        try {
            File newsFileRoot = new File(filePath);
            if (!newsFileRoot.getParentFile().exists()) {
                newsFileRoot.getParentFile().mkdirs();
            }
            try(FileOutputStream fos = new FileOutputStream(filePath)) {
                byte[] buf = new byte[1024];
                long len = 0;
                //html上传时使用&quot;代替",此处替换回去
                fos.write(contents);
                fos.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
