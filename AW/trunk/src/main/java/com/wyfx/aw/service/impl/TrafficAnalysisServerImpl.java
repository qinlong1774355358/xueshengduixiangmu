package com.wyfx.aw.service.impl;

import com.wyfx.aw.dao.AnalysisRecordMapper;
import com.wyfx.aw.dao.FlowDetailMapper;
import com.wyfx.aw.entity.AnalysisRecord;
import com.wyfx.aw.entity.FlowDetail;
import com.wyfx.aw.service.TrafficAnalysisServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: TrafficAnalysisServerImpl
 * @Description: 蜜罐流量分析业务实现
 * @author: zhangguliang
 * @date: 2019-11-18
 */
@Service
@Transactional
public class TrafficAnalysisServerImpl implements TrafficAnalysisServer {


    @Autowired
    private FlowDetailMapper flowDetailMapper;

    @Autowired
    private AnalysisRecordMapper analysisRecordMapper;
    /**
     * 流量包列表
     * @param flowDetail
     * @return
     */
    @Override
    public List<FlowDetail> selectFlowDetail(FlowDetail flowDetail) {
        return flowDetailMapper.selectFlowDetail(flowDetail);
    }
    /**
     * 流量包列表添加
     * @param flowDetail
     * @return
     */
    @Override
    public int insertSelective(FlowDetail flowDetail) {
        return flowDetailMapper.insertSelective(flowDetail);
    }
    /**
     * 分析中的流量实现业务查询
     * @param ids
     * @return
     */
    @Override
    public List<FlowDetail> selectArrayListFlowDetail(List<Integer> ids) {
        return flowDetailMapper.selectFlowDetailById(ids);
    }

    /**
     * 分析结果记录查询
     * @param analysisRecord
     * @return
     */
    @Override
    public List<AnalysisRecord> selectAnalysisRecord(AnalysisRecord analysisRecord) {
        return analysisRecordMapper.selectAnalysisRecord(analysisRecord);
    }

    /**
     * 分析结果记录添加
     * @param analysisRecord
     * @return
     */
    @Override
    public boolean insertAnalysisRecord(AnalysisRecord analysisRecord) {
        return analysisRecordMapper.insertSelective(analysisRecord)>0;
    }
}
