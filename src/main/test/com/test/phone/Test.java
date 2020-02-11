package com.test.phone;

import java.io.*;

/**
 * Created by linpeng123l on 2016/6/21.
 * lp
 */
public class Test {

    public static void main(String[] args) throws IOException {
//        String str = "PQWIVLQQ";
//        System.out.println(rotateString(str, 8, 5));
        File file = new File("/Users/hws/Desktop/test.txt");
//        Writer
        /*System.out.println(file.getPath());
        System.out.println(file.getAbsolutePath());
        file.listFiles();
        try (InputStream inputStream = new FileInputStream("/Users/guest/test.txt")) {
            int len;
            byte[] bs = new byte[1024];
            while ((len = inputStream.read(bs)) != -1) {
                System.out.println(new String(bs, 0, len));
            }
        }
        try (OutputStream outputStream = new FileOutputStream("/Users/guest/test.txt")) {
            outputStream.write("hello world".getBytes());
            outputStream.flush();
        }
        try(InputStream inputStream = new ByteArrayInputStream("hello world".getBytes())){
            int len;
            byte[] bs = new byte[1024];
            while ((len = inputStream.read(bs)) != -1) {
                System.out.println(new String(bs, 0, len));
            }
        }*/
        /*try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
            byteArrayOutputStream.write("hello world".getBytes());
            byte[] bs = byteArrayOutputStream.toByteArray();
            System.out.println(new String(bs));
        }*/

        /*try(InputStream inputStream = new BufferedInputStream(new FileInputStream("/Users/guest/test.txt"))){
            int len;
            byte[] bs = new byte[1024];
            while ((len = inputStream.read(bs)) != -1) {
                System.out.println(new String(bs, 0, len));
            }
        }*/
      /*  file.createNewFile();
        try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream("/Users/hws/Desktop/test.txt"))) {
            outputStream.write("hello world".getBytes());
            outputStream.flush();
        }*/
        /*try (Reader reader = new FileReader("/Users/hws/Desktop/test.txt")) {
            int len;
            char[] chs = new char[1024];
            while ((len = reader.read(chs)) != -1) {
                System.out.println(new String(chs, 0, len));
            }
        }*/
        /*try (Writer writer = new FileWriter("/Users/hws/Desktop/test.txt")) {
            writer.write("hello world".toCharArray());
            writer.flush();
        }*/
        /*try(Reader reader = new CharArrayReader("hello world".toCharArray())){
            int len;
            char[] chs = new char[1024];
            while ((len = reader.read(chs)) != -1) {
                System.out.println(new String(chs, 0, len));
            }
        }*/
        /*try(CharArrayWriter charArrayWriter = new CharArrayWriter()){
            charArrayWriter.write("hello world".toCharArray());
            char[] chs = charArrayWriter.toCharArray();
            System.out.println(new String(chs));
        }*/
        /*try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/hws/Desktop/test.txt"),"UTF-8"))) {
            String str;
            while ((str = bufferedReader.readLine())!=null){
                System.out.println(str);
            }
        }*//*
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/hws/Desktop/test.txt"),"UTF-8"))) {
            bufferedWriter.write("hello world");
            bufferedWriter.flush();
        }*/
        /*try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("/Users/hws/Desktop/test.txt"),"GBK")) {
            int len;
            char[] chs = new char[1024];
            while ((len = inputStreamReader.read(chs)) != -1) {
                System.out.println(new String(chs, 0, len));
            }
        }*/
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream("/Users/hws/Desktop/test.txt"),"GBK")) {
            outputStreamWriter.write("hello world");
            outputStreamWriter.flush();
        }
    }
    
  /*  public static String rotateString(String A, int n, int p) {
        char[] nums = A.toCharArray();
        char[] temps = new char[n - p - 1];
        for (int i = 0; i < n - p - 1; i++) {
            for(int j=n-i;j>0;j--){

            }
        }
        for (int i = 0; i < p + 1; i++) {
            nums[n - i - 1] = nums[p - i];
        }
        for (int i = 0; i < n - p - 1; i++) {
            nums[i] = temps[i];
        }
        return new String(nums);
    }*/

    /*public static String rotateString(String A, int n, int p) {
        char[] nums = A.toCharArray();
        char[] temps = new char[n - p - 1];
        for (int i = 0; i < n - p - 1; i++) {
            temps[i] = nums[p + i + 1];
        }
        for (int i = 0; i < p + 1; i++) {
            nums[n - i - 1] = nums[p - i];
        }
        for (int i = 0; i < n - p - 1; i++) {
            nums[i] = temps[i];
        }
        return new String(nums);
    }*/
    public static String rotateString(String A, int n, int p) {
        char[] nums = A.toCharArray();
        char temp = nums[0];
        int start = 0, begin = 0;
        int dif = n - p - 1;
        for (int i = 0; i < n; i++) {
            if (start >= dif) {
                if (start - dif == begin) {
                    nums[start] = temp;
                } else {
                    nums[start] = nums[start - dif];
                }
                start = start - dif;
                if (start == begin) {
                    start++;
                    begin++;
                    temp = nums[begin];
                }
            } else {
                nums[start] = nums[n + start - dif];
                start = n + start - dif;
            }
        }
        return new String(nums);
    }
}
