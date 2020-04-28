package com.wyfx.aw.entity;

public class ServerList {
    private Long id;

    private String serverAddr;

    private Integer port;

    private Integer isValid;

    public ServerList(Long id, String serverAddr, Integer port, Integer isValid) {
        this.id = id;
        this.serverAddr = serverAddr;
        this.port = port;
        this.isValid = isValid;
    }

    public ServerList() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr == null ? null : serverAddr.trim();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}