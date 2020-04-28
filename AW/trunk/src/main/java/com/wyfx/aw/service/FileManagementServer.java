package com.wyfx.aw.service;

import com.wyfx.aw.entity.FileManagement;

import java.util.List;

/**
 * @ClassName: FileManagementServer
 * @Description: 文件管理业务接口
 * @author: zhangguliang
 * @date: 2019-11-7
 */
public interface FileManagementServer {

    /**
     * 文件与文件夹查看
     * @return
     */
    List<FileManagement> selectFileManagement(FileManagement fileManagement);

    /**
     * 文件与文件夹添加
     * @return
     */
    boolean insertFileManagement(FileManagement fileManagement);
    /**
     * 删除文件与文件夹
     * @return
     */
    boolean deleteFileManagement(FileManagement fileManagement);
}
