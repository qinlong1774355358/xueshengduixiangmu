package com.wyfx.aw.entity;

public class AwServerAttribute {
    private Long id;

    private Long serverId;

    private String cpu;

    private String hardDisk;

    private String ram;

    private String ip4Addr;

    private String ip6Addr;

    private String system;

    public AwServerAttribute(Long id, Long serverId, String cpu, String hardDisk, String ram, String ip4Addr, String ip6Addr, String system) {
        this.id = id;
        this.serverId = serverId;
        this.cpu = cpu;
        this.hardDisk = hardDisk;
        this.ram = ram;
        this.ip4Addr = ip4Addr;
        this.ip6Addr = ip6Addr;
        this.system = system;
    }

    public AwServerAttribute() {
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

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu == null ? null : cpu.trim();
    }

    public String getHardDisk() {
        return hardDisk;
    }

    public void setHardDisk(String hardDisk) {
        this.hardDisk = hardDisk == null ? null : hardDisk.trim();
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram == null ? null : ram.trim();
    }

    public String getIp4Addr() {
        return ip4Addr;
    }

    public void setIp4Addr(String ip4Addr) {
        this.ip4Addr = ip4Addr == null ? null : ip4Addr.trim();
    }

    public String getIp6Addr() {
        return ip6Addr;
    }

    public void setIp6Addr(String ip6Addr) {
        this.ip6Addr = ip6Addr == null ? null : ip6Addr.trim();
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system == null ? null : system.trim();
    }
}