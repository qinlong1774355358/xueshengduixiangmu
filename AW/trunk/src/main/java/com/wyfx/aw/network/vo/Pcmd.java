package com.wyfx.aw.network.vo;

import com.wyfx.aw.utils.SocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * @Author johnliu
 * @create 2018/9/6 16:33
 * @Description 构造c++中的消息结构体
 * 注意：该结构体中，当通过构造函数  生成Pcmd对象时，dataBuf 包含以上类型结构的二进制消息；
 * 当通过readToPcmd生成Pcmd时dataBuf的值不包含type和size等数据
 **/
public class Pcmd {

    private static Logger logger=LoggerFactory.getLogger(Pcmd.class);

    private int idst;//id标识

    private int type;//命令类型
    private int size;//消息（data）长度
    private int resv;
    private byte[] dataBuf;//二进制消息

    public Pcmd() {
    }

    /**
     * 构造生成socket输出流
     */
    public Pcmd(int idst, int type, int size, int resv, String data) {

        byte[] headByte=new byte[16];
        byte[] temp;
        temp=SocketUtil.intToBytesLH(idst);
        System.arraycopy(temp,0,headByte,0,temp.length);//将idst保存到byte流中

        /*System.arraycopy(type,0,headByte,4,1);//将type保存到byte流中

        temp=SocketUtil.intToBytesLH(size);
        System.arraycopy(temp,0,headByte,5,temp.length);//将size保存到byte流中

        temp=SocketUtil.intToBytesLH(resv);
        System.arraycopy(temp,0,headByte,9,temp.length);//将resv保存到byte流中*/

        temp=SocketUtil.intToBytesLH(type);
        System.arraycopy(temp,0,headByte,4,temp.length);//将type保存到byte流中

        temp=SocketUtil.intToBytesLH(size);
        System.arraycopy(temp,0,headByte,8,temp.length);//将size保存到byte流中

        temp=SocketUtil.intToBytesLH(resv);
        System.arraycopy(temp,0,headByte,12,temp.length);//将resv保存到byte流中
        int dataLen=0;
        try {
            if(data!=null){
                temp=data.getBytes("UTF-8");
                dataLen=temp.length;
            }
            dataBuf=new byte[16+dataLen];
            System.arraycopy(headByte,0,dataBuf,0,headByte.length);//将head保存到byte流中
            if(dataLen>0){
                System.arraycopy(temp,0,dataBuf,16,temp.length);//将data保存到byte流中
            }
            this.idst=idst;
            this.type=type;
            this.size=size;
        } catch (UnsupportedEncodingException e) {
            logger.error("构造Pcmd流失败:",e);
        }
    }

    /**
     * 构造生成socket输出流
     */
    public Pcmd(int idst, int type, int size, int resv, byte[] buf) {
        dataBuf=new byte[16+size];
        byte[] temp;
        temp=SocketUtil.intToBytesLH(idst);
        System.arraycopy(temp,0,dataBuf,0,temp.length);//将idst保存到byte流中

        temp=SocketUtil.intToBytesLH(type);
        System.arraycopy(temp,0,dataBuf,4,temp.length);//将type保存到byte流中

        temp=SocketUtil.intToBytesLH(size);
        System.arraycopy(temp,0,dataBuf,8,temp.length);//将size保存到byte流中

        temp=SocketUtil.intToBytesLH(resv);
        System.arraycopy(temp,0,dataBuf,12,temp.length);//将resv保存到byte流中

        if(buf!=null && size>0){
            System.arraycopy(buf,0,dataBuf,16,size);//将buf保存到byte流dataBuf中
        }
        this.idst=idst;
        this.type=type;
        this.size=size;
        this.resv=resv;
    }

    public byte[] getDataBuf() {
        return dataBuf;
    }

    public void setDataBuf(byte[] dataBuf) {
        this.dataBuf = dataBuf;
    }

    public int getIdst() {
        return idst;
    }

    public void setIdst(int idst) {
        this.idst = idst;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getResv() {
        return resv;
    }

    public void setResv(int resv) {
        this.resv = resv;
    }

    @Override
    public String toString() {
        return "Pcmd{" +
                "idst=" + idst +
                ", type=" + type +
                ", size=" + size +
                ", resv=" + resv +
                '}';
    }
}
