package com.wyfx.aw.dao;

import com.wyfx.aw.entity.FlowDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowDetailMapper {

    int deleteByPrimaryKey(Long id);

    int insert(FlowDetail record);

    int insertSelective(FlowDetail record);

    FlowDetail selectByPrimaryKey(Long id);

    List<FlowDetail> selectFlowDetailById(@Param("ids")List<Integer> ids);

    List<FlowDetail> selectFlowDetail(FlowDetail flowDetail);

    int updateByPrimaryKeySelective(FlowDetail record);

    int updateByPrimaryKeyWithBLOBs(FlowDetail record);

    int updateByPrimaryKey(FlowDetail record);
}