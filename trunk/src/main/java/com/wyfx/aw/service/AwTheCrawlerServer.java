package com.wyfx.aw.service;

import com.wyfx.aw.entity.AwGetWebsiteNews;
import com.wyfx.aw.entity.AwLogging;

import java.util.List;

/**
 * @ClassName: AwTheCrawlerServer
 * @Description: 爬虫获取网站消息
 * @author: zhangguliang
 * @date: 2019-11-7
 */
public interface AwTheCrawlerServer {
    /**
     * 获取网站信息查询
     * @return
     */
    List<AwGetWebsiteNews> selectAwTheCrawler(AwGetWebsiteNews awGetWebsiteNews);

    /**
     * 添加网站信息
     * @param awGetWebsiteNews
     * @return
     */
    boolean addAwTheCrawler(AwGetWebsiteNews awGetWebsiteNews);
}