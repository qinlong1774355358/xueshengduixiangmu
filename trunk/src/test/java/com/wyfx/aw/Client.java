package com.wyfx.aw;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;

    public Client() {
        try {
            this.socket = new Socket("121.48.162.134",15678);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取服务器信息
     */
    private class ServerHandler implements Runnable{
        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"GBK"));
                String message = null;
                while ((message=in.readLine())!=null){
                    System.out.println(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    public void start(){
        ServerHandler handler = new ServerHandler();
        Thread t = new Thread(handler);
        t.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            OutputStream out = socket.getOutputStream();
            OutputStreamWriter ows = new OutputStreamWriter(out,"GBK");
            BufferedWriter bs = new BufferedWriter(ows);
            PrintWriter pw = new PrintWriter(bs,true);
            System.out.println("开始聊天");
            String message = null;
            while (true){
                message = br.readLine();
                pw.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public  static void main(String[] arg){
        Client client = new Client();
        client.start();
    }
}
