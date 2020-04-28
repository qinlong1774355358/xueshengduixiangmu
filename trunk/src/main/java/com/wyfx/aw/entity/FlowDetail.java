package com.wyfx.aw.entity;

public class FlowDetail {
    private Long id;

    private Long nodeId;

    private Integer serverId;

    private Integer status;

    private String exitIpAddr;

    private String inputFlow;

    private String outFlow;

    private String totalFlow;

    private String note;

    private String fileName;

    private Long fileSize;

    private Long offset;

    private String path;

    public FlowDetail(Long id, Integer serverId, Integer status,Long nodeId, String exitIpAddr, String inputFlow, String outFlow, String totalFlow, String note, String fileName, Long fileSize, Long offset) {
        this.id = id;
        this.serverId = serverId;
        this.nodeId = nodeId;
        this.status = status;
        this.exitIpAddr = exitIpAddr;
        this.inputFlow = inputFlow;
        this.outFlow = outFlow;
        this.totalFlow = totalFlow;
        this.note = note;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.offset = offset;
    }

    public FlowDetail(Long id,Integer serverId, Integer status, Long nodeId, String exitIpAddr, String inputFlow, String outFlow, String totalFlow, String note, String fileName, Long fileSize, Long offset, String path) {
        this.id = id;
        this.serverId = serverId;
        this.nodeId = nodeId;
        this.status = status;
        this.exitIpAddr = exitIpAddr;
        this.inputFlow = inputFlow;
        this.outFlow = outFlow;
        this.totalFlow = totalFlow;
        this.note = note;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.offset = offset;
        this.path = path;
    }

    public FlowDetail() {
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
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getExitIpAddr() {
        return exitIpAddr;
    }

    public void setExitIpAddr(String exitIpAddr) {
        this.exitIpAddr = exitIpAddr == null ? null : exitIpAddr.trim();
    }

    public String getInputFlow() {
        return inputFlow;
    }

    public void setInputFlow(String inputFlow) {
        this.inputFlow = inputFlow == null ? null : inputFlow.trim();
    }

    public String getOutFlow() {
        return outFlow;
    }

    public void setOutFlow(String outFlow) {
        this.outFlow = outFlow == null ? null : outFlow.trim();
    }

    public String getTotalFlow() {
        return totalFlow;
    }

    public void setTotalFlow(String totalFlow) {
        this.totalFlow = totalFlow == null ? null : totalFlow.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }
}