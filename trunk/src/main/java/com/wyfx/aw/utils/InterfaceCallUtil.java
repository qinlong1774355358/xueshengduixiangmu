package com.wyfx.aw.utils;

import com.alibaba.fastjson.JSONObject;
import com.wyfx.aw.config.filter.UrlFilter;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName: InterfaceCallUtil
 * @Description: 接口调用工具
 * @author: zhangguliang
 * @date: 2019-11-13
 */
public class InterfaceCallUtil {

    public static JSONObject Post(String url, Map<String, Object> mapParams) {
        JSONObject json = new JSONObject();
        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;  //获取输出流，往缓冲区写入数据
        StringBuffer sb = new StringBuffer();//map参数格式化成url发送参数格式
        String params = "";// 格式化之后url传递的参数
        try {
            // 格式化参数
            if (mapParams.size() == 1) {
                for (Map.Entry<String, Object> entry : mapParams.entrySet()) {
                    sb.append(entry.getKey()).append("=").append(
                            java.net.URLEncoder.encode(entry.getValue().toString(),
                                    "UTF-8"));  //对参数进行编码格式化以及拼接
                }
                params = sb.toString();
            } else {
                for (Map.Entry<String, Object> entry : mapParams.entrySet()) {
                    sb.append(entry.getKey()).append("=").append(
                            java.net.URLEncoder.encode(entry.getValue().toString(),
                                    "UTF-8")).append("&");  //对参数进行编码格式化以及拼接
                }
                params = sb.toString().substring(0, sb.toString().length() - 1);
            }
            System.out.println(params);
            // 创建URL对象
            java.net.URL connURL = new java.net.URL(url);

            // 打开URL连接
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
                    .openConnection();

            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");  //设置连接的状态，建立持久化连接，Keep-Alive；close短连接
            httpConn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");  //设置浏览器属性
            httpConn.setRequestProperty("Accept-Charset", "utf-8");  //设置编码语言

            //设置传送的内容是可序列化的java对象，即键值对
            httpConn.setRequestProperty("Content-type", "multipart/form-data");//设置请求格式/json/xml/object

            // 设置POST方式相关属性
            // 设定请求的方法为"POST"，默认是GET
            httpConn.setRequestMethod("POST");

            //设置是否从HttpURLConnection读入，默认情况下是true;
            httpConn.setDoInput(true);

            //设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
            httpConn.setDoOutput(true);

            // Post请求不能使用缓存,需设置成false
            httpConn.setUseCaches(false);

            //防止网络异常，设置连接主机超时（单位：毫秒）
            httpConn.setConnectTimeout(30000);

            //防止网络异常，设置从主机读取数据超时（单位：毫秒）
            httpConn.setReadTimeout(30000);

            //设置文件请求的长度
            //httpConn.setRequestProperty("Content-Length", params.getBytes().length + "");

            //httpConn.connect();

            // 获取HttpURLConnection对象对应的输出流， 该方法已经隐含调用httpConn.connect()连接方法；
            out = new PrintWriter(httpConn.getOutputStream());

            // post方式发送请求参数
            out.write(params);

            //释放输出流的缓冲，同时关闭输出流对象，不再写入数据 ，释放网络资源
            out.flush();

            /**
             * 获取响应状态码，更多状态码请百度
             * (200)服务器成功返回;
             * (404)请求网页不存在;
             * (503)服务不可用
             */
            int code = httpConn.getResponseCode();
            System.out.println("响应状态码："+code);
            if(code==200) {
                // 定义BufferedReader输入流来读取URL的响应，设置编码方式
                in = new BufferedReader(new InputStreamReader(httpConn
                        .getInputStream(), "UTF-8"));

                String line;
                // 读取返回的内容
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            }
            //httpConn.disconnect();//关闭连接，释放资源，该方法会彻底关闭长连接，释放网络资源
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();  //关闭输出流，释放网络资源
                }
                if (in != null) {
                    in.close();  //关闭输入流，释放网络资源
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        json = (JSONObject)JSONObject.parse(result);
        return json;
    }
    public static String  sendPostWithFile(String utl){

        DataOutputStream out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(utl);
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            String BOUNDARY ="--------------575104ADFSA";
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection","Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70 Safari/537.36");
            conn.setRequestProperty("Charset-Type","UTF-8");
            conn.setRequestProperty("Content-Type","multipart/form-data");
            System.out.println(utl);
            out = new DataOutputStream(conn.getOutputStream());
            System.out.println("===================================");
            byte[] end_data = ("\r\n"+BOUNDARY+"==\r\n").getBytes();
            StringBuilder sb1 = new StringBuilder();
            sb1.append("--");
            sb1.append(BOUNDARY);
            sb1.append("\r\n");
            sb1.append("Content-Disposition:form-data;name=\"directoryName\"");
            sb1.append("\r\n");
            sb1.append("\r\n");
            sb1.append("name9");
            sb1.append("\r\n");
            out.write(sb1.toString().getBytes());
            File file = new File("E:/script.js");
            StringBuilder sb = new StringBuilder();
            sb1.append("--");
            sb1.append(BOUNDARY);
            sb1.append("\r\n");
            sb1.append("Content-Disposition:form-data;name=\"file\";file=\""+file.getName()+"\"");
            sb1.append("\r\n");
            sb1.append("Content-Type:text/plain");
            sb1.append("\r\n");
            sb1.append("\r\n");
            System.out.println(sb1.toString());
            out.write(sb.toString().getBytes());
            DataInputStream in1 = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while((bytes=in1.read(bufferOut))!=-1){
                out.write(bufferOut,0,bytes);
            }
            out.write("\r\n".getBytes());
            in1.close();
            out.write(end_data);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line=null;
            while ((line = in.readLine())!=null){
                result+=line;
            }
        }catch (Exception e){
            System.out.println("发送post异常");
            e.printStackTrace();
        }finally {
                try {
            if(out!=null){
                out.close();
            }
            if(in!=null){
                in.close();
            }
            } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }

    /**
     * 上传图片
     * @param urlStr
     * @param textMap
     * @param fileMap
     * @param contentType 没有传入文件类型默认采用application/octet-stream
     * contentType非空采用filename匹配默认的图片类型
     * @return 返回response数据
     */
    @SuppressWarnings("rawtypes")
    public static String formUpload(String urlStr, Map<String, String> textMap,
                                    Map<String, String> fileMap,String contentType) {
        String res = "";
        HttpURLConnection conn = null;
        // boundary就是request头和上传文件内容的分隔符
        String BOUNDARY = "---------------------------123821742118716";
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type","multipart/form-data; boundary=" + BOUNDARY);
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // text
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator iter = textMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes());
                System.out.println(strBuf.toString());
            }
            // file
            if (fileMap != null) {
                Iterator iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();

                    //没有传入文件类型，同时根据文件获取不到类型，默认采用application/octet-stream
                    contentType = new MimetypesFileTypeMap().getContentType(file);
                    //contentType非空采用filename匹配默认的图片类型
                    if(!"".equals(contentType)){
                        if (filename.endsWith(".png")) {
                            contentType = "image/png";
                        }else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".jpe")) {
                            contentType = "image/jpeg";
                        }else if (filename.endsWith(".gif")) {
                            contentType = "image/gif";
                        }else if (filename.endsWith(".ico")) {
                            contentType = "image/image/x-icon";
                        }
                    }
                    if (contentType == null || "".equals(contentType)) {
                        contentType = "application/octet-stream";
                    }
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    System.out.println(strBuf.toString());
                    out.write(strBuf.toString().getBytes());
                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();
            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + urlStr);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }


    public static JSONObject get(String address){
        JSONObject json = new JSONObject();
        try {
        json.put("orderId","2019061015241733858019");
        URL url = new URL(address);
        //打开和url之间的连接
        HttpURLConnection conn = null;

            conn = (HttpURLConnection) url.openConnection();

        PrintWriter out = null;
        //请求方式
        conn.setRequestMethod("GET");
        //设置通用的请求属性
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
        //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
        //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。get请求不需要设置
        conn.setDoOutput(true);
        conn.setDoInput(true);
        //获取URLConnection对象对应的输出流
        conn.connect();
        //获取URLConnection对象对应的输入流
        InputStream is = conn.getInputStream();
        //构造一个字符流缓存
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String str = "";
        com.alibaba.fastjson.JSONObject jsonss = null;
        while ((str = br.readLine()) != null) {
            System.out.println(str);
            jsonss =com.alibaba.fastjson.JSONObject.parseObject(str);
        }
        json = jsonss;
        System.out.println("获取到的报文数据为："+jsonss);
        //关闭流
        is.close();
        //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
        //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
        conn.disconnect();
        System.out.println("结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     *
     * @param address 下载地址
     * @param filePath 文件目录
     * @param fileName 文件名字
     * @return
     */
    public static Object get1(String address,String filePath,String fileName){
        try {
            URL url = new URL(address);
            //打开和url之间的连接
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            PrintWriter out = null;
            //请求方式
            conn.setRequestMethod("GET");
            //设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            //设置是否向httpUrlConnection输出，设置是否从httpUrlConnection读入，此外发送post请求必须设置这两个
            //最常用的Http请求无非是get和post，get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，
            //post与get的 不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。get请求不需要设置
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //获取URLConnection对象对应的输出流
            conn.connect();
            //获取URLConnection对象对应的输入流
            InputStream is = conn.getInputStream();
            //构造一个字符流缓存
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            File file = new File(filePath);
            System.out.println(file.getPath());
            if(!file.exists()){
                file.mkdir();
            }
            out = new PrintWriter(file+"/"+fileName);
            String str = "";
            while ((str = br.readLine()) != null) {
                out.write(str);
            }
            //关闭流
            is.close();
            //断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            //固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些。
            conn.disconnect();
            System.out.println("结束");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
