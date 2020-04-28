package com.wyfx.aw.service;

import com.wyfx.aw.entity.AwFunctionServer;
import com.wyfx.aw.entity.AwServerAttribute;
import com.wyfx.aw.entity.AwServerInfo;
import com.wyfx.aw.entity.FileManagement;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: BasicAttributesServer
 * @Description: 基本属性业务接口
 * @author: zhangguliang
 * @date: 2019-11-7
 */
public interface BasicAttributesServer {
    /**
     * 蜜罐服务器信息表查询
     * @return
     */
    Map selectBasicAttributes(Map map);

    /**
     * 服务器所部署服务信息查询
     * @return
     */
    List<AwFunctionServer> selectAwFunctionServer(String serverId);

    /**
     * 修改蜜罐服务器信息
     * @return
     */
    boolean updateAwServerInfo(AwServerInfo awServerInfo);

}
