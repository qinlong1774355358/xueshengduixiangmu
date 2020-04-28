package com.wyfx.aw.dao;

import com.wyfx.aw.entity.FunctionServer;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface FunctionServerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FunctionServer record);

    int insertSelective(FunctionServer record);

    FunctionServer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FunctionServer record);

    int updateByPrimaryKey(FunctionServer record);

    List<FunctionServer> selectByserverId(Long serverId);
}