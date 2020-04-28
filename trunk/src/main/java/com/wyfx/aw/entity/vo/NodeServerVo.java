package com.wyfx.aw.entity.vo;

public class NodeServerVo {
    private Long id;

    private Long serverId;

    private String serverType;

    private Integer currentsupport;

    private Integer runningState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public Integer getCurrentsupport() {
        return currentsupport;
    }

    public void setCurrentsupport(Integer currentsupport) {
        this.currentsupport = currentsupport;
    }

    public Integer getRunningState() {
        return runningState;
    }

    public void setRunningState(Integer runningState) {
        this.runningState = runningState;
    }
}
