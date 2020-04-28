package com.wyfx.aw.service.impl;

import com.wyfx.aw.dao.AwGetWebsiteNewsMapper;
import com.wyfx.aw.dao.AwLoggingMapper;
import com.wyfx.aw.entity.AwGetWebsiteNews;
import com.wyfx.aw.entity.AwLogging;
import com.wyfx.aw.service.AwTheCrawlerServer;
import com.wyfx.aw.service.OperationLoggingServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: AwTheCrawlerImpl
 * @Description: 获取网站信息业务处理
 * @author: zhangguliang
 * @date: 2019-11-12
 */
@Service
@Transactional
public class AwTheCrawlerImpl implements AwTheCrawlerServer {

    @Autowired
    private AwGetWebsiteNewsMapper awGetWebsiteNewsMapper;

    /**
     * 日志信息查询
     * @return
     */
    @Override
    public List<AwGetWebsiteNews> selectAwTheCrawler(AwGetWebsiteNews awGetWebsiteNews) {
        return awGetWebsiteNewsMapper.selectAwGetWebsiteNews(awGetWebsiteNews);
    }
    /**
     * 日志信息详情查询
     * @return
     */
    @Override
    public boolean addAwTheCrawler(AwGetWebsiteNews awGetWebsiteNews) {
       int size = awGetWebsiteNewsMapper.insertSelective(awGetWebsiteNews);
       if(size<1){
           return false;
       }
        return true;
    }
}
