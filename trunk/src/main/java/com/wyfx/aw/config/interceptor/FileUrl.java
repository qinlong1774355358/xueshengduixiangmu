package com.wyfx.aw.config.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName: FileUrl
 * @Description: 文件映射
 * @author: zhangguliang
 * @date: 2019-12-9
 */
@Configuration
public class FileUrl extends WebMvcConfigurerAdapter {
    //通过映射路径访问存放文件目录
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    //上传文件存放目录
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println(uploadFolder);
        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:" + uploadFolder);
    }
}
