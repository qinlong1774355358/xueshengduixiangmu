package com.wyfx.aw.network;

import com.wyfx.aw.network.vo.Pcmd;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.ByteOrder;


/**
 * 常量化netty的引导配置类Bootstrap
 */
public class BootstrapHelper {

    private static Logger logger=LoggerFactory.getLogger(BootstrapHelper.class);
    public static final Bootstrap bootstrap = new Bootstrap();
    private static EventLoopGroup worker = new NioEventLoopGroup();
    private static EventLoopGroup businessGroup = new NioEventLoopGroup(20);

    static {
        //EventLoopGroup worker = new NioEventLoopGroup();

        //设置工作线程
        bootstrap.group(worker);

        //初始化channel
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, false);

        //设置handler管道
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline()
                        .addLast(new MyLengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN,1024*1024*1024,8,4,4,0,true))
                        .addLast(new PcmdDecoder())
                        .addLast(new PcmdEncoder())
                        .addLast(businessGroup,"business",new DispatcherHandler());
            }
        });
        logger.info("Bootstrap完成配置");
    }
}
