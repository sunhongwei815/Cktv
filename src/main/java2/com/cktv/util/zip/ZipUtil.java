package com.cktv.util.zip;

/**
 * Created by zhangyp on 2016/5/8 0008.
 */

import java.io.*;
import java.util.zip.*;
/**
 * 对zip文件进行解压
 */
public class ZipUtil {
    public static void main(String[] args) {
        unzip("E:\\test\\test1.zip", "E:\\test");
    }

    /**
     * @param zipFileName 压缩后zipFileName.zip
     * @param inputFile   需要压缩的文件
     * @throws Exception
     */
    private void zip(String zipFileName, File inputFile) throws Exception {
        System.out.println("压缩中...");
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(
                zipFileName));
        BufferedOutputStream bos = new BufferedOutputStream(zos);
        zip(zos, inputFile, inputFile.getName(), bos);
        bos.close();
        zos.close(); // 输出流关闭
        System.out.println("压缩完成");
    }

    private void zip(ZipOutputStream out, File f, String base,
                     BufferedOutputStream bo) throws Exception { // 方法重载
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            if (fl.length == 0) {
                out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base
                System.out.println(base + "/");
            }
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + "/" + fl[i].getName(), bo); // 递归遍历子文件夹
            }
        } else {
            out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
            System.out.println(base);
            FileInputStream in = new FileInputStream(f);
            BufferedInputStream bi = new BufferedInputStream(in);
            int b;
            while ((b = bi.read()) != -1) {
                bo.write(b); // 将字节流写入当前zip目录
            }
            bi.close();
            in.close(); // 输入流关闭
        }
    }

    /**
     * 解压~但是文件名字是中文的时候会有错误
     */
    public static void unzip(String inpath, String outpath) {
        long startTime = System.currentTimeMillis();
        File zipfile = new File(inpath);
        try {
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipfile)));//输入源zip路径
            BufferedInputStream bis = new BufferedInputStream(zis);
            String Parent = outpath; //输出路径（文件夹目录）
            File Fout = null;
            ZipEntry entry = null;
            try {
                while ((entry = zis.getNextEntry()) != null && !entry.isDirectory()) {
                    Fout = new File(Parent, entry.getName());
                    if (!Fout.exists()) {
                        (new File(Fout.getParent())).mkdirs();
                    }
                    FileOutputStream out = new FileOutputStream(Fout);
                    BufferedOutputStream bos = new BufferedOutputStream(out);
                    int b;
                    while ((b = bis.read()) != -1) {
                        bos.write(b);
                    }
                    bos.close();
                    out.close();
                    System.out.println(Fout + "解压成功");
                }
                bis.close();
                zis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗费时间： " + (endTime - startTime) + " ms");
    }

    //压缩文件上传后解压
    public static void unzip(File file, String outpath) {
//        InputStream in = null;
//        OutputStream out = null;
//            ZipFile zipFile=new ZipFile(file);
//            for(Enumeration entries=zipFile.entries();entries.hasMoreElements();){
//                ZipEntry entry=(ZipEntry) entries.nextElement();
//                //具体的文件的文件名
//                String zipEntryName=entry.getName();
//                in=zipFile.getInputStream(entry);
//                //具体的文件的路径
//                String entryFilePath=outpath+zipEntryName;
//                File entryDirFile=new File(entryFilePath);
//                //判断是文件是否存在还是文件是文件夹
//                if(entryDirFile.exists()){
//                    entryDirFile.delete();
//                }else if(entryFilePath.lastIndexOf(".")==-1){
//                    entryDirFile.mkdirs();
//                    continue;
//                }else{
//                    out=new FileOutputStream(new File(entryDirFile.getAbsolutePath()));
//                    byte[] dates=new  byte[2048];
//                    long len=0;
//                    while((len=in.read(dates))!=-1){
//                        out.write(dates,0,len);
//                    }
//                    out.flush();
        try {
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(file)));//输入源zip路径
            BufferedInputStream bis = new BufferedInputStream(zis);
            String Parent = outpath; //输出路径（文件夹目录）
            File Fout = null;
            ZipEntry entry = null;
            while ((entry = zis.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    Fout = new File(Parent, entry.getName());
                    if (!Fout.exists()) {
                        (new File(Fout.getParent())).mkdirs();
                    }
                    FileOutputStream out = new FileOutputStream(Fout);
                    BufferedOutputStream bos = new BufferedOutputStream(out);
                    int b;
                    while ((b = bis.read()) != -1) {
                        bos.write(b);
                    }
                    bos.close();
                    out.close();
                } else {
                    outpath = outpath + "/" + entry.getName();
                    file = new File("");
                    unzip(file, outpath);
                }
                System.out.println(Fout + "解压成功");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}