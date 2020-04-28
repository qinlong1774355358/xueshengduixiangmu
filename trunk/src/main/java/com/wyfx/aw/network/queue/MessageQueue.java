package com.wyfx.aw.network.queue;

import com.wyfx.aw.network.vo.Pcmd;
import com.wyfx.aw.network.vo.TcpAddress;
import io.netty.channel.Channel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
    /**
     * 接收消息队列
     */
    public static final BlockingQueue<Message> receiveQueue = new LinkedBlockingQueue<Message>();

    /**
     * 根据文件名称保存实时读取日志文件的任务线程
     */
    public static final Map<String,Runnable> lastLogReadThreadlMap=new ConcurrentHashMap<>();

    /**
     * 保存活动的tcp连接
     */
    public static final Map<String,Channel> channelMap=new ConcurrentHashMap<>();

    /**
     * 保存等待连接的host跟port
     */
    public static final List<TcpAddress> waitConnList= Collections.synchronizedList(new ArrayList<TcpAddress>());

    /**
     * 发送消息
     * @param serverId
     * @param pcmd
     */
    public static void sendMessage(long serverId,Pcmd pcmd) throws Exception {
        System.out.println(serverId);
        System.out.println(pcmd);
        System.out.println(MessageQueue.channelMap);
        System.out.println(MessageQueue.channelMap.get(String.valueOf(serverId)));
        Channel channel= MessageQueue.channelMap.get(String.valueOf(serverId));
        System.out.println(channel);
        if(channel!=null){
            channel.writeAndFlush(pcmd);
            System.out.println("发送消息:"+pcmd);
            System.out.println(receiveQueue);
        }else {
            throw new Exception("设备未连接");
        }
    }

    /**
     * 回调获取接收到的数据
     * @param pcmd
     * @return
     */
    public static Message getMessage(Pcmd pcmd){
        Message message=null;
        FutureTask<Message> task = new FutureTask<Message>(new MessageProxy(pcmd));
        new Thread(task).start();
        try {
            message=task.get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return message;
    }
}
