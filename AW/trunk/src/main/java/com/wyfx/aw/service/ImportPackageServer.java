package com.wyfx.aw.service;

import com.wyfx.aw.entity.AnalysisRecord;
import com.wyfx.aw.entity.FlowDetail;
import com.wyfx.aw.entity.ImportPackage;

import java.util.List;

/**
 * @ClassName: ImportPackageServer
 * @Description: 导入流量分析业务接口
 * @author: zhangguliang
 * @date: 2019-11-18
 */
public interface ImportPackageServer {
    /**
     * 已导入流量包列表查看
     * @return
     */
    List<ImportPackage> selectImportPackage(ImportPackage importPackage);
    /**
     * 导入流量包添加
     * @return
     */
    int insertSelective(ImportPackage importPackage);

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
