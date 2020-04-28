package com.wyfx.aw.entity;

public class NodeInfo {
    private Long id;

    private Long serverId;

    private String nodeId;

    private String ipAddr;
    /**
     * 节点来源
     */
    private String nodeSource;
    /**
     * 连接次数
     */
    private Integer reconnCount;
    /**
     * 节点运行状态:{0:运行;1:未运行}
     */
    private Integer runningState;
    /**
     * 节点接入状态
     */
    private Integer joinStatus;

    /**
     * 控制状态（0：已控制，1：未控制，2：攻击执行中）
     */
    private Integer controlStatus;

    public NodeInfo(Long id, Long serverId, String nodeId, String ipAddr, String nodeSource, Integer reconnCount, Integer runningState, Integer joinStatus, Integer controlStatus) {
        this.id = id;
        this.serverId = serverId;
        this.nodeId = nodeId;
        this.ipAddr = ipAddr;
        this.nodeSource = nodeSource;
        this.reconnCount = reconnCount;
        this.runningState = runningState;
        this.joinStatus = joinStatus;
        this.controlStatus = controlStatus;
    }

    public NodeInfo() {
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

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr == null ? null : ipAddr.trim();
    }

    public String getNodeSource() {
        return nodeSource;
    }

    public void setNodeSource(String nodeSource) {
        this.nodeSource = nodeSource == null ? null : nodeSource.trim();
    }

    public Integer getReconnCount() {
        return reconnCount;
    }

    public void setReconnCount(Integer reconnCount) {
        this.reconnCount = reconnCount;
    }

    public Integer getRunningState() {
        return runningState;
    }

    public void setRunningState(Integer runningState) {
        this.runningState = runningState;
    }

    public Integer getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(Integer joinStatus) {
        this.joinStatus = joinStatus;
    }
    public Integer getControlStatus() {
        return controlStatus;
    }

    public void setControlStatus(Integer controlStatus) {
        this.controlStatus = controlStatus;
    }
}