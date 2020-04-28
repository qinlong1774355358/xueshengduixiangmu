package com.wyfx.aw.entity;

/**
 * 暗网节点服务
 */
public class NodeServer {
    private Long id;

    private Long serverId;

    private Integer isTor;

    private Integer isI2p;

    private Integer isZeronet;
    /**
     *  当前支持的节点服务:{0:"tor";1:"I2P";2:"ZeroNet"}
     */
    private Integer currentsupport;
    /**
     * 运行状态;{0:运行；1:停止}
     */
    private Integer runningState;

    public NodeServer(Long id, Long serverId, Integer isTor, Integer isI2p, Integer isZeronet,Integer runningState, Integer currentsupport) {
        this.id = id;
        this.serverId = serverId;
        this.isTor = isTor;
        this.isI2p = isI2p;
        this.isZeronet = isZeronet;
        this.currentsupport = currentsupport;
        this.runningState=runningState;
    }

    public NodeServer() {
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

    public Integer getIsTor() {
        return isTor;
    }

    public void setIsTor(Integer isTor) {
        this.isTor = isTor;
    }

    public Integer getIsI2p() {
        return isI2p;
    }

    public void setIsI2p(Integer isI2p) {
        this.isI2p = isI2p;
    }

    public Integer getIsZeronet() {
        return isZeronet;
    }

    public void setIsZeronet(Integer isZeronet) {
        this.isZeronet = isZeronet;
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

    @Override
    public String toString() {
        return "NodeServer{" +
                "id=" + id +
                ", serverId=" + serverId +
                ", isTor=" + isTor +
                ", isI2p=" + isI2p +
                ", isZeronet=" + isZeronet +
                ", currentsupport='" + currentsupport + '\'' +
                ", runningState=" + runningState +
                '}';
    }
}