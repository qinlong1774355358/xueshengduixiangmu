package com.wyfx.aw.controller;

import com.wyfx.aw.controller.commons.ResponseCode;
import com.wyfx.aw.controller.commons.ResponseEntity;
import com.wyfx.aw.entity.BasicServices;
import com.wyfx.aw.service.ManagementServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName: ManagementController
 * @Description: 管理控制
 * @author: zhangguliang
 * @date: 2019-11-6
 */
@RestController
@RequestMapping("/aw/management")
public class ManagementController {
    @Autowired
    private ManagementServer managementServer;

    /**
     * 基础服务查询
     * @param basicServices
     * @return
     */
    @RequestMapping(value = "/getBasicServices",method = RequestMethod.GET)
    public Object getBasicServices(BasicServices basicServices) {
        if(null==basicServices.getServerId()){
            return new ResponseEntity(ResponseCode.SUCCESS.getValue(),"服务号错误");
        }
        BasicServices basicServices1= managementServer.selectBasicServices(basicServices);
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),basicServices1);
    }
    /**
     * 基础服务查询
     * @param basicServices
     * @return
     */
    @RequestMapping(value = "/addBasicServices",method = RequestMethod.POST)
    public Object addBasicServices(BasicServices basicServices) {
        String message = "添加错误";
        if(null==basicServices.getServerId()){
            return new ResponseEntity(ResponseCode.SUCCESS.getValue(),"服务号错误");
        }
       if (managementServer.insertBasicServices(basicServices)){
           message = "添加成功";
       };
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),message);
    }
    /**
     * 基础服务更新
     * @param basicServices
     * @return
     */
    @RequestMapping(value = "/updateBasicServices",method = RequestMethod.POST)
    public Object updateBasicServices(BasicServices basicServices) {
        String message = "添加错误";
        if(null==basicServices.getServerId()){
            return new ResponseEntity(ResponseCode.SUCCESS.getValue(),"服务号错误");
        }
        if (managementServer.updateBasicServices(basicServices)){
            message = "添加成功";
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),message);
    }

}
