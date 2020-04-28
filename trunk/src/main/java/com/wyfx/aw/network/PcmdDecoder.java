package com.wyfx.aw.network;

import com.wyfx.aw.network.vo.Pcmd;
import com.wyfx.aw.utils.SocketUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义协议解码器
 */
public class PcmdDecoder extends ByteToMessageDecoder {

    private static Logger logger=LoggerFactory.getLogger(PcmdDecoder.class);
    /**
     * 头部长度字节数
     * 由于在PcmdDecoder的encode方法中使用的是writeInt，int为4个字节
     */
    private Integer HEAD_LENGTH = 4;
    private int INT_BYTE_SIZE=4;
    private int CMD_HEAD_SIZE=16;

    /*private Class<?> genericClass;

    public PcmdDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }*/

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //由于一个完整的
        /*int len=byteBuf.readableBytes();
        if (len < CMD_HEAD_SIZE) {
            return;
        }
        byte[] bytes=new byte[len];
        byteBuf.getBytes(byteBuf.readerIndex(),bytes);
        byteBuf.readerIndex(len);//由于byteBuf的getBytes()不会更新readIndex的值，所以此处需要手动更新
        int num =byteBuf.readerIndex();
        logger.info("接收到的数据:"+num);
        Pcmd pcmd= getDeserialize(bytes);
        list.add(pcmd);*/
        int len=byteBuf.readableBytes();
        logger.info("进入Pcmd解码器"+len);
        if (len < CMD_HEAD_SIZE) {
            return;
        }
        byte[] bytes=new byte[len];
        byteBuf.readBytes(bytes);
        Pcmd pcmd= getDeserialize(bytes);
        list.add(pcmd);
        /*int len=byteBuf.readableBytes();
        logger.info("进入Pcmd解码器"+len);
        if (len < CMD_HEAD_SIZE) {
            return;
        }
        int lenOffset=8;
        byte[] lengthBytes=null;
        byte[] bytes=null;
        do {
            lengthBytes=new byte[INT_BYTE_SIZE];
            byteBuf.getBytes(lenOffset+byteBuf.readerIndex(),lengthBytes);
            int length= SocketUtil.bytesToIntLH(lengthBytes,0);//每个包的长度
            bytes=new byte[CMD_HEAD_SIZE+length];
            byteBuf.readBytes(bytes);
            Pcmd pcmd= getDeserialize(bytes);
            list.add(pcmd);
        }while (byteBuf.isReadable());*/
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("Pcmd解码异常:",cause);
        /*super.exceptionCaught(ctx, cause);*/
    }

    /**
     * 反序列化Pcmd
     * @param wsaBuf
     * @return
     */
    private Pcmd getDeserialize(byte[] wsaBuf){
        Pcmd pcmd=new Pcmd();
        byte[] temp=new byte[INT_BYTE_SIZE];
        //读取SessionId，即Idst，普通连接时，Idst为0，当启动控制后，Idst才会有值
        System.arraycopy(wsaBuf,0,temp,0,temp.length);
        pcmd.setIdst(SocketUtil.bytesToIntLH(temp,0));
        //读取命令类型
        System.arraycopy(wsaBuf,4,temp,0,temp.length);
        pcmd.setType(SocketUtil.bytesToIntLH(temp,0));
        /*pcmd.setType(SocketUtil.bytesToIntLH(temp,0));*/
        //读取消息长度
        System.arraycopy(wsaBuf,8,temp,0,temp.length);
        pcmd.setSize(SocketUtil.bytesToIntLH(temp,0));
        //读取resv
        System.arraycopy(wsaBuf,12,temp,0,temp.length);
        pcmd.setResv(SocketUtil.bytesToIntLH(temp,0));
        //读取数据
        int wsaBufLen=wsaBuf.length;
        if(wsaBufLen-CMD_HEAD_SIZE>0){
            byte[] data=new byte[wsaBufLen-CMD_HEAD_SIZE];
            System.arraycopy(wsaBuf,16,data,0,data.length);
            pcmd.setDataBuf(data);
        }
        return pcmd;
    }



}
