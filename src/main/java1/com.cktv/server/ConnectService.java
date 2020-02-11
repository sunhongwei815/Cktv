package com.cktv.server;

import com.alibaba.fastjson.JSONObject;
import com.cktv.domain.Device;
import com.cktv.inter.Msg;
import com.cktv.serviceManager.DeviceManager;
import com.cktv.serviceManagerImpl.DeviceManagerImpl;
import com.cktv.store.MsgStore;
import com.trt.util.bean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by 11835 on 2016/7/20.
 * 当服务器连接上客户端时的操作
 */
public class ConnectService extends Thread {
    private static Socket socket;
    private static String device_did;
    private static int status;//设备的现在状态
    private static CountDownLatch latch=new CountDownLatch(1);//线程锁
    private DeviceManager deviceManager;
   // public static Map<String, ConnectService>  connectServiceHashMap = new HashMap<>();

    public ConnectService(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        //不断的读取数据和发送数据
        new ReadClient().start();
        //new HeartBeat().start();  需要优化
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startListen();
    }
    public class ReadClient extends Thread{
        @Override
        public void run() {
            while (true) {
                InputStream inputStream = null;
                InputStreamReader isr = null;
                BufferedReader br = null;
                String message = null;
                try {
                    inputStream = socket.getInputStream();
                    isr = new InputStreamReader(inputStream);
                    br = new BufferedReader(isr);
                    message = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //将读取的信息转化为json数据
                JSONObject jsonObject = JSONObject.parseObject(message);
                device_did = jsonObject.getString("device_did");
                status = jsonObject.getInteger("run_status");
                System.out.println(device_did);
                System.out.println(status);
                latch.countDown();//设备count减一
                //保存设备状态。。可以优化，判断上次数据做出判断，不用每次查询数据库
                deviceManager=(DeviceManager) BeanUtil.load2("deviceManagerImpl");
                Device device = deviceManager.selectByPrimaryKey(device_did);
                if (device != null) {
                    if (device.getRun_status() != status) {
                        device.setRun_status(status);
                        deviceManager.updateByPrimaryKey(device);
                    }
                }
            }
        }
    }
    //开始监听
    private void startListen() {
        System.out.println("设备device_did="+device_did);
           while (!socket.isClosed()) {
               Msg msg = null;
               try {
                   msg = MsgStore.takeMsg(device_did);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
    transmitMessage(msg);
}
}

//发送消息
private void transmitMessage(Msg msg) {
        try {
        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.write((JSONObject.toJSONString(msg)).getBytes());
        dataOutputStream.write("\r\n".getBytes());
        dataOutputStream.flush();
        } catch (IOException e) {
        e.printStackTrace();
        }
        }


private class HeartBeat extends Thread {
    @Override
    public void run() {
        boolean stop = false;
        while (!stop) {
            try {
                socket.sendUrgentData(0xFF);
                Thread.sleep(1000);
            } catch (Exception ex) {
                ConnectService.this.interrupt();
                System.out.println(device_did+"断开连接");
                // connectServiceHashMap.remove(device_did);
                stop = true;
            }
        }
    }
}
}

