package com.wyfx.aw.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;
import java.nio.ByteOrder;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class AwSever {

    private final int port;

    public AwSever(int port){
        this.port=port;
    }

    public void start() throws Exception {
        System.out.println("******************************111111111111***************************************");
        //Netty内部都是通过线程在处理各种数据，EventLoopGroup就是用来管理调度他们的，注册Channel，管理他们的生命周期。
        //NioEventLoopGroup是一个处理I/O操作的多线程事件循环
        //bossGroup作为boss,接收传入连接
        //因为bossGroup仅接收客户端连接，不做复杂的逻辑处理，为了尽可能减少资源的占用，取值越小越好.
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //workerGroup作为worker，处理boss接收的连接的流量和将接收的连接注册进入这个worke
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //ServerBootstrap负责建立服务端
            //你可以直接使用Channel去建立服务端，但是大多数情况下你无需做这种乏味的事情
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    //指定使用NioServerSocketChannel产生一个Channel用来接收连接  指定NIO的模式 NioServerSocketChannel对应TCP, NioDatagramChannel对应UDP
                    .channel(NioServerSocketChannel.class)
                    //设置 socket 地址使用所选的端口
                    .localAddress(new InetSocketAddress(port))
                    //ChannelInitializer用于配置一个新的Channel
                    //用于向你的Channel当中添加ChannelInboundHandler的实现
                    //添加 EchoServerHandler 到 Channel 的 ChannelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            //ChannelPipeline用于存放管理ChannelHandel
                            //ChannelHandler用于处理请求响应的业务逻辑相关代码
                            // //配置通信数据的处理逻辑, 可以addLast多个

                            //心跳机制 参数:1.读空闲超时时间 2.写空闲超时时间 3.所有类型的空闲超时时间(读、写) 4.时间单位
                            //在Handler需要实现userEventTriggered方法，在出现超时事件时会被触发
                            //ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(10, 0, 0,TimeUnit.SECONDS));
                            //按自定义的方式来接收消息 ByteOrder.LITTLE_ENDIAN
                            //ch.pipeline().addLast(new MyProtocolDecoder(1024,8,4,16,0,true));
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN,1024,8,4,4,0,true))
                                    .addLast(new PcmdDecoder())
                                    .addLast(new PcmdEncoder());


                        }
                    })//对Channel进行一些配置
                    //注意以下是socket的标准参数
                    //BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。如果未设置或所设置的值小于1，Java将使用默认值50。
                    //Option是为了NioServerSocketChannel设置的，用来接收传入连接的  设置TCP缓冲区
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //保持连接
                    //是否启用心跳保活机制。在双方TCP套接字建立连接后（即都进入ESTABLISHED状态）并且在两个小时左右上层没有任何数据传输的情况下，这套机制才会被激活。
                    //childOption是用来给父级ServerChannel之下的Channels设置参数的
                    .childOption(ChannelOption.SO_KEEPALIVE, false);
            //绑定的服务器;sync 等待服务器关闭 ,也可以在该处再绑定端口。 bind返回future(异步), 加上sync阻塞在获取连接处
            ChannelFuture f = b.bind().sync();
            /*System.out.println(EchoServer.class.getName() + " started and listen on " + f.channel().localAddress());*/
            //sync()会同步等待连接操作结果，用户线程将在此wait()，直到连接操作完成之后，线程被notify(),用户代码继续执行
            //closeFuture()当Channel关闭时返回一个ChannelFuture,用于链路检测
            f.channel().closeFuture().sync();
        } finally {
            //释放 channel 和 块，直到它被关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
