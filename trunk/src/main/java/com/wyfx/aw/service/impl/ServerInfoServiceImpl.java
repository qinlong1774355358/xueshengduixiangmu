package com.wyfx.aw.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyfx.aw.dao.*;
import com.wyfx.aw.entity.*;
import com.wyfx.aw.entity.vo.NodeServerVo;
import com.wyfx.aw.entity.vo.ServerInfoVo;
import com.wyfx.aw.entity.vo.ServerListVo;
import com.wyfx.aw.network.AwClient;
import com.wyfx.aw.network.BootstrapHelper;
import com.wyfx.aw.network.queue.MessageQueue;
import com.wyfx.aw.network.vo.CmdUtil;
import com.wyfx.aw.network.vo.Pcmd;
import com.wyfx.aw.service.ServerInfoService;
import com.wyfx.aw.service.TrafficAnalysisServer;
import com.wyfx.aw.utils.CommandControlUtile;
import com.wyfx.aw.utils.GetAddressByIp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.netty.channel.Channel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class ServerInfoServiceImpl implements ServerInfoService {

    @Autowired
    private ServerInfoMapper serverInfoMapper;
    @Autowired
    private WebServerMapper webServerMapper;
    @Autowired
    private ServerAttributeMapper serverAttributeMapper;
    @Autowired
    private ServerListMapper serverListMapper;
    @Autowired
    private FunctionServerMapper functionServerMapper;
    @Autowired
    private AttackManagerMapper attackManagerMapper;
    @Autowired
    private FlowSettingMapper flowSettingMapper;
    @Autowired
    private FlowDetailMapper flowDetailMapper;
    @Autowired
    private NodeServerMapper nodeServerMapper;
    @Autowired
    private TrafficAnalysisServer trafficAnalysisServer;

    //蜜罐服务器属性
    @Autowired
    private AwServerAttributeMapper awServerAttributeMapper;

    //蜜罐服务器属性
    @Autowired
    private AwFunctionServerMapper awFunctionServerMapper;

    //日志记录表
    @Autowired
    private AwLoggingMapper awLoggingMapper;

    /**
     * 查询蜜罐服务器的运行状态信息
     * @param serverId
     * @return
     */
    @Override
    public ServerInfoVo findServerStatus(int serverId) {
        ServerInfo serverInfo= serverInfoMapper.selectByPrimaryKeyServerId(serverId);
        System.out.println(serverInfo);
        ServerInfoVo serverInfoVo=(serverInfo==null)?null:new ServerInfoVo(Integer.parseInt(serverInfo.getId().toString()),serverInfo.getName(),serverInfo.getConnStatus());
        return serverInfoVo;
    }

    /**
     * 关闭连接
     * @param serverId
     */
    @Override
    public void closeTcpConnection(Integer serverId) {
        Channel channel=(Channel)MessageQueue.channelMap.get(serverId);
        if(channel!=null){
            channel.closeFuture();
        }
    }

    /**
     * 添加蜜罐服务器信息
     * @param host
     */
    @Override
    public ServerInfo addServerInfo(String host,int port) throws  Exception{
        //JSONObject jsonObject=GetAddressByIp.getIpInfo(host);
        ServerInfo serverInfo=serverInfoMapper.selectByHostAndPort(host,port);
        if(serverInfo==null){
            long time=System.currentTimeMillis();
            serverInfo=new ServerInfo();
            long id=serverInfoMapper.selectMaxId()+1;
            String name=GetAddressByIp.getBase64Str(String.valueOf(time));
            serverInfo.setId(id);
//            serverInfo.setServerId(name);
            serverInfo.setName(name);
            //String local=(jsonObject==null)?"本地":jsonObject.getString("country")+"-"+jsonObject.getString("region");
            //serverInfo.setLocal(local);
            serverInfo.setHost(host);
            serverInfo.setPort(port);
            //serverInfoMapper.insertSelective(serverInfo);
        }
        return serverInfo;
    }

    /**
     * 分页查询有效的服务器列表信息
     * @param isDelete
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo findAvailableServer(int isDelete, int pageNum, int pageSize)throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        List<ServerListVo> list= serverInfoMapper.selectByIsDelete(isDelete);
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    /**
     * 根据ID删除服务器
     * @param id
     */
    @Override
    public void deleteById(int id)throws Exception {
        serverInfoMapper.updateById(id,1,1);
    }

    @Override
    public ServerInfo findServerById(int serverId)throws Exception {
        return serverInfoMapper.selectByPrimaryKeyServerId(serverId);
    }


    /**
     * 查询蜜罐服务器上所部署的web服务
     * @param id
     * @return
     */
    @Override
    public List<WebServer> findWebServerById(long id)throws Exception {
        return webServerMapper.selectByserverId(id);
    }

    /**
     * 根据ID查询服务器属性
     * @param id
     * @return
     */
    @Override
    public ServerAttribute findServerAttributeById(long id) throws Exception{
        return serverAttributeMapper.selectByserverId(id);
    }

    /**
     * 修改蜜罐服务器名称
     * @param id
     * @param serverName
     */
    @Override
    public void updateServerName(long id, String serverName) throws Exception{
        ServerInfo serverInfo=new ServerInfo();
        serverInfo.setId(id);
        serverInfo.setName(serverName);
        serverInfoMapper.updateByPrimaryKeySelective(serverInfo);
    }

    /**
     * 添加可用蜜罐服务器列表信息
     * @param ip
     * @param port
     * @throws Exception
     */
    @Override
    public void addServerList(String ip, int port) throws Exception {
        System.out.println("==============================1111111111111111====================================");
        if(serverListMapper.selectByIpAndPort(ip, port)==null){
            ServerList serverList=new ServerList();
            serverList.setPort(port);
            serverList.setServerAddr(ip);
            serverListMapper.insert(serverList);
        }
        System.out.println("==============================222222222222222222====================================");
        ServerInfo serverInfo=serverInfoMapper.selectByHostAndPort(ip,port);
        System.out.println("==============================3333333333333333333333====================================");
        if(serverInfo==null || serverInfo.getConnStatus()==null || serverInfo.getConnStatus()==0){
            new Thread(new AwClient(ip,port,BootstrapHelper.bootstrap)).start();
        }
        System.out.println("===============================66666666666666666===========================================");
    }
    /**
     * 根据蜜罐服务器id查询挂载服务
     * @param serverId
     * @return
     */
    @Override
    public List<FunctionServer> findFunctionServerById(long serverId) {
        List<FunctionServer> list= functionServerMapper.selectByserverId(serverId);
        return list;
    }

    @Override
    public AttackManager findAttackByserverId(long serverId) {
        return attackManagerMapper.selectByserverId(serverId);
    }

    @Override
    public FlowSetting findFlowSetting(long serverId) {
        return flowSettingMapper.selectByserverId(serverId);
    }

    @Override
    public NodeServerVo findNodeServer(long serverId) {
        NodeServer nodeServer= nodeServerMapper.selectByserverId(serverId);
        List list=new ArrayList();
        if(nodeServer.getIsTor()==0){
            list.add("TOR");
        }
        if(nodeServer.getIsI2p()==0){
            list.add("I2P");
        }
        if(nodeServer.getIsZeronet()==0){
            list.add("ZeroNet");
        }
        NodeServerVo nodeServerVo=new NodeServerVo();
        BeanUtils.copyProperties(nodeServer,nodeServerVo);
        nodeServerVo.setServerType(list.toString().replaceAll(",","/").replace("[","").replace("]",""));
        return nodeServerVo;
    }

    @Override
    public void updateNodeServer(Integer id,Integer serverId, Integer runningState, Integer currentsupport) {
        NodeServer nodeServer=new NodeServer();
        nodeServer.setId(Long.valueOf(id.toString()));
        nodeServer.setServerId(Long.valueOf(serverId.toString()));
        if(runningState!=null){
            nodeServer.setRunningState(runningState);
        }
        if(currentsupport!=null){
            nodeServer.setCurrentsupport(currentsupport);
        }
        nodeServerMapper.updateByserverIdSelective(nodeServer);
    }

    @Override
    public void changeWebServer(Integer serverId,Integer webServerId,Integer runningState) {
        /*Byte cmd=(runningState==0)?CmdUtil.START_WEB_SERVER:CmdUtil.STOP_WEB_SERVER;*/
        int cmd=(runningState==0)?CmdUtil.START_WEB_SERVER:CmdUtil.STOP_WEB_SERVER;
        byte[] bytes=null;
        try {
            Pcmd pcmd=new Pcmd(serverId,(int)cmd,0,0,bytes);
            System.out.println(pcmd);
            MessageQueue.sendMessage(serverId,pcmd);
            WebServer webServer=new WebServer(Long.valueOf(webServerId),Long.valueOf(serverId),null,runningState,null);
            if (CommandControlUtile.state){
                webServerMapper.updateByPrimaryKeySelective(webServer);
                CommandControlUtile.state = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateFlowServer(Long id,Long serverId, Integer runningState, Integer flowType) {
        FlowSetting flowSetting=new FlowSetting();
        flowSetting.setId(id);
        flowSetting.setServerId(serverId);
        try {
            if(runningState!=null){
                flowSetting.setRunningState(runningState);
            }
            if(flowType!=null){
                flowSetting.setFlowType(flowType);
            }
            flowSettingMapper.updateByPrimaryKeySelective(flowSetting);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void updateAttackSetting(Long id, Long serverId, Integer runningState, Integer attackWay, String minAddr, String maxAddr) {
        AttackManager attackManager=new AttackManager(id,serverId,attackWay,runningState,minAddr,maxAddr);
        attackManagerMapper.updateByPrimaryKeySelective(attackManager);
    }


    @Override
    public void updateServerAttribute(long serverId,JSONObject jsonObject) {
        ServerAttribute serverAttribute=JSON.parseObject(jsonObject.toString(), ServerAttribute.class);
        serverAttribute.setServerId(serverId);
        serverAttribute.setIp6Addr(jsonObject.get("port").toString());
        System.out.println("--------------"+serverAttribute.toString());
//        ServerAttribute serverAttribute=new ServerAttribute(null,serverId,jsonObject.getString("cpu"),jsonObject.getString("hard_disk"),jsonObject.getString("ram"),jsonObject.getString("ip4_addr"),jsonObject.getString("ip6_addr"),jsonObject.getString("system"));
        int num= serverAttributeMapper.updateByserverId(serverAttribute);
        if(num<1){
            System.out.println("更新失败后，改为添加数据");
            serverAttributeMapper.insertSelective(serverAttribute);
        }
    }

    @Override
    public void insertNodeServer(long serverId, JSONObject jsonObject) {
        System.out.println("1================================================="+jsonObject.toString());
        NodeServer nodeServer= JSON.parseObject(jsonObject.toString(), NodeServer.class);
        nodeServer.setServerId(serverId);
        System.out.println(nodeServer.toString());
        if(null!=nodeServerMapper.selectByserverId(serverId)){
            nodeServerMapper.updateByserverIdSelective(nodeServer);
            return;
        }
//        NodeServer nodeServer=new NodeServer(null,serverId,jsonObject.getInteger("isTor"),jsonObject.getInteger("is_i2p"),jsonObject.getInteger("is_zeronet"),jsonObject.getInteger("currentSupport"),jsonObject.getInteger("runningState"));
        nodeServerMapper.insertSelective(nodeServer);
    }

    @Override
    public void insertWebServer(long serverId, JSONObject jsonObject) {
        System.out.println("2================================================="+jsonObject.toString());
        WebServer webServer= JSON.parseObject(jsonObject.toString(), WebServer.class);
        webServer.setServerId(serverId);
        if (null!=webServerMapper.selectByserverId(serverId)){
            webServer.setId(webServerMapper.selectByserverId(serverId).get(0).getId());
            webServerMapper.updateByPrimaryKeySelective(webServer);
            return;
        }
        System.out.println(webServer.toString());
        webServerMapper.insertSelective(webServer);
    }

    @Override
    public void insertFlowDetail(long serverId, JSONObject jsonObject) {
        System.out.println("3================================================="+jsonObject.toString());
        FlowDetail flowDetail= JSON.parseObject(jsonObject.toString(), FlowDetail.class);
        flowDetail.setServerId((int)serverId);
        System.out.println(flowDetail.toString());
        flowDetailMapper.insertSelective(flowDetail);
    }

    @Override
    public void insertAttackManager(long serverId, JSONObject jsonObject) {
        System.out.println("4================================================="+jsonObject.toString());
        AttackManager attackManager= JSON.parseObject(jsonObject.toString(), AttackManager.class);
        attackManager.setServerId(serverId);
        System.out.println(attackManager.toString());
        attackManagerMapper.insertSelective(attackManager);
    }

    @Override
    public void fileInsert(long serverId, JSONObject jsonObject) {
//        System.out.println("================================================="+jsonObject.toString());
//        AttackManager attackManager= JSON.parseObject(jsonObject.toString(), AttackManager.class);
//        attackManager.setServerId(serverId);
//        System.out.println(attackManager.toString());
//        attackManagerMapper.insertSelective(attackManager);
    }

    @Override
    public void insertAwServerAttribute(long serverId, JSONObject jsonObject) {
        System.out.println("5================================================="+jsonObject.toString());
        AwServerAttribute awServerAttribute= JSON.parseObject(jsonObject.toString(), AwServerAttribute.class);
        awServerAttribute.setServerId(serverId);
        System.out.println(awServerAttribute.toString());
        int size = awServerAttributeMapper.updateByPrimaryKeySelective(awServerAttribute);
        if(size<1){
            awServerAttributeMapper.insertSelective(awServerAttribute);
        }
    }
    @Override
    public void insertAwFunctionServer(long serverId, JSONObject jsonObject) {
        System.out.println("6================================================="+jsonObject.toString());
        AwFunctionServer awFunctionServer= JSON.parseObject(jsonObject.toString(), AwFunctionServer.class);
        awFunctionServer.setServerId(serverId);
        System.out.println(awFunctionServer.toString());
        awFunctionServerMapper.insertSelective(awFunctionServer);
    }
    //日志记录表添加
     @Override
    public void insertAwLogging(long serverId, JSONObject jsonObject) {
        System.out.println("7================================================="+jsonObject.toString());
         AwLogging awLogging= JSON.parseObject(jsonObject.toString(), AwLogging.class);
         awLogging.setServerId((int)serverId);
        System.out.println(awLogging.toString());
         awLoggingMapper.insertSelective(awLogging);
    }

    @Override
    public void insertAnalysisRecord(long serverId, JSONObject jsonObject) {
        System.out.println("8================================================="+jsonObject.toString());
        AnalysisRecord analysisRecord= JSON.parseObject(jsonObject.toString(), AnalysisRecord.class);
        analysisRecord.setServerId((int)serverId);
        System.out.println(analysisRecord.toString());
        trafficAnalysisServer.insertAnalysisRecord(analysisRecord);
    }
}
