package com.wyfx.aw.utils;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Author: Liujie
 * create: 2018-02-03 16:53
 * description: 定义转换 socket 消息长度的方法
 *  参考: http://blog.csdn.net/ljl157011/article/details/19291611
 **/
public class SocketUtil {
    private static Logger logger=LoggerFactory.getLogger(SocketUtil.class);

    /**
     * 将byte数组转化为Long类型
     * @param array
     * @return
     */
    public static long bytesToLong(byte[] array){
        return ((((long) array[0] & 0xff) << 56) | (((long) array[1] & 0xff) << 48) | (((long) array[2] & 0xff) << 40)
                | (((long) array[3] & 0xff) << 32) | (((long) array[4] & 0xff) << 24)
                | (((long) array[5] & 0xff) << 16) | (((long) array[6] & 0xff) << 8) | (((long) array[7] & 0xff) << 0));
    }

    public static long bytes2Long(byte[] byteNum) {
        long num = 0;
        for (int ix = 0; ix < 8; ++ix) {
            num <<= 8;
            num |= (byteNum[ix] & 0xff);
        }
        return num;
    }

    /**
     *  int 转字节数组,本方法适用于(高位在前，低位在后)的顺序。和 byteArrayToInt（）配套使用
     */
    public static byte[] intToByteArrayHL(int i) {
        byte[] result = new byte[4];
        result[0] = (byte)((i >> 24) & 0xFF);
        result[1] = (byte)((i >> 16) & 0xFF);
        result[2] = (byte)((i >> 8) & 0xFF);
        result[3] = (byte)(i & 0xFF);
        return result;
    }

    /**
     *  字节数组转int,和 intToByteArray（）配合使用
     */
    public static int byteArrayToIntHL(byte[] b) {
        int intValue=0;
        for(int i=0;i<b.length;i++){
            intValue +=(b[i] & 0xFF)<<(8*(3-i));
        }
        return intValue;
    }
    /**
     *  字节数组转int,和 intToByteArray（）配合使用
     */
    public static int byteArrayToInt2(byte[] src, int offset) {
        int value;
        value = (int) ( ((src[offset] & 0xFF)<<24)
                |((src[offset+1] & 0xFF)<<16)
                |((src[offset+2] & 0xFF)<<8)
                |(src[offset+3] & 0xFF));
        return value;
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。
     * @param value
     *            要转换的int值
     * @return byte数组
     */
    public static byte[] intToBytesLH(int value)
    {
        byte[] byte_src = new byte[4];
        byte_src[3] = (byte) ((value & 0xFF000000)>>24);
        byte_src[2] = (byte) ((value & 0x00FF0000)>>16);
        byte_src[1] = (byte) ((value & 0x0000FF00)>>8);
        byte_src[0] = (byte) ((value & 0x000000FF));
        return byte_src;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序。
     *
     * @param ary byte数组
     * @param offset 从数组的第offset位开始
     * @return int数值
     */
    public static int bytesToIntLH(byte[] ary, int offset) {
        int value;
        value = (int) ((ary[offset]&0xFF)
                | ((ary[offset+1]<<8) & 0xFF00)
                | ((ary[offset+2]<<16)& 0xFF0000)
                | ((ary[offset+3]<<24) & 0xFF000000));
        return value;
    }


    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。 和bytesToInt（）配套使用
     * @param value
     *            要转换的int值
     * @return byte数组
     */
    /*public static byte[] intToBytes( int value )
    {
        byte[] src = new byte[4];
        src[3] =  (byte) ((value>>24) & 0xFF);
        src[2] =  (byte) ((value>>16) & 0xFF);
        src[1] =  (byte) ((value>>8) & 0xFF);
        src[0] =  (byte) (value & 0xFF);
        return src;
    }*/

    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToBytes（）配套使用
     * @param * src byte数组
     * @param *offset 从数组的第offset位开始
     * @return int数值
     */
    /*public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF)
                | ((src[offset+1] & 0xFF)<<8)
                | ((src[offset+2] & 0xFF)<<16)
                | ((src[offset+3] & 0xFF)<<24));
        return value;
    }*/



    public static byte[] intToByteArray2(int i) throws Exception {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buf);
        out.writeInt(i);
        byte[] b = buf.toByteArray();
        out.close();
        buf.close();
        return b;
    }

    /*public static void main(String[] args){
        int num=153262;
        *//*byte[] bytes= SocketUtil.intToByteArray(num);*//*
        byte[] bytes= SocketUtil.intToBytes(num);

        *//*int num1=SocketUtil.byteArrayToInt(bytes);*//*
        int num1=SocketUtil.bytesToInt(bytes,0);
        System.out.println("num1:"+num1);
        for (int i=0;i<bytes.length;i++){
            System.out.println("第"+i+"个字节："+bytes[i]);
        }
    }*/


    /**
     * 将byte数组转化成String,为了支持中文，转化时用UTF-8编码方式
     */
    public static String ByteArraytoString(byte[] valArr) {
        String result=null;
        int index = 0;
        while(index < valArr.length) {
            if(valArr[index] == 0) {
                break;
            }
            index++;
        }
        byte[] temp = new byte[index];
        System.arraycopy(valArr, 0, temp, 0, index);
        try {
            result= new String(temp,"UTF-8");//UTF-8
            /*result= new String(temp,"GBK");*/
        } catch (UnsupportedEncodingException e) {
            logger.error("Byte转String异常:",e);
        }
        return result;
    }

    /**
     * 将String转化为byte,为了支持中文，转化时用UTF-8编码方式
     */
    public byte[] StringToByteArray(String str){
        byte[] temp = null;
        try {
            temp = str.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            logger.error("String转byte异常:",e);
        }
        return temp;
    }

    /*public static void main(String[] args) throws Exception {
        int num=1985343811;
        byte[] bytes=intToBytesLH(num);
        System.out.println(Arrays.toString(bytes));
    }*/

}
