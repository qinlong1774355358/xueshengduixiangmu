package com.wyfx.aw.network;

import com.wyfx.aw.entity.ServerInfo;
import com.wyfx.aw.network.queue.MessageQueue;
import com.wyfx.aw.network.vo.Pcmd;
import com.wyfx.aw.service.ServerInfoService;
import com.wyfx.aw.utils.ApplicationContextRegister;
import com.wyfx.aw.network.vo.CmdUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.ByteOrder;

public class AwClient implements Runnable {
    private static Logger logger=LoggerFactory.getLogger(AwClient.class);
    private final String host;
    private final int port;
    private Bootstrap bootstrap;

    private Channel channel;
    private Long serverInfoId;

    private ServerInfoService serverInfoService= ApplicationContextRegister.getApplicationContext().getBean(ServerInfoService.class);



    public AwClient(String host,int port){
        this.host=host;
        this.port=port;
    }

    public AwClient(String host,int port,Bootstrap bootstrap){
        this.host=host;
        this.port=port;
        this.bootstrap=bootstrap;
    }



    /*public static void main(String[] args){
        //new Thread(new AwClient("121.48.162.134",15678)).start();
        //new Thread(new AwClient("192.168.1.22",5678)).start();
    }*/

    @Override
    public void run(){
        //EventLoopGroup group = new NioEventLoopGroup();
        try {
            /*Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, false)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN,1024,8,4,4,0,true))
                                    .addLast(new PcmdDecoder(Pcmd.class))
                                    .addLast(new PcmdEncoder())
                                    .addLast(new DataHandler());

                        }
                    });*/
            System.out.println("===============================444444444444444444===========================================");
            ChannelFuture f = bootstrap.connect(host,port).sync();
            System.out.println("===============================555555555555555===========================================");
            f.channel().closeFuture().sync();
            System.out.println("===============================66666666666666===========================================");
        }catch (Exception e){
            logger.error("socket连接异常",e);
        }/*finally {
            try {
                //释放 channel 和 块，直到它被关闭
                group.shutdownGracefully().sync();
            }catch (Exception e){
                logger.error("释放channel异常");
            }
        }*/
    }
}
