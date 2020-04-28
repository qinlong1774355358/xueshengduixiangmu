package com.wyfx.aw.service;

import com.github.pagehelper.PageInfo;
import com.wyfx.aw.entity.NodeInfo;
import java.util.List;
import java.util.Map;

public interface NodeInfoService {

    /**
     * 查询蜜罐服务器中的所有节点
     * @param pageNum
     * @param pageSize
     * @param id
     * @return
     * @throws Exception
     */
    PageInfo findNodeInfoById(int pageNum, int pageSize, int id) throws Exception;

    /**
     * 查询节点详情信息
     * @return
     */
    PageInfo findNodeDetail(int nodeId,int pageNum, int pageSize) throws Exception;

    PageInfo findNodeInfoManager(int pageNum, int pageSize) throws Exception;

    void addFlowInfo();


}
