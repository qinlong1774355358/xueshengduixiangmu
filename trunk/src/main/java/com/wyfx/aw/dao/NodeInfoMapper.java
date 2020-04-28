package com.wyfx.aw.dao;

import com.wyfx.aw.entity.NodeInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface NodeInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(NodeInfo record);

    int insertSelective(NodeInfo record);

    NodeInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NodeInfo record);

    int updateByPrimaryKey(NodeInfo record);

    List<Map> selectAllOfManager();

    List<Map> selectAllByserverId(long serverId);

    List<Map> selectNodeDetail(long nodeId);
}