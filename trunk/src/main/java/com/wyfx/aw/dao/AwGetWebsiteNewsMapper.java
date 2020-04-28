package com.wyfx.aw.dao;

import com.wyfx.aw.entity.AwGetWebsiteNews;

import java.util.List;

public interface AwGetWebsiteNewsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AwGetWebsiteNews record);

    int insertSelective(AwGetWebsiteNews record);

    AwGetWebsiteNews selectByPrimaryKey(Integer id);

   List<AwGetWebsiteNews> selectAwGetWebsiteNews(AwGetWebsiteNews record);

    int updateByPrimaryKeySelective(AwGetWebsiteNews record);

    int updateByPrimaryKey(AwGetWebsiteNews record);
}