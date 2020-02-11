package com.test.phone;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cktv.inter.Msg;
import org.junit.Test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

class TestDevice {
    @Test
    public void connectServer() {
        try {
            Socket socket = new Socket("127.0.0.1", 9999);
            OutputStream outputStream=socket.getOutputStream();
            DataOutputStream dataOutputStream=new DataOutputStream(outputStream);
            Msg msg=new Msg();
            msg.setControl_type("power");
            msg.setDevice_did("112ssserrr");
            dataOutputStream.write(JSONObject.toJSONString(msg).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}