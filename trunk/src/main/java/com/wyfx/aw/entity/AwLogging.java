package com.wyfx.aw.entity;

public class AwLogging {
    private Integer id;

    private Integer serverId;

    private String title;

    private String content;

    public AwLogging(Integer id, Integer serverId, String title) {
        this.id = id;
        this.serverId = serverId;
        this.title = title;
    }

    public AwLogging(Integer id, Integer serverId, String title, String content) {
        this.id = id;
        this.serverId = serverId;
        this.title = title;
        this.content = content;
    }

    public AwLogging() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}