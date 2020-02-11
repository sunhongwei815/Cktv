package com.cktv.util.zip;

import com.cktv.util.exception.MessageException;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.*;

public class ZipUtils {
  
    public static final String EXT = ".zip";  
    private static final String BASE_DIR = "";  
    private static final String PATH = File.separator;
    private static final int BUFFER = 1024;
    private static Logger logger = Logger.getLogger(ZipUtils.class);
  
    /** 
     * 文件 解压缩 
     *  
     * @param srcPath 
     *            源文件路径 
     *  
     * @throws Exception 
     */  
    public static void unzip(String srcPath) throws Exception {  
        File srcFile = new File(srcPath);  
  
        unzip(srcFile);  
    }  
  
    /** 
     * 解压缩 
     *  
     * @param srcFile 
     * @throws Exception 
     */  
    public static void unzip(File srcFile) throws Exception {  
        String basePath = srcFile.getParent();  
        unzip(srcFile, basePath);  
    }  
  
    /** 
     * 解压缩 
     *  
     * @param srcFile 
     * @param destFile 
     * @throws Exception 
     */  
    public static void unzip(File srcFile, File destFile) throws Exception {  
  
        CheckedInputStream cis = new CheckedInputStream(new FileInputStream(
                srcFile), new CRC32());
  
        ZipInputStream zis = new ZipInputStream(cis);
  
        unzip(destFile, zis);  
  
        zis.close();  
  
    }  
  
    /** 
     * 解压缩 
     *  
     * @param srcFile 
     * @param destPath 
     * @throws Exception 
     */  
    public static void unzip(File srcFile, String destPath)  
            throws Exception {  
        unzip(srcFile, new File(destPath));  
  
    }  
  
    /** 
     * 文件 解压缩 
     *  
     * @param srcPath 
     *            源文件路径 
     * @param destPath 
     *            目标文件路径 
     * @throws Exception 
     */  
    public static void unzip(String srcPath, String destPath)  
            throws Exception {  
  
        File srcFile = new File(srcPath);  
        unzip(srcFile, destPath);  
    }  
  
    /** 
     * 文件 解压缩 
     *  
     * @param destFile 
     *            目标文件 
     * @param zis 
     *            ZipInputStream 
     * @throws Exception 
     */  
    private static void unzip(File destFile, ZipInputStream zis)  
            throws Exception {  
  
        ZipEntry entry = null;
        while ((entry = zis.getNextEntry()) != null) {  
  
            // 文件  
            String dir = destFile.getPath() + File.separator + entry.getName();  
  
            File dirFile = new File(dir);  
  
            // 文件检查  
            fileProber(dirFile);  
  
            if (entry.isDirectory()) {  
                dirFile.mkdirs();  
            } else {  
                unzipFile(dirFile, zis);  
            }  
  
            zis.closeEntry();  
        }  
    }  
  
    /** 
     * 文件探针 
     *  
     *  
     * 当父目录不存在时，创建目录！ 
     *  
     *  
     * @param dirFile 
     */  
    private static void fileProber(File dirFile) {  
  
        File parentFile = dirFile.getParentFile();  
        if (!parentFile.exists()) {  
  
            // 递归寻找上级目录  
            fileProber(parentFile);  
  
            parentFile.mkdir();  
        }  
  
    }  
  
    /** 
     * 文件解压缩 
     *  
     * @param destFile 
     *            目标文件 
     * @param zis 
     *            ZipInputStream 
     * @throws Exception 
     */  
    private static void unzipFile(File destFile, ZipInputStream zis)  
            throws Exception {  
  
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(destFile));
  
        int count;
        byte data[] = new byte[BUFFER];  
        while ((count = zis.read(data, 0, BUFFER)) != -1) {  
            bos.write(data, 0, count);  
        }  
  
        bos.close();  
    }
    /**
     * 实现将多个文件进行压缩，生成指定目录下的指定名字的压缩文件
     *
     * @param filename
     *            压缩文件的名称
     * @param temp_path
     *            指定生成的压缩文件所存放的目录
     * @param folder
     *            folder :需要压缩的文件夹路径
     * @throws Exception
     *
     */
    public static void zipfiles(String filename, String temp_path, String folder) {
        File file = new File(folder);
        System.out.println("存放压缩文件的路径为"+temp_path);
        System.out.println("要压缩的文件路径为"+file);
        if(!file.exists()){
            throw new MessageException("文件不存在！");
        }
        File file1=new File(temp_path);
        if(!file1.exists()){
            file1.mkdirs();
        }
        File[] files = file.listFiles();
        String zipAdress = temp_path + filename;
        File zipFile = new File(zipAdress);
        InputStream input = null;
        try (FileOutputStream outputStream = new FileOutputStream(zipFile)) {
            CheckedOutputStream cos = new CheckedOutputStream(outputStream, new CRC32());
            ZipOutputStream zipOut = new ZipOutputStream(cos);
            zipOut.setComment(file.getName());
            String basedir = "";
            for (int i = 0; i < files.length; i++) {
                compressByType(files[i], zipOut, basedir);
            }
            zipOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new MessageException("压缩出错");
        }
    }
    /**
     * 实现将多个文件进行压缩，生成指定目录下的指定名字的压缩文件
     *
     * @param file
     *            需要压缩的文件（可以是文件或文件夹）
     * @param out
     *            ZipOutputStream
     * @param basedir
     *            压缩文件里的路径
     * @throws Exception
     *
     *
     */
    private static void compressByType(File file, ZipOutputStream out, String basedir) throws Exception {
        if (file.isDirectory()) {
            compressDirectory(file, out, basedir);
        } else {
            logger.info("压缩：" + basedir + file.getName());
            compressFile(file, out, basedir);
        }
    }
    /**
     * 压缩文件夹
     *
     * @param dir
     *            需要压缩的文件夹
     * @param out
     *            ZipOutputStream
     * @param basedir
     *            压缩文件里的路径
     *
     *
     */
    private static void compressDirectory(File dir, ZipOutputStream out, String basedir) throws Exception {
        if (!dir.exists()) {
            return;
        }
        File[] files = dir.listFiles();
        if (files.length == 0) {
            compressEmptyFolder(dir, out, basedir);
        } else {
            for (int i = 0; i < files.length; i++) {
                compressByType(files[i], out, basedir + dir.getName() + File.separator);
            }
        }
    }
    /**
     * 压缩文件
     *
     * @param file
     *            需要压缩的文件
     * @param out
     *            ZipOutputStream
     * @param basedir
     *            压缩文件里的路径
     * @throws Exception
     *
     *
     */
    private static void compressFile(File file, ZipOutputStream out, String basedir) throws Exception {
        if (!file.exists()) {
            return;
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(basedir + file.getName());
            out.putNextEntry(entry);
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = bis.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 压缩空的文件夹
     *
     * @param file
     *            需要压缩的空文件夹
     * @param out
     *            ZipOutputStream
     * @param basedir
     *            压缩文件里的路径
     * @throws Exception
     *
     *
     */
    private static void compressEmptyFolder(File file, ZipOutputStream out, String basedir) throws Exception {
        if (!file.exists()) {
            return;
        }
        ZipEntry entry = new ZipEntry(basedir + file.getName() + File.separator);
        out.putNextEntry(entry);
    }
}  