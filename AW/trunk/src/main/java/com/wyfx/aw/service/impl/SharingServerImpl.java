package com.wyfx.aw.service.impl;

import com.wyfx.aw.entity.FileManagement;
import com.wyfx.aw.service.FileManagementServer;
import com.wyfx.aw.service.SharingServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: ManagementServer
 * @Description: 共用业务实现
 * @author: zhangguliang
 * @date: 2019-11-7
 */
@Service
@Transactional
public class SharingServerImpl implements SharingServer {
    @Autowired
    private FileManagementServer fileManagementServer;

    //根据id文件查询所有子文件夹与文件
    @Override
    public List<FileManagement> getFileById(FileManagement fileManagement) {
        List<FileManagement> fileManagement1= fileManagementServer.selectFileManagement(fileManagement);
        String codingId = "0";
        for (int i=0;i<fileManagement1.size();i++){
             codingId = fileManagement1.get(0).getCodingId();
        }
        FileManagement fileManagement2 = new FileManagement();
        fileManagement2.setCodingIdId(codingId);
        List<FileManagement> fileManagement3= fileManagementServer.selectFileManagement(fileManagement2);
        return fileManagement3;
    }

//    /**
//     * 基础服务实现业务查询
//     * @param fileManagement
//     * @return
//     */
//    @Override
//    public List<FileManagement> selectFileManagement(FileManagement fileManagement) {
//        return fileManagementMapper.selectFileManagement(fileManagement);
//    }
//    /**
//     * 基础服务实现业务添加
//     * @param fileManagement
//     * @return
//     */
//    @Override
//    public boolean insertFileManagement(FileManagement fileManagement) {
//        int count = fileManagementMapper.insert(fileManagement);
//        if(count<1){
//            return false;
//        }
//        return true;
//    }
//    /**
//     * 基础服务实现业务修改
//     * @param basicServices
//     * @return
//     */
//    @Override
//    public boolean updateBasicServices(BasicServices basicServices) {
//        if(null==basicServices.getServerId()){
//            return false;
//        }
//        int count = basicServicesMapper.updateBasicServices(basicServices);
//        if(count<1){
//            return false;
//        }
//        return true;
//    }
}
