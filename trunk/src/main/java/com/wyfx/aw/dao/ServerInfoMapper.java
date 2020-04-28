package com.wyfx.aw.dao;

import com.wyfx.aw.entity.ServerInfo;
import com.wyfx.aw.entity.vo.ServerListVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ServerInfo record);

    int insertSelective(ServerInfo record);


    int updateByPrimaryKeySelective(ServerInfo record);

    int updateByPrimaryKeyWithBLOBs(ServerInfo record);

    int updateByPrimaryKey(ServerInfo record);

    /**
     * 查询未删除的蜜罐服务器信息
     * @param isDelete
     * @return
     */
    List<ServerListVo> selectByIsDelete(int isDelete);

    ServerInfo selectByPrimaryKey(long id);

    ServerInfo selectByPrimaryKeyServerId(int serverId);
    /**
     * 更新蜜罐服务器的删除状态和有效状态
     * @param id
     * @return
     */
    int updateById(@Param("id")long id, @Param("isDelete")int isDelete, @Param("isValid")int isValid);

    /**
     * 查询表中最大的主键Id
     * @return
     */
    long selectMaxId();

    ServerInfo selectByHostAndPort(@Param("host")String host,@Param("port")int port);


}