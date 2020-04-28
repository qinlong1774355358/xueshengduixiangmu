package com.wyfx.aw.dao;

import com.wyfx.aw.entity.AwServerInfo;

public interface AwServerInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AwServerInfo record);

    int insertSelective(AwServerInfo record);

    AwServerInfo selectByPrimaryKey(Long id);

    AwServerInfo selectAwServerInfo(AwServerInfo record);

    int updateByPrimaryKeySelective(AwServerInfo record);

    int updateByPrimaryKeyWithBLOBs(AwServerInfo record);

    int updateByPrimaryKey(AwServerInfo record);
}