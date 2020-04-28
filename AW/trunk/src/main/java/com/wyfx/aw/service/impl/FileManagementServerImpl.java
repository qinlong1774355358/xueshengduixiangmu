package com.wyfx.aw.service.impl;

import com.wyfx.aw.dao.FileManagementMapper;
import com.wyfx.aw.entity.FileManagement;
import com.wyfx.aw.service.FileManagementServer;
import com.wyfx.aw.service.SharingServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: FileManagementServerImpl
 * @Description: 文件管理业务实现
 * @author: zhangguliang
 * @date: 2019-11-7
 */
@Service
@Transactional
public class FileManagementServerImpl implements FileManagementServer {

    @Autowired
    private SharingServer sharingServer;

    @Autowired
    private FileManagementMapper fileManagementMapper;

    /**
     * 文件与文件夹查看
     * @param fileManagement
     * @return
     */
    @Override
    public List<FileManagement> selectFileManagement(FileManagement fileManagement) {
        return fileManagementMapper.selectFileManagement(fileManagement);
    }
    /**
     * 文件与文件夹添加
     * @param fileManagement
     * @return
     */
    @Override
    public boolean insertFileManagement(FileManagement fileManagement) {
        int count = fileManagementMapper.insert(fileManagement);
        if(count<1){
            return false;
        }
        return true;
    }
    /**
     * 删除文件与文件夹
     * @param fileManagement
     * @return
     */
    @Override
    public boolean deleteFileManagement(FileManagement fileManagement) {
        if (null==fileManagement.getId()){
            return false;
        }
        System.out.println(fileManagement.getFilePath());
        List<FileManagement> fileManagements = fileManagementMapper.selectFilePathByID(fileManagement);
        System.out.println(fileManagements);
        for (int i=0;i<fileManagements.size();i++){
            if(fileManagements.get(i).getId()>1){
                fileManagementMapper.deleteByPrimaryKey(fileManagements.get(i).getId());
            }
        }
        return true;
    }
}
