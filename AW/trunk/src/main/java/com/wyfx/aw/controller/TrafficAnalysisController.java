package com.wyfx.aw.controller;

import com.wyfx.aw.controller.commons.ResponseCode;
import com.wyfx.aw.controller.commons.ResponseEntity;
import com.wyfx.aw.entity.AnalysisRecord;
import com.wyfx.aw.entity.FlowDetail;
import com.wyfx.aw.network.queue.MessageQueue;
import com.wyfx.aw.network.vo.CmdUtil;
import com.wyfx.aw.network.vo.Pcmd;
import com.wyfx.aw.service.TrafficAnalysisServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;


/**
 * @ClassName: TrafficAnalysisController
 * @Description: 蜜罐流量分析控制
 * @author: zhangguliang
 * @date: 2019-11-18
 */
@RestController
@RequestMapping("/aw/trafficAnalysis")
public class TrafficAnalysisController {
    @Autowired
    private TrafficAnalysisServer trafficAnalysisServer;

    /**
     * 流量包列表查询
     * @param flowDetail
     * @return
     */
    @RequestMapping(value = "/getFlowDetail",method = RequestMethod.GET)
    public Object getFlowDetail(FlowDetail flowDetail) throws Exception {
        List<FlowDetail> flowDetailList= trafficAnalysisServer.selectFlowDetail(flowDetail);
       List<Map> list = new ArrayList<>();
        if(flowDetailList.size()<1){
            new ResponseEntity(ResponseCode.SUCCESS.getValue(),"重新输入查询");
        }
        for (FlowDetail flowDetail1:flowDetailList) {
            Map map = new HashMap();
            Field[]fields = flowDetail1.getClass().getDeclaredFields();
            for (Field field:fields) {
                String type = field.getGenericType().toString();
                    Object value = flowDetail1.getClass().getMethod("get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1)).invoke(flowDetail1);
                    map.put(field.getName(),value);
            }
                    map.remove("exitIpAddr");
                    map.remove("path");
                    map.remove("fileName");
                    map.remove("fileSize");
                    map.remove("offset");
            list.add(map);
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),list);
    }

    /**
     * 分析中的流量与分析完成的流量列表
     * @param flowDetail
     * @return
     */
    @RequestMapping(value = "/getFlowDetailByID",method = RequestMethod.GET)
    public Object getFlowDetailByID(FlowDetail flowDetail) {
       if(null==flowDetail.getServerId()){
           return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"请输入服务器id");
       }
//       String[] str = id.split(",");
//       List<Integer> ids = new ArrayList<Integer>();
//       for (int i=0;i<str.length;i++){
//           ids.add(Integer.parseInt(str[i]));
//       }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(), trafficAnalysisServer.selectFlowDetail(flowDetail));
    }

    /**
     * 蜜罐流量分析结果
     * @param analysisRecord
     * @return
     */
    @RequestMapping(value = "/getAnalysisRecord",method = RequestMethod.GET)
    public Object getAnalysisRecord(AnalysisRecord analysisRecord) {
        if(null==analysisRecord.getServerId()){
            return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"服务器不正确");
        }
        analysisRecord.setStatus(0);
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),trafficAnalysisServer.selectAnalysisRecord(analysisRecord));
    }
    /**
     * 分析结果记录添加
     * @param analysisRecord
     * @return
     */
    @RequestMapping(value = "/addAnalysisRecord",method = RequestMethod.POST)
    public Object addAnalysisRecord(AnalysisRecord analysisRecord) {
        String messate = "添加失败";
        if(null==analysisRecord.getServerId()){
            return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"服务器不正确");
        }
        System.out.println(analysisRecord.toString());
       boolean fig = trafficAnalysisServer.insertAnalysisRecord(analysisRecord);
        if(fig){
            messate = "添加成功";
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),messate);
    }

    /**
     * 流量包列表启动分析命令
     * @param serverId
     * @return
     */
    @RequestMapping(value = "/startAnalysis",method = RequestMethod.GET)
    public Object getAnalysisRecord(int serverId) {
        byte[] bytes= null;
        Pcmd pcmd=new Pcmd(serverId,(int) CmdUtil.FLOW_FILE_UPLOAD,0,0,bytes);
        try {
            MessageQueue.sendMessage(serverId,pcmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"启动分析命令");
    }

    /**
     * 分析中的流量启动
     * @param serverId
     * @return
     */
    @RequestMapping(value = "/analysisRecordStart",method = RequestMethod.GET)
    public Object getAnalysisRecordStart(int serverId) {
        byte[] bytes= null;
        Pcmd pcmd=new Pcmd(serverId,(int) CmdUtil.START_FLOW,0,0,bytes);
        try {
            MessageQueue.sendMessage(serverId,pcmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"分析中的流量启动命令");
    }

    /**
     * 分析中的流量停止
     * @param serverId
     * @return
     */
    @RequestMapping(value = "/analysisRecordStop",method = RequestMethod.GET)
    public Object getAnalysisRecordStop(int serverId) {
        byte[] bytes= null;
        Pcmd pcmd=new Pcmd(serverId,(int) CmdUtil.STOP_FLOW,0,0,bytes);
        try {
            MessageQueue.sendMessage(serverId,pcmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"分析中的流量停止命令");
    }

    /**
     * 分析中的流量关闭任务
     * @param serverId
     * @return
     */
    @RequestMapping(value = "/analysisRecordClose",method = RequestMethod.GET)
    public Object getAnalysisRecordClose(int serverId) {
        byte[] bytes= null;
        Pcmd pcmd=new Pcmd(serverId,(int) CmdUtil.END_FLOW,0,0,bytes);
        try {
            MessageQueue.sendMessage(serverId,pcmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"分析中的流量关闭任务命令");
    }

}
