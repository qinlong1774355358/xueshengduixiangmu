package com.wyfx.aw.dao;

import com.wyfx.aw.entity.WebServer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebServerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WebServer record);

    int insertSelective(WebServer record);

    WebServer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WebServer record);

    int updateByPrimaryKey(WebServer record);

    /**
     * 根据蜜罐服务器Id查询
     * @param serverId
     * @return
     */
    List<WebServer> selectByserverId(Long serverId);
}