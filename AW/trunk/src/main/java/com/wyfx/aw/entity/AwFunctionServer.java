package com.wyfx.aw.entity;

public class AwFunctionServer {
    private Long id;

    private Long serverId;

    private String serverType;

    private Integer deployState;

    private Integer runningState;

    private String installDir;

    public AwFunctionServer(Long id, Long serverId, String serverType, Integer deployState, Integer runningState, String installDir) {
        this.id = id;
        this.serverId = serverId;
        this.serverType = serverType;
        this.deployState = deployState;
        this.runningState = runningState;
        this.installDir = installDir;
    }

    public AwFunctionServer() {
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

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType == null ? null : serverType.trim();
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
}