package com.wyfx.aw.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 下载工具类
 */
public class DownloadUtile {
    /**
     *
     * @param url 文件地址
     * @param filePath 下载路径
     * @return
     */
    public static boolean saveUrlAs(String url, String filePath){
       try {
        File file=new File(filePath);
        if (!file.exists())
        {
            //如果文件夹不存在，则创建新的的文件夹
            file.mkdirs();
        }
        FileOutputStream fileOut = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        URL httpUrl= new URL(url);
         conn=(HttpURLConnection) httpUrl.openConnection();
           conn.setRequestMethod("GET");
           conn.setDoInput(true);
           conn.setDoOutput(true);
           // post方式不能使用缓存
           conn.setUseCaches(false);
           //连接指定的资源
           conn.connect();
           //获取网络输入流
           inputStream=conn.getInputStream();
           BufferedInputStream bis = new BufferedInputStream(inputStream);
           //判断文件的保存路径后面是否以/结尾
           if (!filePath.endsWith("/")) {
               filePath += "/";
           }
           //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
           fileOut = new FileOutputStream(filePath+url.substring(url.lastIndexOf("/")));
           BufferedOutputStream bos = new BufferedOutputStream(fileOut);
           byte[] buf = new byte[1024];
           int length =bis.read(buf);
           //保存文件
           while(length != -1)
           {
               bos.write(buf, 0, length);
               length =bis.read(buf);
           }
           bos.close();
           bis.close();
           conn.disconnect();
          return true;
       } catch (Exception e) {
            e.printStackTrace();
        }
       return false;
    }

    /**
     * 文件删除工具
     * @param dir 文件路径
     * @return
     */
    public static boolean delDir(File dir){
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
