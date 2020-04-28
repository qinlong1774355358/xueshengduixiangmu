package com.wyfx.aw.dao;

import com.wyfx.aw.entity.AwServerAttribute;

public interface AwServerAttributeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AwServerAttribute record);

    int insertSelective(AwServerAttribute record);

    AwServerAttribute selectByPrimaryKey(Long id);

    AwServerAttribute selectAwServerAttribute(AwServerAttribute record);

    int updateByPrimaryKeySelective(AwServerAttribute record);

    int updateByPrimaryKey(AwServerAttribute record);
}