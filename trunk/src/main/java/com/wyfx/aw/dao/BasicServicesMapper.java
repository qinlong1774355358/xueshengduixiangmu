package com.wyfx.aw.dao;

import com.wyfx.aw.entity.BasicServices;

public interface BasicServicesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BasicServices record);

    int insertSelective(BasicServices record);

    BasicServices selectByPrimaryKey(Integer id);

    BasicServices selectBasicServices(BasicServices record);

    int updateByPrimaryKeySelective(BasicServices record);

    int updateByPrimaryKeyWithBLOBs(BasicServices record);

    int updateBasicServices(BasicServices record);

    int updateByPrimaryKey(BasicServices record);
}