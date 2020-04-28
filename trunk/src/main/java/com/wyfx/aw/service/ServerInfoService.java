package com.wyfx.aw.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.wyfx.aw.entity.*;
import com.wyfx.aw.entity.vo.NodeServerVo;
import com.wyfx.aw.entity.vo.ServerInfoVo;
import com.wyfx.aw.entity.vo.ServerListVo;

import java.util.List;
import java.util.Map;

public interface ServerInfoService {


    ServerInfoVo findServerStatus(int serverId);

    void closeTcpConnection(Integer serverId);

    /**
     * 添加蜜罐服务器信息
     * @param host
     */
    ServerInfo addServerInfo(String host,int port) throws  Exception;


    /**
     * 分页查询有效的服务器列表信息
     * @param isDelete
     * @return
     */
    PageInfo findAvailableServer(int isDelete, int pageNum, int pageSize)throws Exception;

//    /**
//     * 分页查询有效的服务器列表信息
//     * @return
//     */
//    boolean findAvailableServer(Map map)throws Exception;

    /**
     * 根据ID删除服务器
     * @param id
     */
    void deleteById(int id)throws Exception;

    /**
     * 通过蜜罐服务器Id查询服务器信息
     * @param serverId
     * @return
     */
    ServerInfo findServerById(int serverId)throws Exception;


    /**
     * 根据ID查询服务器所部署的web服务
     * @param id
     * @return
     */
    List<WebServer> findWebServerById(long id)throws Exception;

    /**
     * 根据ID查询服务器属性
     * @param id
     * @return
     */
    ServerAttribute findServerAttributeById(long id)throws Exception;

    /**
     * 修改蜜罐服务器名称
     * @param id
     * @param serverName
     */
    void updateServerName(long id,String serverName)throws Exception;

    /**
     * 添加可用蜜罐服务列列表信息
     * @param ip
     * @param port
     * @throws Exception
     */
    void addServerList(String ip,int port) throws  Exception;

    /**
     * 根据蜜罐服务器id查询挂载服务
     * @param id
     * @return
     */
    List<FunctionServer> findFunctionServerById(long id);

    AttackManager findAttackByserverId(long serverId);

    FlowSetting findFlowSetting(long serverId);

    NodeServerVo findNodeServer(long serverId);

    void updateNodeServer(Integer id,Integer serverId, Integer runningState, Integer currentsupport);

    void changeWebServer(Integer serverId,Integer webServerId,Integer runningState);

    void updateFlowServer(Long id,Long serverId, Integer runningState, Integer flowType);

    void updateAttackSetting(Long id,Long serverId,Integer runningState,Integer attackWay,String minAddr,String maxAddr);

    void updateServerAttribute(long serverId, JSONObject jsonObject);

    void insertNodeServer(long serverId,JSONObject jsonObject);

    void insertWebServer(long serverId,JSONObject jsonObject);

    void insertFlowDetail(long serverId,JSONObject jsonObject);

    void insertAttackManager(long serverId,JSONObject jsonObject);

    void fileInsert(long serverId,JSONObject jsonObject);

    void insertAwServerAttribute(long serverId, JSONObject jsonObject);

    void insertAwFunctionServer(long serverId, JSONObject jsonObject);

    void insertAwLogging(long serverId, JSONObject jsonObject);

    void insertAnalysisRecord(long serverId,JSONObject jsonObject);
}
