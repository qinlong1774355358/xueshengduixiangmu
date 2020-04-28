package com.wyfx.aw.dao;

import com.wyfx.aw.entity.AnalysisRecord;

import java.util.List;

public interface AnalysisRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AnalysisRecord record);

    int insertSelective(AnalysisRecord record);

    AnalysisRecord selectByPrimaryKey(Integer id);

    List<AnalysisRecord> selectAnalysisRecord(AnalysisRecord record);

    int updateByPrimaryKeySelective(AnalysisRecord record);

    int updateByPrimaryKey(AnalysisRecord record);
}