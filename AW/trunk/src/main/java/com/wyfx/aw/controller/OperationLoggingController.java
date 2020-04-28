package com.wyfx.aw.controller;

import com.wyfx.aw.controller.commons.ResponseCode;
import com.wyfx.aw.controller.commons.ResponseEntity;
import com.wyfx.aw.entity.AwLogging;
import com.wyfx.aw.entity.AwServerInfo;
import com.wyfx.aw.network.queue.Message;
import com.wyfx.aw.network.queue.MessageQueue;
import com.wyfx.aw.network.vo.CmdUtil;
import com.wyfx.aw.network.vo.Pcmd;
import com.wyfx.aw.service.BasicAttributesServer;
import com.wyfx.aw.service.OperationLoggingServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName: OperationLoggingController
 * @Description: 运行日志
 * @author: zhangguliang
 * @date: 2019-11-12
 */
@RestController
@RequestMapping("/aw/operationLogging")
public class OperationLoggingController {

    @Autowired
    private OperationLoggingServer operationLoggingServer;
    /**
     * 日志信息表查询
     * @return
     */
    @RequestMapping(value = "/getAwLogging",method = RequestMethod.GET)
    public Object getAwLogging(String serverId) {
        if(null==serverId){
            new ResponseEntity(ResponseCode.SUCCESS.getValue(),"请输入serverId");
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),operationLoggingServer.selectAllAwLogging(serverId));
    }
    /**
     * 根据id查询所有详情日志信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/getAwFunctionServerByserverId",method = RequestMethod.GET)
    public Object getAwLoggingByserverId(String id) {
       if(null==id){
           return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"请输入id");
       }
       return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),operationLoggingServer.selectAwLoggingByPrmaryKey(Integer.parseInt(id)));
    }

    /**
     * 日志信息命令
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
            Pcmd pcmd=new Pcmd(Integer.parseInt(serverId), CmdUtil.LOGGING,0,0,bytes);
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
     * 日志信息添加
     * @param awLogging 日志信息对象
     * @return
     */
    @RequestMapping(value = "/addAwLogging",method = RequestMethod.POST)
    public Object addAwLogging(AwLogging awLogging) {
       if(null==awLogging.getServerId()){
           return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"请输入服务器id");
       }
        String message="失败";

        try {
            if (operationLoggingServer.addAwLogging(awLogging)){
                message = "成功";
            };
        }catch (Exception e){
            e.printStackTrace();
        }
       return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),message);
    }

}
