package com.wyfx.aw.dao;

import com.wyfx.aw.entity.AwLogging;

import java.util.List;

public interface AwLoggingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AwLogging record);

    int insertSelective(AwLogging record);

    AwLogging selectByPrimaryKey(Integer id);

    List<AwLogging> selectAwLogging(String serverId);

    int updateByPrimaryKeySelective(AwLogging record);

    int updateByPrimaryKeyWithBLOBs(AwLogging record);

    int updateByPrimaryKey(AwLogging record);
}