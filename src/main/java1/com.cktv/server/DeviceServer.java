package com.cktv.server;

import com.cktv.config.SocketPort;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 11835 on 2016/7/20.
 * 用于控制设备的开关机
 */
public class DeviceServer extends Thread{
    @Override
    public void run() {
        try {
            ServerSocket serverSocket=new ServerSocket(SocketPort.port);
            while (true) {
                Socket socket = serverSocket.accept();
                ConnectService connectService = new ConnectService(socket);
                connectService.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
