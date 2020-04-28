package com.wyfx.aw.ws.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.websocket.Session;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 日志信息实时采集
 */
public class LogRead {
    private static Logger logger=LoggerFactory.getLogger(LogRead.class);
    ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
    ScheduledFuture scheduledFuture=null;
    /**
     * 指针：记录上次文件读取位置
     */
    private long pointer;
    private Session session;
    public LogRead(Session session){
        this.session=session;
    }
    /**
     *
     * @param fileName 文件全路径
     */
    public void readFile(String fileName){
        try {
            File logFile=new File(fileName);
            scheduledFuture= exec.scheduleWithFixedDelay(new Runnable(){
                @Override
                public void run() {
                    try {
                        long len=logFile.length();
                        if(len<pointer){
                            pointer=0;
                        }else {
                            RandomAccessFile randomAccessFile=new RandomAccessFile(logFile,"rw");
                            randomAccessFile.seek(pointer);//移动文件指针位置
                            String tem=null;
                            while((tem=randomAccessFile.readLine())!=null){
                                if(!session.isOpen()){
                                    scheduledFuture.cancel(true);//取消定时任务
                                    break;
                                }
                                session.getBasicRemote().sendText(tem+"<br>");//发送数据到前端
                                pointer=randomAccessFile.getFilePointer();
                            }
                            randomAccessFile.close();
                        }
                    }catch (Exception e){
                        logger.error("读取日志文件异常",e);
                        scheduledFuture.cancel(true);
                    }
                }
            }, 0, 10, TimeUnit.SECONDS);
        }catch (NullPointerException e){
            logger.error("This file does not exist",e);
            scheduledFuture.cancel(true);
        }finally {
            if(scheduledFuture.isCancelled()){
                exec.shutdown();
            }
        }
    }
}
