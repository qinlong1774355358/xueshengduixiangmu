package com.wyfx.aw.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.UUID;

/**
 * 获取ip地址的地域信息
 */
public class GetAddressByIp {
    /**
     *
     * @param ipAddr
     * @return
     *  {
     * code: 0,
     * data: {
     * ip: "121.48.162.134",
     * country: "中国",
     * area: "",
     * region: "四川",
     * city: "成都",
     * county: "XX",
     * isp: "教育网",
     * country_id: "CN",
     * area_id: "",
     * region_id: "510000",
     * city_id: "510100",
     * county_id: "xx",
     * isp_id: "100027"
     * }
     * }
     * @throws Exception
     */
    public static JSONObject getIpInfo(String ipAddr) throws  Exception{
        String aliAddr="http://ip.taobao.com/service/getIpInfo.php?ip=";
        try{
            String str = getJsonContent(aliAddr+ipAddr);
            JSONObject jsonObject= JSONObject.parseObject(str);
            JSONObject data=(JSONObject) jsonObject.get("data");
            int code=jsonObject.getInteger("code");
            if(code==0){
                return data;
            }else {
                throw new Exception("ip地址有误");
            }
        }catch(Exception e){
            return null;
        }
    }

    public static String getJsonContent(String urlStr) {
        try {// 获取HttpURLConnection连接对象
            URL url = new URL(urlStr);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            // 设置连接属性
            httpConn.setConnectTimeout(3000);
            httpConn.setDoInput(true);
            httpConn.setRequestMethod("GET");
            // 获取相应码
            int respCode = httpConn.getResponseCode();
            if (respCode == 200) {
                return convertStream2Json(httpConn.getInputStream());
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String convertStream2Json(InputStream inputStream) {
        String jsonStr = "";
        // ByteArrayOutputStream相当于内存输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        // 将输入流转移到内存输出流中
        try {
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, len);
            }
            // 将内存流转换为字符串
            jsonStr = new String(out.toByteArray());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    /*public static void main(String[] args){
        try {
            JSONObject jsonObject= GetAddressByIp.getIpInfo("121.48.162.134");
            System.out.println(jsonObject);
        }catch (Exception e){

        }
    }*/

    /**
     * 获取32位uuid
     * @return
     */
    public static String getUUID(){
        String uuid=UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }

    public static String getBase64Str(String source){
        final Base64.Encoder encoder = Base64.getEncoder();
        try {
            String encodedText = encoder.encodeToString(source.getBytes("utf8"));
            return encodedText;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
