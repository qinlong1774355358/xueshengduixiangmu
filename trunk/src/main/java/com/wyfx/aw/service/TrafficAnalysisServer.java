package com.wyfx.aw.service;

import com.wyfx.aw.entity.AnalysisRecord;
import com.wyfx.aw.entity.FlowDetail;

import java.util.List;

/**
 * @ClassName: TrafficAnalysisServer
 * @Description: 蜜罐流量分析业务接口
 * @author: zhangguliang
 * @date: 2019-11-18
 */
public interface TrafficAnalysisServer {
    /**
     * 流量包列表查看
     * @return
     */
    List<FlowDetail> selectFlowDetail(FlowDetail flowDetail);
    /**
     * 流量包列表添加
     * @return
     */
    int insertSelective(FlowDetail flowDetail);

    /**
     * 分析中的流量查询
     * @return
     */
    List<FlowDetail> selectArrayListFlowDetail(List<Integer> ids);
    /**
     * 分析结果记录查询
     * @return
     */
    List<AnalysisRecord> selectAnalysisRecord(AnalysisRecord analysisRecord);

    /**
     * 分析结果记录添加
     * @return
     */
    boolean insertAnalysisRecord(AnalysisRecord analysisRecord);
}
