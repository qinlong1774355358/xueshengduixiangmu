package com.wyfx.aw.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class FileManagement {
    private Integer id;

    private Integer serverId;

    private String codingId;

    private Integer type;

    private String filePath;

    private String fileName;
    @DateTimeFormat(pattern = "YYYY-MM-dd HH:mm")
    private Date updateTime;

    private String codingIdId;

    public FileManagement(Integer id, Integer serverId, String codingId, Integer type, String filePath, String fileName, Date updateTime, String codingIdId) {
        this.id = id;
        this.serverId = serverId;
        this.codingId = codingId;
        this.type = type;
        this.filePath = filePath;
        this.fileName = fileName;
        this.updateTime = updateTime;
        this.codingIdId = codingIdId;
    }

    public FileManagement() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getCodingId() {
        return codingId;
    }

    public void setCodingId(String codingId) {
        this.codingId = codingId == null ? null : codingId.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCodingIdId() {
        return codingIdId;
    }

    public void setCodingIdId(String codingIdId) {
        this.codingIdId = codingIdId == null ? null : codingIdId.trim();
    }
}