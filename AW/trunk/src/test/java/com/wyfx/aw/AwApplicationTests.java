package com.wyfx.aw;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wyfx.aw.entity.ServerInfo;
import com.wyfx.aw.entity.vo.ServerListVo;
import com.wyfx.aw.service.ServerInfoService;
import com.wyfx.aw.utils.InterfaceCallUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class AwApplicationTests {
    private Socket socket;

    public AwApplicationTests()  {
        try {
            System.out.println("正在连接中。。。。。。");
            socket = new Socket("192.168.0.20",8088);
            System.out.println("已连接");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    @Autowired
//    private ServerInfoService serverInfoService;
//
//    @Test
//    public void contextLoads() throws Exception {
//
//
//    }
        public static void main(String[] args) throws IOException {
            Map<String, Object> mapParams = new HashMap<>();
//            mapParams.put("serverId","3");
//            mapParams.put("directoryName","name9");
//            File file = new File("F:\\flash\\msvcr120.dll");
////
////            mapParams.put("file",file.getName());
////            InterfaceCallUtil.Post("http://192.168.0.12:8081/aw/fileManagement/addFileupload",mapParams);
            String url = "http://192.168.0.12:8081/aw/fileManagement/addFileupload";
            String fileName = "E:/package-list";
            Map<String, String> textMap = new HashMap<String, String>();
            //可以设置多个input的name，value
            textMap.put("directoryName", "name11");
            textMap.put("serverId", "3");
            //设置file的name，路径
            Map<String, String> fileMap = new HashMap<String, String>();
            fileMap.put("file", fileName);
            String contentType = "";//image/png
            String ret = InterfaceCallUtil.formUpload(url, textMap, fileMap,contentType);
            System.out.println(ret);
//
//            InterfaceCallUtil.sendPostWithFile("http://192.168.0.12:8081/aw/fileManagement/addFileupload");
//            JSONObject str = InterfaceCallUtil.Post("http://192.168.0.104:8081/aw/serverDetail/findDeployServer",mapParams);
//            System.out.println(str);
//            JSONObject json = InterfaceCallUtil.get("http://192.168.0.104:8081/aw/operationLogging/getAwFunctionServerByserverId?id=4");
//            Map map = json;
//            System.out.println(json.get("data"));
//            AwApplicationTests awApplicationTests = new AwApplicationTests();
//            System.out.println(111);
//            awApplicationTests.starte();

        }

        public  void starte() throws IOException {
            System.out.println(12);
            serverHandler ser = new serverHandler();
            System.out.println(123);
            Thread t = new Thread(ser);
            System.out.println(124);
            t.start();
            System.out.println(125);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println(126);
            OutputStream out = socket.getOutputStream();
            System.out.println(127);
            OutputStreamWriter ows = new OutputStreamWriter(out,"GBK");
            BufferedWriter bs = new BufferedWriter(ows);
            System.out.println(128);
            PrintWriter pw = new PrintWriter(bs,true);
            System.out.println("开始聊天");
            System.out.println("开始聊天吧！！！");
            String messate = "";
            while (true){
                messate = br.readLine();
                System.out.println(messate);
                pw.println(messate);
            }

        }
        private class serverHandler implements  Runnable{
            private serverHandler() throws IOException {
                System.out.println("***************************");
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"GBK"));
                System.out.println("*************111111111111**************");
                String str = null;
                in.readLine();
//                while ((str = in.readLine())!=null){
//                    System.out.println("*************22222**************");
//                    System.out.println(str);
//                }
            }

            @Override
            public void run() {

            }
        }

        private static List filePath = new ArrayList();
        private static List directory = new ArrayList();
        public static void directoryList(String path) throws Exception{
//            List<String> list = new ArrayList<>();
            File file = new File(path);
            String[] files = file.list();
            for (String str:files) {
                File file1 = new File(file.getPath()+"\\"+str);
                if(file1.isDirectory()){
                    directory.add(file1.getPath());
                    System.out.println(file1.getPath());
                    directoryList(file1.getPath());
                }
            }
        }
        public static void filePathList(String path){
            File file = new File(path);
            String[] files = file.list();
            for (String str:files) {
                File file1 = new File(file.getPath()+"\\"+str);
                if(file1.isFile()){
                    filePath.add(file1.getPath());
                }
                if(file1.isDirectory()){
                    directory.add(file1.getPath());
                    System.out.println(file1.getPath());
                    filePathList(file1.getPath());
                }
            }
        }
            public static boolean delDir(File dir){
                System.out.println(dir.getPath());
                if (dir.isDirectory()){
                    String[] children = dir.list();
                    for (int i=0; i<children.length;i++) {
                        boolean success = delDir(new File(dir, children[i]));
                        if (!success)
                            return false;
                    }

                }
                if (dir.delete()){
                    System.out.println("目录已删除！！！");
                    return true;
                }else {
                    System.out.println("目录删除失败！！！！！");
                }
                return false;
            }

}
