package com.wyfx.aw.entity;

public class AttackManager {
    private Long id;

    private Long serverId;
    /**
     * 攻击方式{0：自动搜索相应位置并发送攻击命令；1:指定位置(IP)发送攻击;2:指定范围（IP地址范围）进行攻击
     */
    private Integer attackWay;
    /**
     * 攻击服务运行状态:{0:运行；1:停止}
     */
    private Integer runningState;

    private String minAddr;

    private String maxAddr;

    public AttackManager(Long id, Long serverId, Integer attackWay, Integer runningState, String minAddr, String maxAddr) {
        this.id = id;
        this.serverId = serverId;
        this.attackWay = attackWay;
        this.runningState = runningState;
        this.minAddr = minAddr;
        this.maxAddr = maxAddr;
    }

    public AttackManager() {
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

    public Integer getAttackWay() {
        return attackWay;
    }

    public void setAttackWay(Integer attackWay) {
        this.attackWay = attackWay;
    }

    public Integer getRunningState() {
        return runningState;
    }

    public void setRunningState(Integer runningState) {
        this.runningState = runningState;
    }

    public String getMinAddr() {
        return minAddr;
    }

    public void setMinAddr(String minAddr) {
        this.minAddr = minAddr == null ? null : minAddr.trim();
    }

    public String getMaxAddr() {
        return maxAddr;
    }

    public void setMaxAddr(String maxAddr) {
        this.maxAddr = maxAddr == null ? null : maxAddr.trim();
    }

    @Override
    public String toString() {
        return "AttackManager{" +
                "id=" + id +
                ", serverId=" + serverId +
                ", attackWay=" + attackWay +
                ", runningState=" + runningState +
                ", minAddr='" + minAddr + '\'' +
                ", maxAddr='" + maxAddr + '\'' +
                '}';
    }
}