package com.wyfx.aw.service.impl;

import com.wyfx.aw.dao.AwFunctionServerMapper;
import com.wyfx.aw.dao.AwServerAttributeMapper;
import com.wyfx.aw.dao.AwServerInfoMapper;
import com.wyfx.aw.entity.AwFunctionServer;
import com.wyfx.aw.entity.AwServerAttribute;
import com.wyfx.aw.entity.AwServerInfo;
import com.wyfx.aw.service.BasicAttributesServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: BasicAttributesServerImpl
 * @Description: 基本属性业务处理
 * @author: zhangguliang
 * @date: 2019-11-12
 */
@Service
@Transactional
public class BasicAttributesServerImpl implements BasicAttributesServer {

    @Autowired
    private AwServerAttributeMapper awServerAttributeMapper;

    @Autowired
    private AwServerInfoMapper awServerInfoMapper;

    @Autowired
    private AwFunctionServerMapper awFunctionServerMapper;

    /**
     * 蜜罐服务器信息查询
     * @param map 信息集合查询条件
     * @return
     */
    @Override
    public Map selectBasicAttributes(Map map) {
        Map map1 = new HashMap();
        AwServerAttribute awServerAttribute = new AwServerAttribute();
        awServerAttribute.setServerId(Long.parseLong(map.get("serverId").toString()));
        System.out.println(awServerAttribute.getServerId());
        AwServerAttribute awServerAttribute1 = awServerAttributeMapper.selectAwServerAttribute(awServerAttribute);
        AwServerInfo awServerInfo = new AwServerInfo();
        awServerInfo.setServerId(Integer.parseInt(map.get("serverId").toString()));
        AwServerInfo awServerInfo1 = awServerInfoMapper.selectAwServerInfo(awServerInfo);
        map1.put("awServerAttribute",awServerAttribute1);
        map1.put("name",awServerInfo1.getName());
        map1.put("note",awServerInfo1.getNote());
        return map1;
}
    /**
     * 服务器所部署服务信息查询
     * @return
     */
    @Override
    public List<AwFunctionServer> selectAwFunctionServer(String serverId){
        return awFunctionServerMapper.selectAwFunctionServer(serverId);
    }

    /**
     * 修改蜜罐服务器信息
     * @param awServerInfo
     * @return
     */
    @Override
    public boolean updateAwServerInfo(AwServerInfo awServerInfo) {
        AwServerInfo awServerInfo1 = new AwServerInfo();
        awServerInfo1.setServerId(awServerInfo.getServerId());
        System.out.println(awServerInfo1.toString());
        long id = awServerInfoMapper.selectAwServerInfo(awServerInfo1).getId();
        if (null==awServerInfo){
            return false;
        }
        awServerInfo.setId(id);
        int size = awServerInfoMapper.updateByPrimaryKeySelective(awServerInfo);
        if(size<1){
            return false;
        }
        return true;
    }

}
