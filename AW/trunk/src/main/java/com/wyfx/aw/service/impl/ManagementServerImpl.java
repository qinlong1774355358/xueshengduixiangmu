package com.wyfx.aw.service.impl;

import com.wyfx.aw.dao.BasicServicesMapper;
import com.wyfx.aw.entity.BasicServices;
import com.wyfx.aw.network.queue.MessageQueue;
import com.wyfx.aw.network.vo.CmdUtil;
import com.wyfx.aw.network.vo.Pcmd;
import com.wyfx.aw.service.ManagementServer;
import com.wyfx.aw.utils.CommandControlUtile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: ManagementServer
 * @Description: 管理控制业务实现
 * @author: zhangguliang
 * @date: 2019-11-7
 */
@Service
@Transactional
public class ManagementServerImpl implements ManagementServer {

    @Autowired
    private  BasicServicesMapper basicServicesMapper;

    /**
     * 基础服务实现业务查询
     * @param basicServices
     * @return
     */
    @Override
    public BasicServices selectBasicServices(BasicServices basicServices) {
        return basicServicesMapper.selectBasicServices(basicServices);
    }
    /**
     * 基础服务实现业务添加
     * @param basicServices
     * @return
     */
    @Override
    public boolean insertBasicServices(BasicServices basicServices) {
        int count = basicServicesMapper.insertSelective(basicServices);
        if(count<1){
            return false;
        }
        return true;
    }
    /**
     * 基础服务实现业务修改
     * @param basicServices
     * @return
     */
    @Override
    public boolean updateBasicServices(BasicServices basicServices) {
        int cmd=(basicServices.getControlServiceStatus()==0)? CmdUtil.CONTROL_SERVICE_START:CmdUtil.CONTROL_SERVICE_STOP;
        byte[] bytes=null;
        try {
            Pcmd pcmd=new Pcmd(basicServices.getServerId(),(int)cmd,0,0,bytes);
            MessageQueue.sendMessage(basicServices.getServerId(),pcmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(null==basicServices.getServerId()){
            return false;
        }
        if(CommandControlUtile.state){
            int count = basicServicesMapper.updateBasicServices(basicServices);
            if(count<1){
                return false;
            }
            CommandControlUtile.state = false;
        }else{
            return false;
        }
        return true;
    }
}
