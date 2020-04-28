package com.wyfx.aw.controller;

import com.github.pagehelper.PageInfo;
import com.wyfx.aw.controller.commons.ResponseCode;
import com.wyfx.aw.controller.commons.ResponseEntity;
import com.wyfx.aw.entity.ServerInfo;
import com.wyfx.aw.entity.vo.ServerInfoVo;
import com.wyfx.aw.network.DispatcherHandler;
import com.wyfx.aw.network.queue.Message;
import com.wyfx.aw.service.ServerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 服务器信息
 */
@RestController
@RequestMapping("/aw/serverInfo")
public class ServerInfoController {

    @Autowired
    private ServerInfoService serverInfoService;

    /**
     * 获取蜜罐服务器的运行状态信息
     * @param serverId
     * @return
     */
    @RequestMapping(value = "/getServerStatus",method = RequestMethod.GET)
    public Object getServerStatus(Integer serverId){
        try {
            ServerInfoVo serverInfoVo= serverInfoService.findServerStatus(serverId);
            return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),serverInfoVo);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(ResponseCode.ERROR_SYS.getValue(), "查询异常");
        }
    }

    /**
     * 分页查询蜜罐服务器列表信息
     */
    @RequestMapping(value = "/findAvailableServer",method = RequestMethod.POST)
    public Object findAvailableServer(Integer pageNum,Integer pageSize) {
        PageInfo pageInfo=null;
        try {
            int isDelete=0;
            pageInfo= serverInfoService.findAvailableServer(isDelete,pageNum,pageSize);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity(ResponseCode.ERROR_SYS.getValue(),"查询异常");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),pageInfo);
    }

    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    public Object deleteById(Integer id) {
        try {
            serverInfoService.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity(ResponseCode.ERROR_SYS.getValue(),"删除蜜罐服务器失败");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue());
    }

    @RequestMapping(value = "/updateServerName",method = RequestMethod.POST)
    public Object updateServerName(Integer id,String serverName) {
        try {
            serverInfoService.updateServerName(id,serverName);
        }catch (Exception e){
            e.printStackTrace();
            return  new ResponseEntity(ResponseCode.ERROR_SYS.getValue(),"更新名称异常");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue());
    }

    /**
     * 建立新的蜜罐服务器连接
     * @param host
     * @param port
     * @return
     */
    @RequestMapping(value = "/createTcpConn",method = RequestMethod.POST)
    public Object createTcpConn(String host, Integer port, int serverId){
        try {
            System.out.println("主服务器连接测试===");
            System.out.println(host);
            System.out.println(port);
            System.out.println(serverId);
            DispatcherHandler.setServerid(serverId);
            serverInfoService.addServerList(host,port);
        }catch (Exception e) {
            e.printStackTrace();
            
            return new ResponseEntity(ResponseCode.ERROR_SYS.getValue(), "建立连接失败");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue());
    }

    @RequestMapping(value = "/getNetWorkInfo",method = RequestMethod.POST)
    public Object getNetWorkInfo(Integer id){
        Message message=null;
        try {
            byte[] bytes="发送命令测试".getBytes("utf8");
            /*Pcmd pcmd=new Pcmd(id,CmdUtil.NETWORK_CMD,0,0,bytes);
            MessageQueue.sendMessage(id,pcmd);
            message= MessageQueue.getMessage(pcmd);*/
            System.out.println("message:"+message);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(ResponseCode.ERROR_SYS.getValue(), "发送命令失败");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),message);
    }

    /**
     * 更改蜜罐服务器的控制状态
     * @param serverId
     * @return
     */
    @RequestMapping(value = "/changeServerStatus",method = RequestMethod.POST)
    public Object changeServerStatus(Integer serverId,Integer status){
        try {
            ServerInfo serverInfo= serverInfoService.findServerById(serverId);
            System.out.println("****************************************************************"+serverInfo);
            if(status==0){
                //建立连接
                serverInfoService.addServerList(serverInfo.getHost(),serverInfo.getPort());
                System.out.println("===============================777777777777777777===========================================");
            }else if(status==1){
                //断开连接
                serverInfoService.closeTcpConnection(serverId);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(ResponseCode.ERROR_SYS.getValue(), "更改状态失败");
        }
        System.out.println("===============================8888888888888888888===========================================");
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue());

    }




}
