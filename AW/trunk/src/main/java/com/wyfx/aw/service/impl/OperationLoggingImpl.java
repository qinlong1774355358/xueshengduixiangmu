package com.wyfx.aw.service.impl;

import com.wyfx.aw.dao.AwLoggingMapper;
import com.wyfx.aw.entity.AwLogging;
import com.wyfx.aw.service.OperationLoggingServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: OperationLoggingServer
 * @Description: 运行日志业务处理
 * @author: zhangguliang
 * @date: 2019-11-12
 */
@Service
@Transactional
public class OperationLoggingImpl implements OperationLoggingServer {

    @Autowired
    private AwLoggingMapper awLoggingMapper;

    /**
     * 日志信息查询
     * @return
     */
    @Override
    public List<AwLogging> selectAllAwLogging(String serverId) {
        return awLoggingMapper.selectAwLogging(serverId);
    }
    /**
     * 日志信息详情查询
     * @return
     */
    @Override
    public AwLogging selectAwLoggingByPrmaryKey(int id) {
        return awLoggingMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean addAwLogging(AwLogging awLogging) {
        return awLoggingMapper.insertSelective(awLogging)>0;
    }

}
