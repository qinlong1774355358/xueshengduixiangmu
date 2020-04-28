package com.wyfx.aw.dao;

import com.wyfx.aw.entity.ServerAttribute;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerAttributeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ServerAttribute record);

    int insertSelective(ServerAttribute record);

    ServerAttribute selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ServerAttribute record);

    int updateByPrimaryKey(ServerAttribute record);

    /**
     * 根据蜜罐服务器ID查询服务器属性
     * @param id
     * @return
     */
    ServerAttribute selectByserverId(Long id);

    /**
     * 根据蜜罐服务器Id修改
     * @param record
     * @return
     */
    int updateByserverId(ServerAttribute record);
}