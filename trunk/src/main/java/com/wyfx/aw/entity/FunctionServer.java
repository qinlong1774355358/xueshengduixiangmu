package com.wyfx.aw.entity;

/**
 * 挂载服务
 */
public class FunctionServer {
    private Long id;

    private Long serverId;
    /**
     * 挂载服务类型{0:服务控制管理服务模块;1:流量分析挂历服务模块;2:情报融合分析服务模块:3:论坛服务}
     */
    private Integer serverType;
    /**
     * 部署状态：{0:部署;1:未部署}
     */
    private Integer deployState;
    /**
     * 服务运行状态:{0:运行;1:未运行}
     */
    private Integer runningState;

    private String installDir;

    public FunctionServer(Long id, Long serverId, Integer serverType, Integer deployState, Integer runningState, String installDir) {
        this.id = id;
        this.serverId = serverId;
        this.serverType = serverType;
        this.deployState = deployState;
        this.runningState = runningState;
        this.installDir = installDir;
    }

    public FunctionServer() {
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

    public Integer getDeployState() {
        return deployState;
    }

    public void setDeployState(Integer deployState) {
        this.deployState = deployState;
    }

    public Integer getRunningState() {
        return runningState;
    }

    public void setRunningState(Integer runningState) {
        this.runningState = runningState;
    }

    public String getInstallDir() {
        return installDir;
    }

    public void setInstallDir(String installDir) {
        this.installDir = installDir == null ? null : installDir.trim();
    }

    @Override
    public String toString() {
        return "FunctionServer{" +
                "id=" + id +
                ", serverId=" + serverId +
                ", serverType=" + serverType +
                ", deployState=" + deployState +
                ", runningState=" + runningState +
                ", installDir='" + installDir + '\'' +
                '}';
    }
}