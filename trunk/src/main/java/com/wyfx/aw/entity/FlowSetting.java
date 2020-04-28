package com.wyfx.aw.entity;

/**
 * 流量获取服务设置
 */
public class FlowSetting {
    private Long id;

    private Long serverId;
    /**
     * 流量回传方式;{0：定时(每1h回传一次)；1：定量(每300M回传一次)}
     */
    private Integer flowType;

    private String capacity;

    /**
     * 流量回传服务运行状态:{0:运行；1:停止}
     */
    private Integer runningState;

    public FlowSetting(Long id, Long serverId, Integer flowType, String capacity, Integer runningState) {
        this.id = id;
        this.serverId = serverId;
        this.flowType = flowType;
        this.capacity = capacity;
        this.runningState = runningState;
    }

    public FlowSetting() {
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

    public Integer getFlowType() {
        return flowType;
    }

    public void setFlowType(Integer flowType) {
        this.flowType = flowType;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity == null ? null : capacity.trim();
    }

    public Integer getRunningState() {
        return runningState;
    }

    public void setRunningState(Integer runningState) {
        this.runningState = runningState;
    }

    @Override
    public String toString() {
        return "FlowSetting{" +
                "id=" + id +
                ", serverId=" + serverId +
                ", flowType=" + flowType +
                ", capacity='" + capacity + '\'' +
                ", runningState=" + runningState +
                '}';
    }
}