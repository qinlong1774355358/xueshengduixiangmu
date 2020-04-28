package com.wyfx.aw.entity;

import java.util.List;

public class ServerInfo {
    private Long id;

    private int serverId;

    private String name;

    private Integer type;

    private String local;

    /**
     * 连接状态{"0":在线；"1":"离线"}
     */
    private Integer connStatus;

    private Integer isDelete;

    private Integer isValid;

    private String host;

    private Integer port;

    private String note;

    private NodeServer nodeServer;
    private AttackManager attackManager;
    private ServerAttribute serverAttribute;
    private FlowSetting flowSetting;

    private List<WebServer> webServerList;
    private List<FunctionServer> functionServerList;


    public ServerInfo(Long id, int serverId, String name, Integer type, String local, Integer connStatus, Integer isDelete, Integer isValid, String host, Integer port) {
        this.id = id;
        this.serverId = serverId;
        this.name = name;
        this.type = type;
        this.local = local;
        this.connStatus = connStatus;
        this.isDelete = isDelete;
        this.isValid = isValid;
        this.host = host;
        this.port = port;
    }

    public ServerInfo(Long id, int serverId, String name,Integer type, String local, Integer connStatus, Integer isDelete, Integer isValid, String host, Integer port, String note) {
        this.id = id;
        this.serverId = serverId;
        this.name = name;
        this.type = type;
        this.local = local;
        this.connStatus = connStatus;
        this.isDelete = isDelete;
        this.isValid = isValid;
        this.host = host;
        this.port = port;
        this.note = note;
    }

    public ServerInfo() {
        super();
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

    public void setServerId(int serverId) {
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

    public void setName(Integer type) {
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

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public NodeServer getNodeServer() {
        return nodeServer;
    }

    public void setNodeServer(NodeServer nodeServer) {
        this.nodeServer = nodeServer;
    }

    public AttackManager getAttackManager() {
        return attackManager;
    }

    public void setAttackManager(AttackManager attackManager) {
        this.attackManager = attackManager;
    }

    public ServerAttribute getServerAttribute() {
        return serverAttribute;
    }

    public void setServerAttribute(ServerAttribute serverAttribute) {
        this.serverAttribute = serverAttribute;
    }

    public FlowSetting getFlowSetting() {
        return flowSetting;
    }

    public void setFlowSetting(FlowSetting flowSetting) {
        this.flowSetting = flowSetting;
    }

    public List<WebServer> getWebServerList() {
        return webServerList;
    }

    public void setWebServerList(List<WebServer> webServerList) {
        this.webServerList = webServerList;
    }

    public List<FunctionServer> getFunctionServerList() {
        return functionServerList;
    }

    public void setFunctionServerList(List<FunctionServer> functionServerList) {
        this.functionServerList = functionServerList;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "id=" + id +
                ", serverId='" + serverId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", local='" + local + '\'' +
                ", connStatus=" + connStatus +
                ", isDelete=" + isDelete +
                ", isValid=" + isValid +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", note='" + note + '\'' +
                '}';
    }
}