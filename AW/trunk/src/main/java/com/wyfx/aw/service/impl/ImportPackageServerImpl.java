package com.wyfx.aw.service.impl;

import com.wyfx.aw.dao.AnalysisRecordMapper;
import com.wyfx.aw.dao.FlowDetailMapper;
import com.wyfx.aw.dao.ImportPackageMapper;
import com.wyfx.aw.entity.AnalysisRecord;
import com.wyfx.aw.entity.FlowDetail;
import com.wyfx.aw.entity.ImportPackage;
import com.wyfx.aw.service.ImportPackageServer;
import com.wyfx.aw.service.TrafficAnalysisServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: ImportPackageServerImpl
 * @Description: 导入流量分析业务实现
 * @author: zhangguliang
 * @date: 2019-11-18
 */
@Service
@Transactional
public class ImportPackageServerImpl implements ImportPackageServer {


    @Autowired
    private ImportPackageMapper importPackageMapper;

    @Autowired
    private AnalysisRecordMapper analysisRecordMapper;
    /**
     * 导入流量包列表
     * @param importPackage
     * @return
     */
    @Override
    public List<ImportPackage> selectImportPackage(ImportPackage importPackage) {
        return importPackageMapper.selectImportPackage(importPackage);
    }
    /**
     * 流量包列表添加
     * @param importPackage
     * @return
     */
    @Override
    public int insertSelective(ImportPackage importPackage) {
        return importPackageMapper.insertSelective(importPackage);
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
