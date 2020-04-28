package com.wyfx.aw;

import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TestFileTransferServer {

        public static void main(String[] args) throws  Exception {
                ServerSocket ss = new ServerSocket(8787);
                Socket socket = ss.accept();
            System.out.println("123456");
                output(socket);
//                input(socket);

                ss. close() ;
        }

        public static void output(Socket socket) throws Exception {
            OutputStream os = socket.getOutputStream();
            test test = new test();
            byte[] byte1 = test.input();
//            json.put("pyte",byte1);
            os.write(byte1);
//            File file = new File("F:\\项目管理\\项目开发\\按网\\上传文件\\settings.gradle" );
//            String strFile = file.getName();
//            int len = strFile.getBytes().length;
//            os.write(len);
//            os.write(strFile.getBytes());
//            FileInputStream fis = new FileInputStream(file);
//            byte[] bs = new byte[1824];
//
//            int i;
//            while ((i = fis.read(bs)) != -1){
//                System.out.println(i);
//                os.write(bs, 0, i);
//            }
            socket.shutdownOutput();
//            fis. close();
        }

        public static void input(Socket socket) throws IOException {
            InputStream is = socket. getInputStream();
            byte[] bs2 = new byte[100];
            int l = is. read(bs2);
            if (l ==-1) {
                System.out.println("地区服务器信息失败");
            }else{
                System . out.println(new String(bs2, "utf-8"));
            }
        }
}