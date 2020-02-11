package com.test.phone;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;

/**
 * Created by 11835 on 2016/7/21.
 */
public class TestSocket {

    public static void main(String[] args) {
        InputStream inputStream=null;
        BufferedReader br=null;
        try {
            final Socket socket = new Socket("127.0.0.1", 9999);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try {
                            OutputStream outputStream=null;
                            DataOutputStream dataOutputStream=null;
                            Thread.sleep(10000);
                            outputStream=socket.getOutputStream();
                            dataOutputStream=new DataOutputStream(outputStream);
                            JSONObject data = new JSONObject();
                            String device_did="CKDZ_484561f0918e96b7";
                            data.put("device_did",device_did);
                            data.put("run_status",2);
                            dataOutputStream.write((data.toJSONString()).getBytes());
                            dataOutputStream.write("\r\n".getBytes());
                            dataOutputStream.flush();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            while (true) {
                inputStream = socket.getInputStream();
                br = new BufferedReader(new InputStreamReader(inputStream));
                String msg = br.readLine();
                JSONObject jsonObject = JSON.parseObject(msg);
                String device_did = jsonObject.getString("device_did");
                String control_type = jsonObject.getString("control_type");
                System.out.println(device_did);
                System.out.println(control_type);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
