package com.wyfx.aw.dao;

import com.wyfx.aw.entity.AwFunctionServer;

import java.util.List;

public interface AwFunctionServerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AwFunctionServer record);

    int insertSelective(AwFunctionServer record);

    AwFunctionServer selectByPrimaryKey(Long id);

    List<AwFunctionServer> selectAwFunctionServer(String serverId);

    int updateByPrimaryKeySelective(AwFunctionServer record);

    int updateByPrimaryKey(AwFunctionServer record);
}