package com.wyfx.aw.ws;

import com.wyfx.aw.ws.log.LogRead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value = "/log/{sid}")
public class WebSocketServer {

    private static Logger logger=LoggerFactory.getLogger(WebSocketServer.class);
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据 control
    private Session session;
    private static String[] flags={"web","sCtrl","sFlow"};

    private String sid;
    private static final String userHome=System.getProperty("user.home");


    @OnOpen
    public void onOpen(Session session,@PathParam("sid") String sid) {
        String flagStr= Arrays.toString(flags);
        if(flagStr.contains(sid)){
            this.session = session;
            this.sid=sid;
            webSocketSet.add(this);     //加入set中
            new LogRead(session).readFile(getFilePath(null));
        }else {
            close(session);
        }
    }

    /**
     * 获取日志文件的全路径
     * @return
     */
    public String getFilePath(String date){
        StringBuffer stringBuffer=new StringBuffer(userHome);
        stringBuffer.append("/logs/aw/");
        if(date==null|| date.equals("")){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.format(new Date());
        }
        stringBuffer.append(date+".log");
        logger.info("logFile path:"+stringBuffer.toString());
        return stringBuffer.toString();
    }

    @OnError
    public void onError(Session session, Throwable t) {
        logger.error("连接异常",t);
        close(session);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason){
        close(session);
        logger.info("连接关闭",reason);
    }

    public void close(Session session){
        try {
            webSocketSet.remove(this);  //从set中删除
            session.close();//关闭webSocket连接
            logger.info("webSocket连接关闭："+session.getId());
        }catch (Exception e){
            logger.error("webSocket关闭异常");
        }
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 自定义消息消息发送
     */
    public static void sendInfo(String message,@PathParam("sid") String sid) throws IOException {
        logger.info("推送消息到窗口"+sid+"，推送内容:"+message);
        for (WebSocketServer item : webSocketSet) {
            try {
                if(sid==null) {
                    item.sendMessage(message);
                }else if(item.sid.equals(sid)){
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

}
