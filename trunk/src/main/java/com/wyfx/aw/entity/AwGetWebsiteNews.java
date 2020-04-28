package com.wyfx.aw.entity;

import java.util.Date;

public class AwGetWebsiteNews {
    private Integer id;

    private Integer serverId;

    private String webSiteName;

    private Date toObtainTime;

    public AwGetWebsiteNews(Integer id, Integer serverId, String webSiteName, Date toObtainTime) {
        this.id = id;
        this.serverId = serverId;
        this.webSiteName = webSiteName;
        this.toObtainTime = toObtainTime;
    }

    public AwGetWebsiteNews() {
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

    public String getWebSiteName() {
        return webSiteName;
    }

    public void setWebSiteName(String webSiteName) {
        this.webSiteName = webSiteName == null ? null : webSiteName.trim();
    }

    public Date getToObtainTime() {
        return toObtainTime;
    }

    public void setToObtainTime(Date toObtainTime) {
        this.toObtainTime = toObtainTime;
    }
}