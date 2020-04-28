package com.wyfx.aw.network;

import com.sun.xml.internal.ws.resources.HttpserverMessages;
import com.wyfx.aw.entity.ServerInfo;
import com.wyfx.aw.network.queue.Message;
import com.wyfx.aw.network.queue.MessageQueue;
import com.wyfx.aw.network.vo.Pcmd;
import com.wyfx.aw.service.ServerInfoService;
import com.wyfx.aw.utils.ApplicationContextRegister;
import com.wyfx.aw.network.vo.CmdUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class DispatcherHandler extends SimpleChannelInboundHandler<Pcmd> {
    private static Logger logger=LoggerFactory.getLogger(DispatcherHandler.class);
    private static int serverid = 1;
    // 业务逻辑线程池(业务逻辑最好跟netty io线程分开处理，线程切换虽会带来一定的性能损耗，但可以防止业务逻辑阻塞io线程)
    private final static ExecutorService workerThreadService = newBlockingExecutorsUseCallerRun(Runtime.getRuntime().availableProcessors() * 2);

    private ServerInfoService serverInfoService= ApplicationContextRegister.getApplicationContext().getBean(ServerInfoService.class);
    private Integer serverInfoId;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Pcmd pcmd) throws Exception {
        //将需要实时显示的数据投放到接收队列，其他的业务交由业务线程完成
        logger.info("接收到消息:"+pcmd.toString());
        switch (pcmd.getType()){
             //关闭蜜罐web服务
            case CmdUtil.STOP_WEB_SERVER:
                Message message=new Message(String.valueOf(pcmd.getIdst()),pcmd.getType(),pcmd);
                MessageQueue.receiveQueue.put(message);
                System.out.println("客户端接收到消息并保存到消息队列中");
                break;
            default:
                // 使用自定义业务线程处理复杂的业务逻辑，不会影响netty io线程
                workerThreadService.execute(new Runnable() {
                    @Override
                    public void run() {
                        dispatcher(pcmd);//将pcmd的业务逻辑处理交由子线程分发处理
                    }
                });
                break;
        }
        ReferenceCountUtil.release(pcmd);//此处必须显示的释放引用计数器，否则在使用LengthFieldBasedFrameDecoder的同时，发送方发生了粘包，此时，将只能解析第一个包，后续的包将不能在处理器中执行

        /*if(channelHandlerContext.executor().inEventLoop()){    //如果当前线程就是业务线程ctx.executor(),执行任务
            dispatcher(pcmd);
        }else{
            channelHandlerContext.executor().execute(new Runnable(){
                @Override
                public void run() {
                    dispatcher(pcmd);
                }   //否则把任务投递到业务线程里
            });
        }*/
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    /**
     * 服务器的连接被建立后调用
     * 建立连接后该 channelActive() 方法被调用一次
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel=ctx.channel();
        InetSocketAddress inetSocketAddress=(InetSocketAddress)channel.remoteAddress();
        String host=inetSocketAddress.getAddress().getHostAddress();
        int port=inetSocketAddress.getPort();

        ServerInfo serverInfo= serverInfoService.addServerInfo(host,port);
        serverInfo.setServerId(getServerid());
        System.out.println("+++++++++++++++"+serverInfo.toString());

        System.out.println("--------"+getServerid());
        serverInfoId=Integer.parseInt(String.valueOf(serverInfo.getServerId()));
        logger.info("已成功建立连接:"+host);
        System.out.println(String.valueOf(serverInfoId));
        System.out.println(channel);
        MessageQueue.channelMap.put(String.valueOf(serverInfoId),channel);

        byte[] bytes=null;
        //蜜罐服务器属性
        Pcmd networkPcmd=new Pcmd(serverInfoId,CmdUtil.SELECT_NETWORK,0,0,bytes);
        ctx.writeAndFlush(networkPcmd);
        //蜜罐暗网节点服务
        Pcmd awServerPcmd=new Pcmd(serverInfoId,CmdUtil.SELECT_NODE_SERVER,0,0,bytes);
        ctx.writeAndFlush(awServerPcmd);
        //蜜罐web服务
         Pcmd webServerPcmd=new Pcmd(serverInfoId,CmdUtil.SELECT_WEB_SERVER,0,0,bytes);
         ctx.writeAndFlush(webServerPcmd);

//         //服务器硬件与网络属性
//         Pcmd server_hardware=new Pcmd(serverInfoId,CmdUtil.SERVER_HARDWARE,0,0,bytes);
//         ctx.writeAndFlush(server_hardware);

//        //服务器所部署服务
//         Pcmd server_stare=new Pcmd(serverInfoId,CmdUtil.SERVER_STATE,0,0,bytes);
//         ctx.writeAndFlush(server_stare);
//
//          //未完成
//        //获取蜜罐流量服务信息
//        Pcmd flowServerPcmd=new Pcmd(serverInfoId,CmdUtil.SELECT_FLOW_SERVER,0,0,bytes);
//        ctx.writeAndFlush(flowServerPcmd);

//        Pcmd attackServerPcmd=new Pcmd(serverInfoId,CmdUtil.SELECT_ATTACK_INFO,0,0,bytes);
//        ctx.writeAndFlush(attackServerPcmd);
//
//        Pcmd nodeServerPcmd=new Pcmd(serverInfoId,CmdUtil.SELECT_NODE_INFO,0,0,bytes);
//        ctx.writeAndFlush(nodeServerPcmd);

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        MessageQueue.channelMap.remove(String.valueOf(serverInfoId));
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();//关闭通道
    }

    /**
     * 消息处理分发
     * @param pcmd
     */
    public void dispatcher(Pcmd pcmd){
        int type=pcmd.getType();
        switch (type){
            /**===========================================================================**/
            /** ================================基础服务===================================**/
            /**===========================================================================**/
            //控制服务在线状态修改：
            case CmdUtil.CONTROL_SERVICE_START:
            //控制服务离线状态修改
            case CmdUtil.CONTROL_SERVICE_STOP:
             //控制在线状态修改
            case CmdUtil.CONTROL_START:
            //控制离线状态修改
            case CmdUtil.CONTROL_STOP:
                new DataHandler().updateService(pcmd);
                break;
            /**===========================================================================**/
            /** ================================蜜罐暗网节点服务============================**/
            /**===========================================================================**/
            //获取节点硬件和网络属性
            case CmdUtil.SELECT_NETWORK:
                new DataHandler().addNetWork(pcmd);
                break;
            //添加蜜罐节点服务
            case CmdUtil.SELECT_NODE_SERVER:
                new DataHandler().addNodeServer(pcmd);
                break;
            //启动蜜罐暗网节点服务
            case CmdUtil.START_NODE_SERVER:
            //关闭蜜罐暗网节点服务
            case CmdUtil.STOP_NODE_SERVER:
                new DataHandler().updateNetWork(pcmd);
                break;
             /**===========================================================================**/
             /** ================================蜜罐web管理服务============================**/
             /**===========================================================================**/
            //蜜罐web管理服务进行添加
            case CmdUtil.SELECT_WEB_SERVER:
                new DataHandler().addWebServer(pcmd);
                break;
             //启动蜜罐web服务
            case CmdUtil.START_WEB_SERVER:
            //关闭蜜罐web服务
            case CmdUtil.STOP_WEB_SERVER:
                new DataHandler().updateWebServer(pcmd);
                break;
            /**===========================================================================**/
            /** ================================蜜罐流量获取服务============================**/
            /**===========================================================================**/
            //获取蜜罐流量服务信息
            case CmdUtil.SELECT_FLOW_SERVER:
                new DataHandler().addFlowServer(pcmd);
                break;
            //启动蜜罐流量服务
            case CmdUtil.START_FLOW_SERVER:
            //关闭蜜罐流量服务
            case CmdUtil.STOP_FLOW_SERVER:
                new DataHandler().updateFlowServer(pcmd);
                break;
            /**===========================================================================**/
            /** ================================蜜罐服务攻击管理============================**/
            /**===========================================================================**/
            //攻击服务信息查询
            case CmdUtil.SELECT_ATTACK_INFO:
                new DataHandler().addAttackManager(pcmd);
                break;
             //启动攻击服务
            case CmdUtil.START_ATTACK:
             //关闭攻击服务
            case CmdUtil.STOP_ATTACK:
                new DataHandler().updateAttackServer(pcmd);
                break;
            /**===========================================================================**/
            /** ================================文件管理 ==================================**/
            /**===========================================================================**/
            //文件上传命令
            case CmdUtil.FILE_UPLOAD:
                new DataHandler().addFileupload(pcmd);
                break;
            /**===========================================================================**/
            /** ================================基本属性 ==================================**/
            /**===========================================================================**/
            //服务器硬件与网络属性
            case CmdUtil.SERVER_HARDWARE:
                new DataHandler().addAwServerAttribute(pcmd);
                break;
            //服务器所部署服务
            case CmdUtil.SERVER_STATE:
                new DataHandler().addAwFunctionServer(pcmd);
                break;
             //蜜罐服务器修改
            case CmdUtil.SERVER_UPDATE:
                break;
            /**===========================================================================**/
            /** ================================运行日志 ==================================**/
            /**===========================================================================**/
            //运行日志消息获取
            case CmdUtil.LOGGING:
                new DataHandler().addAwLogging(pcmd);
                break;
            /***********************************************************************************
             ********************************** 流量分析处理 ***********************************
             ***********************************************************************************/
            //上传流量文件
            case CmdUtil.FLOW_FILE_UPLOAD:
                new DataHandler().addAnalysisRecord(pcmd);
                break;
             //启动流量分析
            case CmdUtil.START_FLOW:
                new DataHandler().addAnalysisRecordStart(pcmd);
                break;
            //暂停流量分析
            case CmdUtil.STOP_FLOW:
//                new DataHandler().addAnalysisRecordStart(pcmd);
                break;
             //结束流量分析
            case CmdUtil.END_FLOW:
//                new DataHandler().addAnalysisRecord(pcmd);
                break;

            default:
                //new DataHandler().addNetWork(pcmd);
                break;
        }
    }

    /**
     * 阻塞的ExecutorService
     * @param size
     * @return
     */
    public static ExecutorService newBlockingExecutorsUseCallerRun(int size) {
        return new ThreadPoolExecutor(size, size, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        try {
                            executor.getQueue().put(r);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
    }

    public static int getServerid() {
        return serverid;
    }

    public static void setServerid(int serverid) {
        DispatcherHandler.serverid = serverid;
    }
}
