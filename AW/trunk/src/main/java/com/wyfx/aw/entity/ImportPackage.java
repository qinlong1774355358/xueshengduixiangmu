package com.wyfx.aw.entity;

import java.util.Date;

public class ImportPackage {
    private Integer id;

    private String packageName;

    private Date importTime;

    private Date analysisTime;

    private Integer fileSize;

    private Integer completeSize;

    private String note;

    public ImportPackage(Integer id, String packageName, Date importTime, Date analysisTime, Integer fileSize,Integer completeSize, String note) {
        this.id = id;
        this.packageName = packageName;
        this.importTime = importTime;
        this.analysisTime = analysisTime;
        this.fileSize = fileSize;
        this.completeSize = completeSize;
        this.note = note;
    }

    public ImportPackage() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName == null ? null : packageName.trim();
    }

    public Date getImportTime() {
        return importTime;
    }

    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }
    public Date getAnalysisTime() {
        return importTime;
    }

    public void setAnalysisTime(Date analysisTime) {
        this.analysisTime = analysisTime;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }
    public Integer getCompleteSize() {
        return fileSize;
    }

    public void setCompleteSize(Integer completeSize) {
        this.completeSize = completeSize;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}