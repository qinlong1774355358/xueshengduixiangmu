package com.wyfx.aw.controller;

import com.wyfx.aw.controller.commons.ResponseCode;
import com.wyfx.aw.controller.commons.ResponseEntity;
import com.wyfx.aw.entity.*;
import com.wyfx.aw.entity.vo.NodeServerVo;
import com.wyfx.aw.network.queue.MessageQueue;
import com.wyfx.aw.network.vo.CmdUtil;
import com.wyfx.aw.network.vo.Pcmd;
import com.wyfx.aw.service.ServerInfoService;
import com.wyfx.aw.utils.CommandControlUtile;
import com.wyfx.aw.utils.SocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/aw/serverDetail")
public class ServerDetailController {

    @Autowired
    private ServerInfoService serverInfoService;



    /**
     * 查询服务器属性
     * @param serverId
     * @return
     */
    @RequestMapping(value = "/findServerAttributeById",method = RequestMethod.POST)
    public Object findServerAttributeById(Integer serverId) {
        ServerAttribute serverAttribute=null;
        try {
            serverAttribute=serverInfoService.findServerAttributeById(serverId);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity(ResponseCode.ERROR_SYS.getValue(),"查询异常");
        }
        List<ServerAttribute> list=new ArrayList<>();
        list.add(serverAttribute);
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),list);
    }

    /**
     * 挂载服务查询
     * @param serverId
     * @return
     */
    @RequestMapping(value = "/findDeployServer",method = RequestMethod.POST)
    public Object findDeployServer(Integer serverId) {
        List<FunctionServer> list=null;
        try {
            list=serverInfoService.findFunctionServerById(serverId);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity(ResponseCode.ERROR_SYS.getValue(),"挂载服务查询失败");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),list);
    }

    /**
     * 查询流量回传设置
     * @param serverId
     * @return
     */
    @RequestMapping(value = "/findFlowSetting",method = RequestMethod.POST)
    public Object findFlowSetting(Integer serverId) {
        FlowSetting flowSetting=null;
        try {
            flowSetting=serverInfoService.findFlowSetting(serverId);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity(ResponseCode.ERROR_SYS.getValue(),"流量回传配置信息查询失败");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),flowSetting);
    }

    /**
     * 攻击服务配置信息查询
     * @param serverId
     * @return
     */
    @RequestMapping(value = "/findAttackServer",method = RequestMethod.POST)
    public Object findAttackServer(Integer serverId) {
        AttackManager attackManager=null;
        try {
            attackManager=serverInfoService.findAttackByserverId(serverId);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity(ResponseCode.ERROR_SYS.getValue(),"攻击服务配置信息查询失败");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),attackManager);
    }

    /**
     * 暗网节点服务查询
     * @param serverId
     * @return
     */
    @RequestMapping(value = "/findNodeServer",method = RequestMethod.POST)
    public Object findNodeServer(Integer serverId) {
        NodeServerVo nodeServerVo=null;
        try {
            nodeServerVo=serverInfoService.findNodeServer(serverId);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity(ResponseCode.ERROR_SYS.getValue(),"攻击服务配置信息查询失败");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),nodeServerVo);
    }

    /**
     * 查询蜜罐服务器web服务
     * @param serverId
     * @return
     */
    @RequestMapping(value = "/findWebServer",method = RequestMethod.POST)
    public Object findWebServer(Integer serverId) {
        List<WebServer> list=null;
        try {
            list=serverInfoService.findWebServerById(serverId);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity(ResponseCode.ERROR_SYS.getValue(),"攻击服务配置信息查询失败");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),list);
    }

    /**
     * 改变暗网节点服务的开启(关闭)状态
     */
    @RequestMapping(value = "/changeNodeServer",method = RequestMethod.POST)
    public Object changeNodeServer(Integer id,Integer serverId,Integer runningState){
        /*Byte cmd=(runningState==0)?CmdUtil.START_NODE_SERVER:CmdUtil.STOP_NODE_SERVER;*/
        int cmd=(runningState==0)?CmdUtil.START_NODE_SERVER:CmdUtil.STOP_NODE_SERVER;
        byte[] bytes=null;
        Pcmd pcmd=new Pcmd(serverId,(int)cmd,0,0,bytes);
        try {
            MessageQueue.sendMessage(serverId,pcmd);
            if (CommandControlUtile.state){
                serverInfoService.updateNodeServer(id,serverId,runningState,null);
                CommandControlUtile.state = false;
            }else{
                return new ResponseEntity(ResponseCode.ERROR_SYS.getValue(), "切换节点服务状态失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(ResponseCode.ERROR_SYS.getValue(), "切换节点服务状态失败");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue());
    }

    /**
     * 切换暗网服务
     */
    @RequestMapping(value = "/chooseNodeServer",method = RequestMethod.POST)
    public Object chooseNodeServer(Integer id,Integer serverId,Integer currentsupport){
        byte[] bytes= SocketUtil.intToBytesLH(currentsupport);
        Pcmd pcmd=new Pcmd(serverId,(int)CmdUtil.START_NODE_SERVER,bytes.length,0,bytes);
        try {
            //MessageQueue.sendMessage(serverId,pcmd);
            serverInfoService.updateNodeServer(id,serverId,null,currentsupport);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(ResponseCode.ERROR_SYS.getValue(), "切换暗网服务失败");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue());
    }

    /**
     * 改变暗网Web服务的开启(关闭)状态
     */
    @RequestMapping(value = "/changeWebServer",method = RequestMethod.POST)
    public Object changeWebServer(Integer serverId,Integer webserverId,Integer runningState){
        try {
            serverInfoService.changeWebServer(serverId,webserverId,runningState);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(ResponseCode.ERROR_SYS.getValue(), "切换web服务状态失败");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue());
    }

    /**
     * 切换流量服务运行状态
     */
    @RequestMapping(value = "/changeFlowServer",method = RequestMethod.POST)
    public Object changeFlowServer(Integer id,Integer serverId,Integer runningState){
        /*Byte cmd=(runningState==0)?CmdUtil.START_FLOW_SERVER:CmdUtil.STOP_FLOW_SERVER;*/
        int cmd=(runningState==0)?CmdUtil.START_FLOW_SERVER:CmdUtil.STOP_FLOW_SERVER;
        byte[] bytes=null;
        Pcmd pcmd=new Pcmd(serverId,(int)cmd,0,0,bytes);
        try {
            MessageQueue.sendMessage(serverId,pcmd);
            if (CommandControlUtile.state){
                serverInfoService.updateFlowServer(Long.valueOf(id.toString()),Long.parseLong(serverId.toString()),runningState,null);
                CommandControlUtile.state=false;
            }else{
                return new ResponseEntity(ResponseCode.ERROR_SYS.getValue(), "切换节点服务状态失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(ResponseCode.ERROR_SYS.getValue(), "切换节点服务状态失败");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue());
    }

    /**
     * 更改流量回传方式
     */
    @RequestMapping(value = "/chooseFlowType",method = RequestMethod.POST)
    public Object chooseFlowType(Integer id,Integer serverId,Integer flowType){
        byte[] bytes= SocketUtil.intToBytesLH(flowType);
        Pcmd pcmd=new Pcmd(serverId,(int)CmdUtil.START_NODE_SERVER,bytes.length,0,bytes);
        try {
            //MessageQueue.sendMessage(serverId,pcmd);
            serverInfoService.updateFlowServer(Long.valueOf(id.toString()),Long.parseLong(serverId.toString()),null,flowType);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(ResponseCode.ERROR_SYS.getValue(), "切换暗网服务失败");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue());
    }

    /**
     * 开启或关闭攻击服务
     */
    @RequestMapping(value = "/changeAttackServer",method = RequestMethod.POST)
    public Object changeAttackServer(Integer attckId,Integer serverId,Integer runningState){
        /*Byte cmd=(runningState==0)?CmdUtil.START_ATTACK:CmdUtil.STOP_ATTACK;*/
        int cmd=(runningState==0)?CmdUtil.START_ATTACK:CmdUtil.STOP_ATTACK;
        byte[] bytes=null;
        Pcmd pcmd=new Pcmd(serverId,(int)cmd,0,0,bytes);
        try {
            MessageQueue.sendMessage(serverId,pcmd);
            if(CommandControlUtile.state){
            serverInfoService.updateAttackSetting(Long.valueOf(attckId.toString()),Long.parseLong(serverId.toString()),runningState,null,null,null);
                CommandControlUtile.state=false;
            }else{
                return new ResponseEntity(ResponseCode.ERROR_SYS.getValue(), "切换节点服务状态失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(ResponseCode.ERROR_SYS.getValue(), "切换节点服务状态失败");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue());
    }

    /**
     * 更改攻击方式
     */
    @RequestMapping(value = "/chooseAttackType",method = RequestMethod.POST)
    public Object chooseAttackType(Integer attckId,Integer serverId,Integer attackWay, String minAddr, String maxAddr){
        byte[] bytes= SocketUtil.intToBytesLH(attackWay);
        Pcmd pcmd=new Pcmd(serverId,(int)CmdUtil.START_NODE_SERVER,bytes.length,0,bytes);
        try {
            //MessageQueue.sendMessage(serverId,pcmd);
            serverInfoService.updateAttackSetting(Long.valueOf(attckId.toString()),Long.parseLong(serverId.toString()),null,attackWay,minAddr,maxAddr);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(ResponseCode.ERROR_SYS.getValue(), "切换暗网服务失败");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue());
    }





}
