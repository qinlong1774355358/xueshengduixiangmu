package com.wyfx.aw.dao;

import com.wyfx.aw.entity.FlowSetting;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowSettingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FlowSetting record);

    int insertSelective(FlowSetting record);

    FlowSetting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FlowSetting record);

    int updateByPrimaryKey(FlowSetting record);

    /**
     * 根据蜜罐服务器Id查询
     * @param serverId
     * @return
     */
    FlowSetting selectByserverId(Long serverId);
}