package com.wyfx.aw.controller;

import com.wyfx.aw.controller.commons.ResponseCode;
import com.wyfx.aw.controller.commons.ResponseEntity;
import com.wyfx.aw.entity.FileManagement;
import com.wyfx.aw.network.queue.MessageQueue;
import com.wyfx.aw.network.vo.CmdUtil;
import com.wyfx.aw.network.vo.Pcmd;
import com.wyfx.aw.service.FileManagementServer;
import com.wyfx.aw.service.SharingServer;
import com.wyfx.aw.utils.DownloadUtile;
import com.wyfx.aw.utils.FileReceive;
import com.wyfx.aw.utils.InterfaceCallUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * @ClassName: ManagementController
 * @Description: 文件管理
 * @author: zhangguliang
 * @date: 2019-11-6
 */
@RestController
@RequestMapping("/aw/fileManagement")
public class FileManagementController {
    //文件管理业务接口
    @Autowired
    private FileManagementServer fileManagementServer;
    //共用业务类
    @Autowired
    private SharingServer SharingServer;
    //文件上传工具
    @Autowired
    private FileReceive fileReceive;
    private  FileManagement fileManagement;
    /**
     * 所有文件夹与文件查询
     * @return
     */
    @RequestMapping(value = "/getFile",method = RequestMethod.GET)
    public Object getFileManagement() {
        List<FileManagement> fileManagement1= fileManagementServer.selectFileManagement(fileManagement);
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),fileManagement1);
    }
    /**
     * 根据服务号serverId查询当前服务器下面所有的文件与文件夹
     * @return
     */
    @RequestMapping(value = "/getFileServerById",method = RequestMethod.GET)
    public Object getFileManagementServerId(FileManagement fileManagement) {
        if(null==fileManagement.getServerId()){
            return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"请输入服务号serverId");
        }
        List<FileManagement> fileManagement1= fileManagementServer.selectFileManagement(fileManagement);
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),fileManagement1);
    }

    /**
     * 根据id文件查询当前文件夹下所有子文件夹与文件
     * @param fileManagement
     * @return
     */
    @RequestMapping(value = "/getFileById",method = RequestMethod.GET)
    public Object getFileManagementByID(FileManagement fileManagement) {
       if(null==fileManagement.getId()){
           return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"请输入id");
       }
        List<FileManagement> fileManagement3 = SharingServer.getFileById(fileManagement);
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),fileManagement3);
    }

    /**
     * 根据id查询单个文件
     * @return
     */
    @RequestMapping(value = "/getFilSingleById",method = RequestMethod.GET)
    public Object getFileManagement(FileManagement fileManagement) {
        if(null==fileManagement.getId()){
            return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"请输入id");
        }
        List<FileManagement> fileManagement1= fileManagementServer.selectFileManagement(fileManagement);
        if(fileManagement1.size()==0){
            return new ResponseEntity(ResponseCode.SUCCESS.getValue(),"无文件，请重新输入id");
        }
       if(fileManagement1.get(0).getType()==0){
          return new ResponseEntity(ResponseCode.SUCCESS.getValue(),"非文件，请重新输入id");
       }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),fileManagement1);
    }
    /**
     * 文件上传
     * @param file
     * @return
     */
    @RequestMapping(value = "/addFileupload",method = RequestMethod.POST)
    public Object getFileManagementByID(@RequestParam("file") MultipartFile file, String directoryName, FileManagement fileManagement) {
        String message = "上传失败";
        boolean  management=false;
        if(null==fileManagement.getServerId()){
            return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),message+",请输入服务器serverId");
        }
        if(null==fileManagement.getId()){
            message+=",请输入id，存放目录下的id，详情请查看所有文件夹与文件接口，http://ip：端口/aw/fileManagement/getFile";
            return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),message);
        }
        try {
            fileManagement.setType(0);
            List<FileManagement> fileManagement1= fileManagementServer.selectFileManagement(fileManagement);
            if(fileManagement1.size()<1){
                return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),message);
            }
           String str = fileManagement1.get(0).getFilePath();
           String uploadPath =  str.substring(str.lastIndexOf("fileDirectory")+"fileDirectory".length())+"/"+directoryName;
            boolean fig = fileReceive.fileupload(file.getInputStream(),uploadPath,file.getOriginalFilename());
            if(!fig){
                return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),message);
            }
            String fileName = file.getOriginalFilename();
            //判断文件路径是否存在
            FileManagement count =  new FileManagement();
            if (null!=directoryName){
                count.setFilePath(fileManagement1.get(0).getFilePath()+"/"+directoryName);
                List<FileManagement> list= fileManagementServer.selectFileManagement(count);
                if(list.size()<1){
                    fileManagement.setId(null);
                    String uuid = UUID.randomUUID().toString();
                     uuid = uuid.replaceAll("-","");
                     fileManagement.setCodingId(uuid);
                     fileManagement.setType(0);
                     fileManagement.setFilePath(fileManagement1.get(0).getFilePath()+"/"+directoryName);
                     fileManagement.setFileName(directoryName);
                     fileManagement.setUpdateTime(new Date());
                     fileManagement.setCodingIdId(fileManagement1.get(0).getCodingId());
                     management= fileManagementServer.insertFileManagement(fileManagement);
                }
                 fileName = directoryName+"/"+file.getOriginalFilename();
            }
            if(null!=file.getOriginalFilename()&&!"".equals(file.getOriginalFilename())){
                count.setFilePath(fileManagement1.get(0).getFilePath()+"/"+fileName);
                List<FileManagement> list= fileManagementServer.selectFileManagement(count);
                if(list.size()<1){
                    fileManagement.setId(null);
                    fileManagement.setCodingId("1");
                    fileManagement.setType(1);
                    fileManagement.setFilePath(fileManagement1.get(0).getFilePath()+"/"+fileName);
                    fileManagement.setFileName(file.getOriginalFilename());
                    fileManagement.setUpdateTime(new Date());
                    fileManagement.setCodingIdId(fileManagement1.get(0).getCodingId());
                    management= fileManagementServer.insertFileManagement(fileManagement);
                  }
            }
            if(management){
                message = "上传成功";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),message);
    }

    /**
     * 文件上传命令控制
     * @return
     */
    @RequestMapping(value = "/gerCommandFileName",method = RequestMethod.POST)
    public Object gerCommandFileName(int serverId) {

        byte[] bytes= null;
        Pcmd pcmd=new Pcmd(serverId,(int) CmdUtil.FILE_UPLOAD,0,0,bytes);
        try {
            MessageQueue.sendMessage(serverId,pcmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"文件上传命令");
    }


    /**
     * 下载文件
     * @return
     */
    @RequestMapping(value = "/getDownloadUtile",method = RequestMethod.POST)
    public Object getDownloadUtile() throws Exception {
        fileManagement = new FileManagement();
        fileManagement.setType(1);
        List<FileManagement> fileManagement1= fileManagementServer.selectFileManagement(fileManagement);
        List list = new ArrayList<>();
//        if(null==fileManagement.getId()){
//            return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"请输入id");
//        }
//        List<FileManagement> fileManagement3 = SharingServer.getFileById(fileManagement);
//         String filePath = "";
//        Map map = new HashMap();
//        for (FileManagement fileManagement4:fileManagement3) {
//            if(fileManagement4.getType()==0){
//                String str = fileManagement4.getFilePath();
//                String path = str.substring(str.lastIndexOf("fileDirectory")+"fileDirectory".length());
//                filePath = filePathName+path;
//            }
//            if(fileManagement4.getType()==1){
//              String url = fileManagement4.getFilePath().replace("localhost",fileReceive.getLocalhost());
//              fileManagement4.setFilePath(url);
//              System.out.println(fileManagement4.getFilePath());
//              map.put("fileManagement",fileManagement4);
//            }
//        }
//        System.out.println("==========="+map);
//        String path = "http://localhost:8081/fileDirectory/name13/200203.csv";
//        DownloadUtile.saveUrlAs(path,filePathName);
        for (FileManagement fileManagement:fileManagement1) {
           String str =  fileManagement.getFilePath().replace("localhost",fileReceive.getLocalhost());
            fileManagement.setFilePath(str);
            list.add(fileManagement);
        }
        String message = "下载获取成功";
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),list);
    }

    /**
     * 根据id删除当前目录下所有子文件夹与文件
     * @param fileManagement
     * @return
     */
    @RequestMapping(value = "/deleteFile",method = RequestMethod.POST)
    public Object deleteFile(FileManagement fileManagement) throws IOException {
        List list = new ArrayList<>();
        if(null==fileManagement.getId()){
            return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"请输入id");
        }
        List<FileManagement> fileManagement3 = fileManagementServer.selectFileManagement(fileManagement);
        if (fileManagement3.size()>0){
            fileManagementServer.deleteFileManagement(fileManagement3.get(0));
                File file1 = new File("");
                String filePath =fileReceive.getFilePath();
                System.out.println(fileReceive.getFilePath());
                System.out.println("============="+filePath);
                String str = fileManagement3.get(0).getFilePath();
                String[] sp = str.split("fileDirectory");
                if(sp.length>1){
                    filePath=filePath+sp[1];
                    System.out.println(filePath);
                }
                DownloadUtile.delDir(new File(filePath));
        }
            fileReceive.createExists();
        return  new ResponseEntity(ResponseCode.SUCCESS.getValue(),"删除成功");
    }

}
