package com.wyfx.aw.controller;

import com.wyfx.aw.controller.commons.ResponseCode;
import com.wyfx.aw.controller.commons.ResponseEntity;
import com.wyfx.aw.entity.AwServerInfo;
import com.wyfx.aw.network.queue.Message;
import com.wyfx.aw.network.queue.MessageQueue;
import com.wyfx.aw.network.vo.CmdUtil;
import com.wyfx.aw.network.vo.Pcmd;
import com.wyfx.aw.service.BasicAttributesServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * @ClassName: BasicAttributesController
 * @Description: 基本属性
 * @author: zhangguliang
 * @date: 2019-11-12
 */
@RestController
@RequestMapping("/aw/basicAttributes")
public class BasicAttributesController {
    @Autowired
    private BasicAttributesServer basicAttributesServer;
    /**
     * 蜜罐服务器信息表查询
     * @param request
     * @return
     */
    @RequestMapping(value = "/getBasicAttributes",method = RequestMethod.GET)
    public Object getBasicAttributes(HttpServletRequest request) {
        Map map = new HashMap();
        System.out.println(map.get("serverId"));
        if(null==request.getParameter("serverId")){
           return new ResponseEntity(ResponseCode.SUCCESS.getValue(),"请输入服务号id");
        }
        map.put("serverId",request.getParameter("serverId"));
        Map map1= basicAttributesServer.selectBasicAttributes(map);
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),map1);
    }

    /**
     * 根据serverId查询所有部署服务信息
     * @param serverId
     * @return
     */
    @RequestMapping(value = "/getAwFunctionServerByServerId",method = RequestMethod.GET)
    public Object getAwFunctionServerByServerId(String serverId) {
       if(null==serverId){
           return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"请输入服务器id");
       }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),basicAttributesServer.selectAwFunctionServer(serverId));
    }
     /**
         * 获取所有部署服务信息命令
         * @param serverId
         * @return
         */
        @RequestMapping(value = "/addAwFunctionServerByServerId",method = RequestMethod.GET)
        public Object addAwFunctionServerByServerId(String serverId) {
           if(null==serverId){
               return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"请输入服务器id");
           }
            Message message=null;
            byte[] bytes=null;
            try {
                Pcmd pcmd=new Pcmd(Integer.parseInt(serverId), CmdUtil.SERVER_STATE,0,0,bytes);
                MessageQueue.sendMessage(Integer.parseInt(serverId),pcmd);
                message= MessageQueue.getMessage(pcmd);
                System.out.println("message:"+message);
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseEntity(ResponseCode.ERROR_SYS.getValue(), "发送命令失败");
            }
            return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),message);
    }

    /**
     * 修改蜜罐服务器信息
     * @param awServerInfo
     * @return
     */
    @RequestMapping(value = "/updateAwServerInfo",method = RequestMethod.POST)
    public Object updateAwServerInfo(AwServerInfo awServerInfo) {
        String message = "修改错误";
        if (basicAttributesServer.updateAwServerInfo(awServerInfo)){
            message = "修改成功";
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),message);
    }

}
