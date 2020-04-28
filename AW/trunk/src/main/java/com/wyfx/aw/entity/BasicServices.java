package com.wyfx.aw.entity;

public class BasicServices {
    private Integer id;

    private Integer controlServiceStatus;

    private Integer controlStatus;

    private Integer serverId;

    private String note;

    public BasicServices(Integer id, Integer controlServiceStatus, Integer controlStatus, Integer serverId) {
        this.id = id;
        this.controlServiceStatus = controlServiceStatus;
        this.controlStatus = controlStatus;
        this.serverId = serverId;
    }

    public BasicServices(Integer id, Integer controlServiceStatus, Integer controlStatus, Integer serverId, String note) {
        this.id = id;
        this.controlServiceStatus = controlServiceStatus;
        this.controlStatus = controlStatus;
        this.serverId = serverId;
        this.note = note;
    }

    public BasicServices() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getControlServiceStatus() {
        return controlServiceStatus;
    }

    public void setControlServiceStatus(Integer controlServiceStatus) {
        this.controlServiceStatus = controlServiceStatus;
    }

    public Integer getControlStatus() {
        return controlStatus;
    }

    public void setControlStatus(Integer controlStatus) {
        this.controlStatus = controlStatus;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    @Override
    public String toString() {
        return "BasicServices{" +
                "id=" + id +
                ", controlServiceStatus=" + controlServiceStatus +
                ", controlStatus=" + controlStatus +
                ", serverId=" + serverId +
                ", note='" + note + '\'' +
                '}';
    }
}