package com.wyfx.aw.utils;

import com.sun.org.apache.xpath.internal.objects.XNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * @ClassName: FileInOutPut
 * @Description: 文件上传工具
 * @author: zhangguliang
 * @date: 2019-11-8
 */
@Component
public class FileReceive {
    //文件存放目录
    @Value("${file.uploadFolder}")
    private String filePath;

    //文件存放目录
    @Value("${file.localhost}")
    private String localhost;
    /**
     * 文件上传
     * @param file 文件输入流
     * @param directoryName 目录
     * @param fileName 文件名称
     * @return
     */
    public boolean fileupload(InputStream file, String directoryName,String fileName){
        /**
         * 默认存放地址
         */
        try {
            String path ="";
            //创建目录
            if(null!=directoryName&&!"".equals(directoryName)){
                path = filePath+directoryName+"/";
            }
            File file1 = new File(path);
            if(!file1.exists()){
                file1.mkdirs();
            }
            if(null==fileName||"".equals(fileName)){
                return false;
            }
            System.out.println(path);
            fileName = path+fileName;
            System.out.println(fileName);
            InputStream in = file;
            OutputStream out = new FileOutputStream(fileName);
            byte[] by = new byte[1024];
            while (in.read(by)!=-1){
                out.write(by);
            }
            out.flush();
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 创建目录
     */
    public void createExists(){
        try {
            File file = new File(filePath);
            if(!file.exists()){
                file.mkdir();
            }
            System.out.println(file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件添加
     * @param filea 文件字节
     * @param directoryName 目录
     * @param fileName 文件名字
     * @return
     */
    public boolean fileInsert(byte[] filea, String directoryName,String fileName){
        try {
            File file1 = new File("");
            String filePath = file1.getCanonicalPath()+"\\src\\main\\resources\\static\\fileDirectory\\";
            if(null!=directoryName&&!"".equals(directoryName)){
                filePath = filePath+directoryName+"\\";
                File f = new File(filePath);
                if(!f.exists()) {
                    f.mkdirs(); //创建目录
                }
            }
            String encoding = "ISO-8859-1";
            String fileaString = new String(filea, encoding);
            System.out.println(fileaString);
            // 写入文件
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.write(fileaString.getBytes(encoding));
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getLocalhost() {
        return localhost;
    }
}
