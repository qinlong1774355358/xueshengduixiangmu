package com.wyfx.aw.entity;

public class AwServerInfo {
    private Long id;

    private Integer serverId;

    private String name;

    private String local;

    private Integer connStatus;

    private Integer isDelete;

    private Integer isValid;

    private String host;

    private Integer port;

    private String note;

    public AwServerInfo(Long id, Integer serverId, String name, String local, Integer connStatus, Integer isDelete, Integer isValid, String host, Integer port) {
        this.id = id;
        this.serverId = serverId;
        this.name = name;
        this.local = local;
        this.connStatus = connStatus;
        this.isDelete = isDelete;
        this.isValid = isValid;
        this.host = host;
        this.port = port;
    }

    public AwServerInfo(Long id, Integer serverId, String name, String local, Integer connStatus, Integer isDelete, Integer isValid, String host, Integer port, String note) {
        this.id = id;
        this.serverId = serverId;
        this.name = name;
        this.local = local;
        this.connStatus = connStatus;
        this.isDelete = isDelete;
        this.isValid = isValid;
        this.host = host;
        this.port = port;
        this.note = note;
    }

    public AwServerInfo() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local == null ? null : local.trim();
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
        this.host = host == null ? null : host.trim();
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
        this.note = note == null ? null : note.trim();
    }

    @Override
    public String toString() {
        return "AwServerInfo{" +
                "id=" + id +
                ", serverId=" + serverId +
                ", name='" + name + '\'' +
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