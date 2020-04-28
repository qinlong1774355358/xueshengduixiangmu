package com.wyfx.aw.controller;

import com.wyfx.aw.controller.commons.ResponseCode;
import com.wyfx.aw.controller.commons.ResponseEntity;
import com.wyfx.aw.entity.AnalysisRecord;
import com.wyfx.aw.entity.FlowDetail;
import com.wyfx.aw.entity.ImportPackage;
import com.wyfx.aw.network.queue.MessageQueue;
import com.wyfx.aw.network.vo.CmdUtil;
import com.wyfx.aw.network.vo.Pcmd;
import com.wyfx.aw.service.ImportPackageServer;
import com.wyfx.aw.service.TrafficAnalysisServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: ImportPackageController
 * @Description: 导入流量分析控制
 * @author: zhangguliang
 * @date: 2019-11-18
 */
@RestController
@RequestMapping("/aw/importPackage")
public class ImportPackageController {
    @Autowired
    private ImportPackageServer importPackageServer;

    /**
     * 已导入流量包列表查询
     * @param importPackage
     * @return
     */
    @RequestMapping(value = "/getImportPackage",method = RequestMethod.GET)
    public Object getImportPackage(ImportPackage importPackage) throws Exception {
        List<ImportPackage> importPackageList= importPackageServer.selectImportPackage(importPackage);
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),importPackageList);
    }

    /**
     * 分析中的流量与分析完成的流量列表
     * @param importPackage
     * @return
     */
    @RequestMapping(value = "/getImportPackageByID",method = RequestMethod.GET)
    public Object getImportPackageByID(ImportPackage importPackage) {
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(), importPackageServer.selectImportPackage(importPackage));
    }

    /**
     * 导入流量分析结果
     * @param analysisRecord
     * @return
     */
    @RequestMapping(value = "/getImportAnalysisRecord",method = RequestMethod.GET)
    public Object getAnalysisRecord(AnalysisRecord analysisRecord) {
        analysisRecord.setStatus(1);
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),importPackageServer.selectAnalysisRecord(analysisRecord));
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
       boolean fig = importPackageServer.insertAnalysisRecord(analysisRecord);
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
