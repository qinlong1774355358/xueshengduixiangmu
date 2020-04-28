package com.wyfx.aw.service;

import com.wyfx.aw.entity.AwFunctionServer;
import com.wyfx.aw.entity.AwLogging;
import com.wyfx.aw.entity.AwServerInfo;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: OperationLoggingServer
 * @Description: 运行日志业务接口
 * @author: zhangguliang
 * @date: 2019-11-7
 */
public interface OperationLoggingServer {
    /**
     * 日志信息表查询
     * @return
     */
    List<AwLogging> selectAllAwLogging(String serverId);
    /**
     * 根据iid查询详细日志信息表
     * @return
     */
    AwLogging selectAwLoggingByPrmaryKey(int id);
    /**
     * 添加日志记录
     * @return
     */
    boolean addAwLogging(AwLogging awLogging);
}
