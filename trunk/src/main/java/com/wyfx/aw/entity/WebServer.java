package com.wyfx.aw.entity;

public class WebServer {
    private Long id;

    private Long serverId;

    /**
     * web服务类型{论坛/blog/网页;{0：论坛；1：blog；2：网页}
     */
    private Integer serverType;

    /**
     * web服务运行状态:{0:运行;1未运行}
     */
    private Integer runningState;
    /**
     * 是否含有外挂:{0:含有;1:不含有}
     */
    private Integer hasPlugin;

    public WebServer(Long id, Long serverId, Integer serverType, Integer runningState, Integer hasPlugin) {
        this.id = id;
        this.serverId = serverId;
        this.serverType = serverType;
        this.runningState = runningState;
        this.hasPlugin = hasPlugin;
    }

    public WebServer() {
        super();
    }

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

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }

    public Integer getRunningState() {
        return runningState;
    }

    public void setRunningState(Integer runningState) {
        this.runningState = runningState;
    }

    public Integer getHasPlugin() {
        return hasPlugin;
    }

    public void setHasPlugin(Integer hasPlugin) {
        this.hasPlugin = hasPlugin;
    }

    @Override
    public String toString() {
        return "WebServer{" +
                "id=" + id +
                ", serverId=" + serverId +
                ", serverType=" + serverType +
                ", runningState=" + runningState +
                ", hasPlugin=" + hasPlugin +
                '}';
    }
}