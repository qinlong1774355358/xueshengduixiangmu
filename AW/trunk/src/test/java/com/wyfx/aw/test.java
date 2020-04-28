package com.wyfx.aw;

import com.alibaba.fastjson.JSONObject;
import com.wyfx.aw.network.vo.Pcmd;

import java.io.*;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class test {
    public static void main(String[] args) throws Exception {
//        test t = new test();
//        JSONObject json = new JSONObject();
//        System.out.println(t.input().length);
//        json.put("byte",t.input());
//        byte[] in = (byte[]) json.get("byte");
//
//        t.output(in,"F:\\项目管理\\项目开发\\按网\\上传文件\\name2\\apizzaSQ-pro-1.3.3.zip");

//        System.out.println(t.input().length);
//
//        Pcmd Pcmd =new Pcmd(3, 0, 0, 0, t.input());
//
//        System.out.println(Pcmd);

//        for (int i=0;i<50;i++){
//            Date date = new Date();
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(date);
//            calendar.add(Calendar.MONTH, -4);
//            calendar.add(Calendar.DATE, -1);
//            calendar.add(Calendar.HOUR, -i);
//            calendar.add(Calendar.MINUTE, -i);
//            calendar.getTime();
//            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////            System.out.println();
//            System.out.println(sim.format(calendar.getTime()));
//        }
//        System.out.println(UUID.randomUUID());
        String str1 = "asdg.";
        System.out.println(str1.replace("a","b"));
        Date date = new Date();
       double doub = 5571888154.83438;
        date.setTime((long) doub);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(simpleDateFormat.format(date));
    }


    public byte[] input() throws Exception {
        File file = new File("F:\\项目管理\\项目开发\\按网\\上传文件\\apizzaSQ-pro-1.3.3.zip");
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[500];
        int len = -1;
        while((len = fis.read(b)) != -1) {
            bos.write(b, 0, len);
        }
        byte[] filea = bos.toByteArray();
        fis.close();
        bos.close();
        return filea;
    }





     public  void output(byte[] filea,String path) throws Exception {
         String encoding = "ISO-8859-1";
         String fileaString = new String(filea, encoding);
         System.out.println(fileaString);
         // 写入文件
         FileOutputStream fileOutputStream = new FileOutputStream(path);
         fileOutputStream.write(fileaString.getBytes(encoding));
         fileOutputStream.flush();
         fileOutputStream.close();
     }
}
