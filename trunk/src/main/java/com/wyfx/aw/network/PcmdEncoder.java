package com.wyfx.aw.network;

import com.wyfx.aw.network.vo.Pcmd;
import com.wyfx.aw.utils.SocketUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 自定义协议编码器
 */
public class PcmdEncoder extends MessageToByteEncoder<Pcmd> {
    private int HEAD_SIZE=16;
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Pcmd pcmd, ByteBuf byteBuf) throws Exception {
        byte[] bytes= getSerializerBytes(pcmd);
        byteBuf.writeBytes(bytes);
    }

    /**
     * 序列化Pcmd对象
     * @param pcmd
     * @return
     */
    private byte[] getSerializerBytes(Pcmd pcmd){
        int size=pcmd.getSize();
        byte[] dataBuf=new byte[HEAD_SIZE+size];
        byte[] temp;
        temp=SocketUtil.intToBytesLH(pcmd.getIdst());
        System.arraycopy(temp,0,dataBuf,0,temp.length);//将idst保存到byte流中

        temp=SocketUtil.intToBytesLH(pcmd.getType());
        System.arraycopy(temp,0,dataBuf,4,temp.length);//将type保存到byte流中

        temp=SocketUtil.intToBytesLH(size);
        System.arraycopy(temp,0,dataBuf,8,temp.length);//将size保存到byte流中

        temp=SocketUtil.intToBytesLH(pcmd.getResv());
        System.arraycopy(temp,0,dataBuf,12,temp.length);//将resv保存到byte流中

        if(pcmd.getDataBuf()!=null|| pcmd.getDataBuf().length>0 ){
            System.arraycopy(pcmd.getDataBuf(),0,dataBuf,16,size);//将buf保存到byte流dataBuf中
        }
        return dataBuf;
    }



}
