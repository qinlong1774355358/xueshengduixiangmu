package com.wyfx.aw.network;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wyfx.aw.network.vo.Pcmd;
import com.wyfx.aw.service.NodeInfoService;
import com.wyfx.aw.service.ServerInfoService;
import com.wyfx.aw.utils.ApplicationContextRegister;
import com.wyfx.aw.utils.CommandControlUtile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataHandler {
    private static Logger logger=LoggerFactory.getLogger(DataHandler.class);
    private NodeInfoService nodeInfoService=ApplicationContextRegister.getApplicationContext().getBean(NodeInfoService.class);
    private ServerInfoService serverInfoService=ApplicationContextRegister.getApplicationContext().getBean(ServerInfoService.class);




    /**
     * 基础服务更新
     * @param pcmd
     */
    public void updateService(Pcmd pcmd){
        try {
            CommandControlUtile.state = true;
            System.out.println("**************************************************************************");
//            JSONObject jsonObject=JSONObject.parseObject(new String(pcmd.getDataBuf(),"utf8"));
//            logger.info("NetWork信息:"+jsonObject);
//            serverInfoService.updateServerAttribute(pcmd.getIdst(),jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 添加蜜罐服务器属性
     * @param pcmd
     */
    public void addNetWork(Pcmd pcmd){

        System.out.println("*****************************"+pcmd);
        System.out.println("*****************************"+pcmd.getDataBuf());
        try {
            JSONObject jsonObject=JSONObject.parseObject(new String(pcmd.getDataBuf(),"utf8"));
            System.out.println("========================================"+jsonObject);
            serverInfoService.updateServerAttribute(pcmd.getIdst(),jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 改变暗网节点服务的开启(关闭)状态
     * @param pcmd
     */
    public void updateNetWork(Pcmd pcmd){
        try {
              CommandControlUtile.state = true;
//            JSONObject jsonObject=JSONObject.parseObject(new String(pcmd.getDataBuf(),"utf8"));
//            logger.info("NetWork信息:"+jsonObject);
//            serverInfoService.updateServerAttribute(pcmd.getIdst(),jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 改变暗网Web服务的开启(关闭)状态
     * @param pcmd
     */
    public void updateWebServer(Pcmd pcmd){
        try {
              CommandControlUtile.state = true;
//            JSONObject jsonObject=JSONObject.parseObject(new String(pcmd.getDataBuf(),"utf8"));
//            logger.info("NetWork信息:"+jsonObject);
//            serverInfoService.updateServerAttribute(pcmd.getIdst(),jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 切换流量服务运行状态
     * @param pcmd
     */
    public void updateFlowServer(Pcmd pcmd){
        try {
              CommandControlUtile.state = true;
//            JSONObject jsonObject=JSONObject.parseObject(new String(pcmd.getDataBuf(),"utf8"));
//            logger.info("NetWork信息:"+jsonObject);
//            serverInfoService.updateServerAttribute(pcmd.getIdst(),jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 开启或关闭攻击服务
     * @param pcmd
     */
    public void updateAttackServer(Pcmd pcmd){
        try {
              CommandControlUtile.state = true;
//            JSONObject jsonObject=JSONObject.parseObject(new String(pcmd.getDataBuf(),"utf8"));
//            logger.info("NetWork信息:"+jsonObject);
//            serverInfoService.updateServerAttribute(pcmd.getIdst(),jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 增加服务器节点信息
     * @param pcmd
     */
    public void addNodeServer(Pcmd pcmd){
        try {
            JSONObject jsonObject=JSONObject.parseObject(new String(pcmd.getDataBuf(),"utf8"));
            logger.info("NodeServer信息:"+new String(pcmd.getDataBuf(),"utf8"));
            serverInfoService.insertNodeServer(pcmd.getIdst(),jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 增加蜜罐暗网节点服务数据
     * @param pcmd
     */
    public void addWebServer(Pcmd pcmd){
        try {
           JSONObject jsonObject=JSONObject.parseObject(new String(pcmd.getDataBuf(),"utf8"));
            logger.info("WebServer信息:"+new String(pcmd.getDataBuf(),"utf8"));
            serverInfoService.insertWebServer(pcmd.getIdst(),jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 蜜罐服务器中节点流量详情
     * @param pcmd
     */
    public void addFlowServer(Pcmd pcmd){
        try {
           JSONObject jsonObject=JSONObject.parseObject(new String(pcmd.getDataBuf(),"utf8"));
            logger.info("FlowServer信息:"+new String(pcmd.getDataBuf(),"utf8"));
            serverInfoService.insertFlowDetail(pcmd.getIdst(),jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 增加暗网攻击管理数据
     * @param pcmd
     */
    public void addAttackManager(Pcmd pcmd){
        try {
            JSONObject jsonObject=JSONObject.parseObject(new String(pcmd.getDataBuf(),"utf8"));
            serverInfoService.insertAttackManager(pcmd.getIdst(),jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 处理添加节点信息业务
     * @param pcmd
     */
    public void addNodeInfo(Pcmd pcmd){
        try {
           /* JSONObject jsonObject=JSONObject.parseObject(new String(pcmd.getDataBuf(),"utf8"));
            serverInfoService.insertNodeServer(pcmd.getIdst(),jsonObject);*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 上传文件
     * @param pcmd
     */
    public void addFileupload(Pcmd pcmd) {
        try {
            CommandControlUtile.state = true;
            System.out.println("***********************************");
            System.out.println(pcmd.getDataBuf());
//            JSONObject jsonObject=JSONObject.parseObject(new String(pcmd.getDataBuf(),"utf8"));
//            logger.info("NetWork信息:"+jsonObject);
//            serverInfoService.fileInsert(pcmd.getIdst(),jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     /**
     * 服务器硬件与网络属性
     * @param pcmd
     */
    public void addAwServerAttribute(Pcmd pcmd){
        try {
            CommandControlUtile.state = true;
            System.out.println("***********************************");
            System.out.println(pcmd.getDataBuf());
            JSONObject jsonObject=JSONObject.parseObject(new String(pcmd.getDataBuf(),"utf8"));
            logger.info("NetWork信息:"+jsonObject);
            serverInfoService.insertAwServerAttribute(pcmd.getIdst(),jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 服务器所部署服务
     * @param pcmd
     */
    public void addAwFunctionServer(Pcmd pcmd){
        try {
            CommandControlUtile.state = true;
            System.out.println("***********************************");
            System.out.println(pcmd.getDataBuf());
            JSONObject jsonObject=JSONObject.parseObject(new String(pcmd.getDataBuf(),"utf8"));
            logger.info("NetWork信息:"+jsonObject);
            serverInfoService.insertAwFunctionServer(pcmd.getIdst(),jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     *  运行日志消息获取
     * @param pcmd
     */
    public void addAwLogging(Pcmd pcmd){
        try {
            CommandControlUtile.state = true;
            System.out.println("***********************************");
            System.out.println(pcmd.getDataBuf());
//            JSONObject jsonObject=JSONObject.parseObject(new String(pcmd.getDataBuf(),"utf8"));
//            logger.info("NetWork信息:"+jsonObject);
//            serverInfoService.insertAwLogging(pcmd.getIdst(),jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 上传流量文件
     * @param pcmd
     */
    public void addAnalysisRecord(Pcmd pcmd){
        try {
            CommandControlUtile.state = true;
            System.out.println("***********************************");
            System.out.println(pcmd.getDataBuf());

            JSONObject jsonObject=JSONObject.parseObject(new String(pcmd.getDataBuf(),"utf8"));
            logger.info("NetWork信息:"+jsonObject);
            serverInfoService.insertAnalysisRecord(pcmd.getIdst(),jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 分析中的流量启动命令
     * @param pcmd
     */
    public void addAnalysisRecordStart(Pcmd pcmd){
        try {
            CommandControlUtile.state = true;
//            System.out.println("***********************************");
//            System.out.println(pcmd.getDataBuf());
//            JSONObject jsonObject=JSONObject.parseObject(new String(pcmd.getDataBuf(),"utf8"));
//            logger.info("NetWork信息:"+jsonObject);
//            serverInfoService.insertAnalysisRecord(pcmd.getIdst(),jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 分析中的流量启动命令
     * @param pcmd
     */
    public void addAnalysisRecordStop(Pcmd pcmd){
        try {
            CommandControlUtile.state = true;
//            System.out.println("***********************************");
//            System.out.println(pcmd.getDataBuf());
//            JSONObject jsonObject=JSONObject.parseObject(new String(pcmd.getDataBuf(),"utf8"));
//            logger.info("NetWork信息:"+jsonObject);
//            serverInfoService.insertAnalysisRecord(pcmd.getIdst(),jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 分析中的流量停止命令
     * @param pcmd
     */
    public void addAnalysisRecordClose(Pcmd pcmd){
        try {
            CommandControlUtile.state = true;
//            System.out.println("***********************************");
//            System.out.println(pcmd.getDataBuf());
//            JSONObject jsonObject=JSONObject.parseObject(new String(pcmd.getDataBuf(),"utf8"));
//            logger.info("NetWork信息:"+jsonObject);
//            serverInfoService.insertAnalysisRecord(pcmd.getIdst(),jsonObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
