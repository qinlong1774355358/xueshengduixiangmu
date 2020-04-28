package com.wyfx.aw.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class AnalysisRecord {
    private Integer id;

    private Integer serverId;

    private Integer status;

    private String trafficType;

    private String dir;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date duration;

    private String srcip;

    private String srcipcc;

    private String srcipwho;

    private String srcport;

    private String dstip;

    private String dstipcc;

    private String dstipwho;

    private String dstport;

    private String l4proto;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timefirst;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timelast;

    public AnalysisRecord(Integer id, Integer serverId,Integer status, String dir, Date duration, String srcip, String srcipcc, String srcipwho, String srcport, String dstip, String dstipcc, String dstipwho, String dstport, String l4proto, Date timefirst, Date timelast) {
        this.id = id;
        this.serverId = serverId;
        this.status = status;
        this.dir = dir;
        this.duration = duration;
        this.srcip = srcip;
        this.srcipcc = srcipcc;
        this.srcipwho = srcipwho;
        this.srcport = srcport;
        this.dstip = dstip;
        this.dstipcc = dstipcc;
        this.dstipwho = dstipwho;
        this.dstport = dstport;
        this.l4proto = l4proto;
        this.timefirst = timefirst;
        this.timelast = timelast;
    }

    public AnalysisRecord() {
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
 public Integer getStatus() {
     return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir == null ? null : dir.trim();
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public String getSrcip() {
        return srcip;
    }

    public void setSrcip(String srcip) {
        this.srcip = srcip == null ? null : srcip.trim();
    }

    public String getSrcipcc() {
        return srcipcc;
    }

    public void setSrcipcc(String srcipcc) {
        this.srcipcc = srcipcc == null ? null : srcipcc.trim();
    }

    public String getSrcipwho() {
        return srcipwho;
    }

    public void setSrcipwho(String srcipwho) {
        this.srcipwho = srcipwho == null ? null : srcipwho.trim();
    }

    public String getSrcport() {
        return srcport;
    }

    public void setSrcport(String srcport) {
        this.srcport = srcport == null ? null : srcport.trim();
    }

    public String getDstip() {
        return dstip;
    }

    public void setDstip(String dstip) {
        this.dstip = dstip == null ? null : dstip.trim();
    }

    public String getDstipcc() {
        return dstipcc;
    }

    public void setDstipcc(String dstipcc) {
        this.dstipcc = dstipcc == null ? null : dstipcc.trim();
    }

    public String getDstipwho() {
        return dstipwho;
    }

    public void setDstipwho(String dstipwho) {
        this.dstipwho = dstipwho == null ? null : dstipwho.trim();
    }

    public String getDstport() {
        return dstport;
    }

    public void setDstport(String dstport) {
        this.dstport = dstport == null ? null : dstport.trim();
    }

    public String getL4proto() {
        return l4proto;
    }

    public void setL4proto(String l4proto) {
        this.l4proto = l4proto == null ? null : l4proto.trim();
    }

    public Date getTimefirst() {
        return timefirst;
    }

    public void setTimefirst(Date timefirst) {
        this.timefirst = timefirst;
    }

    public Date getTimelast() {
        return timelast;
    }

    public void setTimelast(Date timelast) {
        this.timelast = timelast;
    }

    public String getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(String trafficType) {
        this.trafficType = trafficType;
    }

    @Override
    public String toString() {
        return "AnalysisRecord{" +
                "id=" + id +
                ", serverId=" + serverId +
                ", status=" + status +
                ", trafficType='" + trafficType + '\'' +
                ", dir='" + dir + '\'' +
                ", duration='" + duration + '\'' +
                ", srcip='" + srcip + '\'' +
                ", srcipcc='" + srcipcc + '\'' +
                ", srcipwho='" + srcipwho + '\'' +
                ", srcport='" + srcport + '\'' +
                ", dstip='" + dstip + '\'' +
                ", dstipcc='" + dstipcc + '\'' +
                ", dstipwho='" + dstipwho + '\'' +
                ", dstport='" + dstport + '\'' +
                ", l4proto='" + l4proto + '\'' +
                ", timefirst=" + timefirst +
                ", timelast=" + timelast +
                '}';
    }
}