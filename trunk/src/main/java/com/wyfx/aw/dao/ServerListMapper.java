package com.wyfx.aw.dao;

import com.wyfx.aw.entity.ServerList;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
@Repository
public interface ServerListMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ServerList record);

    int insertSelective(ServerList record);

    ServerList selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ServerList record);

    int updateByPrimaryKey(ServerList record);

    ServerList selectByIpAndPort(@Param("ip")String ip, @Param("port")int port);

}