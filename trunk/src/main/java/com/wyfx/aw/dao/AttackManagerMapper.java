package com.wyfx.aw.dao;

import com.wyfx.aw.entity.AttackManager;
import org.springframework.stereotype.Repository;

@Repository
public interface AttackManagerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AttackManager record);

    int insertSelective(AttackManager record);

    AttackManager selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AttackManager record);

    int updateByPrimaryKey(AttackManager record);

    /**
     * 根据蜜罐服务器查询
     * @param serverId
     * @return
     */
    AttackManager selectByserverId(Long serverId);
}