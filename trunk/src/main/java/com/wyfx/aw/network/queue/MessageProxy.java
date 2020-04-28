package com.wyfx.aw.network.queue;

import com.wyfx.aw.network.vo.Pcmd;
import java.util.concurrent.Callable;
import java.util.concurrent.BlockingQueue;

public class MessageProxy implements Callable<Message> {

    private Pcmd pcmd;
    private BlockingQueue<Message> receiveQueue=MessageQueue.receiveQueue;
    private int timeout=6*1000;

    public MessageProxy(Pcmd pcmd) {
        this.pcmd = pcmd;
    }

    @Override
    public Message call() throws Exception {
        long time=System.currentTimeMillis();
        Long finalTime=null;
        while (true){
            if(finalTime!=null && finalTime-time>timeout){
                throw new Exception("读取数据超时");
            }
            if(!receiveQueue.isEmpty()){
                Message message=receiveQueue.take();
                if(!message.getIdStr().equals(String.valueOf(pcmd.getIdst())) || message.getType()!=pcmd.getType() ){
                    receiveQueue.put(message);
                    continue;
                }
                return message;
            }
            finalTime=System.currentTimeMillis();
        }
    }

}
