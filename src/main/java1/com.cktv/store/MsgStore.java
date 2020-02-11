package com.cktv.store;

import com.cktv.inter.Msg;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by 11835 on 2016/7/21.
 * msg阻塞队列
 */
public class MsgStore {

    private static Logger logger=Logger.getLogger(MsgStore.class);
    //每个设备设置一个Msg队列
    private static Map<String, LinkedBlockingQueue<Msg>> msgBlockingQueueMap = new HashMap<>();

    public static void addMsg(Msg msg) {
        logger.info("添加消息,消息类型:" + msg.getControl_type() + ",消息接收人:" + msg.getDevice_did());
        if (msgBlockingQueueMap.get(msg.getDevice_did())==null) {
            LinkedBlockingQueue<Msg> linkedBlockingQueue = new LinkedBlockingQueue<Msg>();
            linkedBlockingQueue.add(msg);
            msgBlockingQueueMap.put(msg.getDevice_did(), linkedBlockingQueue);
        } else {
            msgBlockingQueueMap.get(msg.getDevice_did()).add(msg);
        }
    }

    public static Msg takeMsg(String device_did) throws InterruptedException {
        if (msgBlockingQueueMap.get(device_did) == null) {
            msgBlockingQueueMap.put(device_did, new LinkedBlockingQueue<Msg>());
        }
        return msgBlockingQueueMap.get(device_did).take();
    }
}
