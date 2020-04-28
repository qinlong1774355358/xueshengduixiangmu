package com.wyfx.aw.entity.vo;

public class ServerListVo {
    /**
     * 蜜罐服务器Id
     */
    private Long id;
    /**
     * 蜜罐服务器serverId
     */
    private Integer serverId;

    /**
     * 蜜罐服务器名称
     */
    private String name;
    /**
     * 服务器类型（0：中继服务器，1：蜜罐服务器）
     */
    private Integer type;
    /**
     * 所在地
     */
    private String local;
    /**
     * 蜜罐运行状态
     */
    private Integer connStatus;
    /**
     * 蜜罐域名或ip地址
     */
    private String host;

    /**
     * 当前蜜罐支持的暗网节点服务(tor/i2p/ZeroNet)
     */
    private String currentsupport;
    /**
     * 蜜罐暗网节点服务的运行状态
     */
    private Integer runningState;

    /**
     * 蜜罐的操作系统
     */
    private String system;

    /**
     * 部署服务: 论坛/blog/网页;{0：论坛；1：blog；2：网页}
     */
    private Integer serverType;

    /**
     * 攻击服务运行状态
     */
    private Integer attackManagerStatus;


    public ServerListVo() {
    }

    public ServerListVo(Long id, Integer serverId, String name, Integer type, String local, Integer connStatus, String host, String currentsupport, Integer runningState, String system, Integer serverType, Integer attackManagerStatus) {
        this.id = id;
        this.serverId = serverId;
        this.name = name;
        this.type = type;
        this.local = local;
        this.connStatus = connStatus;
        this.host = host;
        this.currentsupport = currentsupport;
        this.runningState = runningState;
        this.system = system;
        this.serverType = serverType;
        this.attackManagerStatus = attackManagerStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getServerId() {
        return serverId;
    }

    public void setserverId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Integer getConnStatus() {
        return connStatus;
    }

    public void setConnStatus(Integer connStatus) {
        this.connStatus = connStatus;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCurrentsupport() {
        return currentsupport;
    }

    public void setCurrentsupport(String currentsupport) {
        this.currentsupport = currentsupport;
    }

    public Integer getRunningState() {
        return runningState;
    }

    public void setRunningState(Integer runningState) {
        this.runningState = runningState;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }

    public Integer getAttackManagerStatus() {
        return attackManagerStatus;
    }

    public void setAttackManagerStatus(Integer attackManagerStatus) {
        this.attackManagerStatus = attackManagerStatus;
    }

    @Override
    public String toString() {
        return "ServerListVo{" +
                "id=" + id +
                ", serverId='" + serverId + '\'' +
                ", name='" + name + '\'' +
                ", local='" + local + '\'' +
                ", connStatus=" + connStatus +
                ", host='" + host + '\'' +
                ", currentsupport='" + currentsupport + '\'' +
                ", runningState=" + runningState +
                ", system='" + system + '\'' +
                ", serverType=" + serverType +
                ", attackManagerStatus=" + attackManagerStatus +
                '}';
    }
}
