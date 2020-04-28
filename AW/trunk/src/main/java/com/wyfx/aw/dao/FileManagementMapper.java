package com.wyfx.aw.dao;

import com.wyfx.aw.entity.FileManagement;

import java.util.List;

public interface FileManagementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FileManagement record);

    int insertSelective(FileManagement record);

    FileManagement selectByPrimaryKey(Integer id);

    List<FileManagement> selectFileManagement(FileManagement fileManagement);

    List<FileManagement> selectFilePath(String codingIdId);

    List<FileManagement> selectFilePathByID(FileManagement fileManagement);

    int updateByPrimaryKeySelective(FileManagement record);

    int updateByPrimaryKey(FileManagement record);
}