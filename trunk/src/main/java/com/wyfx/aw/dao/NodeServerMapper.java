package com.wyfx.aw.dao;

import com.wyfx.aw.entity.NodeServer;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeServerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NodeServer record);

    int insertSelective(NodeServer record);

    NodeServer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NodeServer record);

    int updateByPrimaryKey(NodeServer record);

    /**
     * 根据蜜罐服务器查询
     * @param serverId
     * @return
     */
    NodeServer selectByserverId(Long serverId);

    int updateByserverIdSelective(NodeServer record);
}