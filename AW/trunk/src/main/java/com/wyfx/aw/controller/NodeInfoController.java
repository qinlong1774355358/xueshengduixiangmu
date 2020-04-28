package com.wyfx.aw.controller;

import com.github.pagehelper.PageInfo;
import com.wyfx.aw.controller.commons.ResponseCode;
import com.wyfx.aw.controller.commons.ResponseEntity;
import com.wyfx.aw.entity.NodeInfo;
import com.wyfx.aw.network.queue.MessageQueue;
import com.wyfx.aw.network.vo.CmdUtil;
import com.wyfx.aw.network.vo.Pcmd;
import com.wyfx.aw.service.NodeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/aw/nodeInfo")
public class NodeInfoController {

    @Autowired
    private NodeInfoService nodeInfoService;

    /**
     * 发送开启节点命令
     */
    @RequestMapping(value = "/startNode",method = RequestMethod.POST)
    public Object startNode(Integer nodeId,Integer serverId) {
        try {
            Byte cmd=CmdUtil.START_NODE;
            byte[] bytes=null;
            Pcmd pcmd=new Pcmd(serverId,(int)cmd,0,0,bytes);
            MessageQueue.sendMessage(serverId,pcmd);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity(ResponseCode.ERROR_SYS.getValue(),"开启节点失败");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue());
    }

    /**
     * 发送重启节点命令
     */
    @RequestMapping(value = "/restartNode",method = RequestMethod.POST)
    public Object restartNode(Integer nodeId,Integer serverId) {
        try {
            Byte cmd=CmdUtil.RESTART_NODE;
            byte[] bytes=null;
            Pcmd pcmd=new Pcmd(serverId,(int)cmd,0,0,bytes);
            MessageQueue.sendMessage(serverId,pcmd);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity(ResponseCode.ERROR_SYS.getValue(),"重启节点失败");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue());
    }

    /**
     * 发送关闭节点命令
     */
    @RequestMapping(value = "/stopNode",method = RequestMethod.POST)
    public Object stopNode(Integer nodeId,Integer serverId) {
        try {
            Byte cmd=CmdUtil.STOP_NODE;
            byte[] bytes=null;
            Pcmd pcmd=new Pcmd(serverId,(int)cmd,0,0,bytes);
            MessageQueue.sendMessage(serverId,pcmd);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity(ResponseCode.ERROR_SYS.getValue(),"重启节点失败");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue());
    }


    /**
     * 查询蜜罐服务器下的节点列表
     */
    @RequestMapping(value = "/findNodeInfoByServerId",method = RequestMethod.POST)
    public Object findNodeInfoByServerId(Integer pageNum, Integer pageSize,Integer serverId) {
        PageInfo pageInfo=null;
        try {
            pageInfo= nodeInfoService.findNodeInfoById(pageNum,pageSize,serverId);
            System.out.println(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity(ResponseCode.ERROR_SYS.getValue(),"查询异常");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),pageInfo);
    }

    /**
     * 查询所有节点列表
     */
    @RequestMapping(value = "/findNodeInfoManager",method = RequestMethod.POST)
    public Object findNodeInfoManager(Integer pageNum, Integer pageSize) {
        PageInfo pageInfo=null;
        try {
            pageInfo= nodeInfoService.findNodeInfoManager(pageNum,pageSize);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity(ResponseCode.ERROR_SYS.getValue(),"查询异常");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),pageInfo);
    }

    /**
     * 节点详情查询
     * @param nodeId
     * @return
     */
    @RequestMapping(value = "/findNodeDetail",method = RequestMethod.POST)
    public Object findNodeDetail(Integer nodeId,Integer pageNum, Integer pageSize) {
        PageInfo pageInfo=null;
        try {
            pageInfo= nodeInfoService.findNodeDetail(nodeId,pageNum,pageSize);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity(ResponseCode.ERROR_SYS.getValue(),"查询异常");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),pageInfo);
    }

    /**
     * 下载流量文件
     * @return
     */
    @RequestMapping(value = "/downloadFlowFile",method = RequestMethod.GET)
    public Object downloadFlowFile(Integer flowId, HttpServletRequest request, HttpServletResponse response) {



        return new ResponseEntity(ResponseCode.SUCCESS.getValue());
    }

}
