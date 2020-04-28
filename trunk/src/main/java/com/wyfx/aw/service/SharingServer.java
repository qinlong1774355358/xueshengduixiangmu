package com.wyfx.aw.service;

import com.wyfx.aw.entity.FileManagement;

import java.util.List;

/**
 * 共用业务类
 */
public interface SharingServer {
     List<FileManagement> getFileById(FileManagement fileManagement);
}
