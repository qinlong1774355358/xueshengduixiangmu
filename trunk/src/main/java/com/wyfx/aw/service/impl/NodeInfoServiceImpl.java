package com.wyfx.aw.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyfx.aw.dao.NodeInfoMapper;
import com.wyfx.aw.entity.NodeInfo;
import com.wyfx.aw.network.vo.CmdUtil;
import com.wyfx.aw.network.vo.Pcmd;
import com.wyfx.aw.service.NodeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class NodeInfoServiceImpl implements NodeInfoService {

    @Autowired
    private NodeInfoMapper nodeInfoMapper;

    /**
     * 查询蜜罐服务器中的所有节点
     * @return
     */
    @Override
    public PageInfo findNodeInfoById(int pageNum, int pageSize, int serverId) throws Exception {
        Page<Map> objects = PageHelper.startPage(pageNum, pageSize);
        List<Map> list=nodeInfoMapper.selectAllByserverId(serverId);
        System.out.println(objects);
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }


    @Override
    public PageInfo findNodeInfoManager(int pageNum, int pageSize) throws Exception {
        PageHelper.startPage(pageNum,pageSize);
        List<Map> list=nodeInfoMapper.selectAllOfManager();
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    @Override
    public PageInfo findNodeDetail(int nodeId, int pageNum, int pageSize) throws Exception {
        PageHelper.startPage(pageNum,pageSize);
        List<Map> list=nodeInfoMapper.selectNodeDetail(nodeId);
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    @Override
    public void addFlowInfo() {

    }
}
