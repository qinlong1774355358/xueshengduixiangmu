package com.wyfx.aw.service;

import com.wyfx.aw.entity.BasicServices;

/**
 * @ClassName: ManagementServer
 * @Description: 管理控制业务接口
 * @author: zhangguliang
 * @date: 2019-11-7
 */
public interface ManagementServer {
    /**
     * 基础服务查询
     * @return
     */
    BasicServices selectBasicServices(BasicServices basicServices);
    /**
     * 基础服务添加
     * @return
     */
    boolean insertBasicServices(BasicServices basicServices);
    /**
     * 基础服务修改
     * @return
     */
    boolean updateBasicServices(BasicServices basicServices);
}
