package com.wyfx.aw.entity.vo;

public class ServerInfoVo {

    private Integer id;
    private String serverName;
    /**
     * 蜜罐服务器状态
     */
    private Integer serverStatus;

    public ServerInfoVo(Integer id, String serverName, Integer serverStatus) {
        this.id = id;
        this.serverName = serverName;
        this.serverStatus = serverStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Integer getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(Integer serverStatus) {
        this.serverStatus = serverStatus;
    }
}
