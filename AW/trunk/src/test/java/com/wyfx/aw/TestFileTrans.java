package com.wyfx.aw;

import java.io.*;
import java.net.Socket;

public class TestFileTrans{
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8787);
        input(socket);
//        output(socket);
        socket.close();
    }
    public static void output( Socket socket) throws IOException {
        OutputStream os = socket.getOutputStream();
        os.write("文件传输成功".getBytes());
        socket.shutdownOutput();
    }
    public static void input( Socket socket) throws IOException {
        InputStream is = socket.getInputStream();
//          is.read();
        System.out.println(is.read());
        byte len = (byte) is.read();
        System.out.println(len);
        byte[] fileName = new byte[len];
        System.out.println(fileName);
        is. read(fileName);
        FileOutputStream fos = new FileOutputStream("F:\\项目管理\\项目开发\\按网\\上传文件\\name3\\"+"apizzaSQ-pro-1.3.3.zip");
        byte[] bs = new byte[1024];
        int i;
        while ((i = is.read(bs)) != -1) {
            fos.write(bs, 0, i);
        }
        fos.close();
    }
}


