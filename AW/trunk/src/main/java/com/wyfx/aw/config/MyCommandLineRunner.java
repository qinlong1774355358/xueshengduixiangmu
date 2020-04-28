package com.wyfx.aw.config;

import com.wyfx.aw.network.BootstrapHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author johnliu
 * @create 2018/8/31 20:12
 * @Description 在Spring容器加载完成后执行，可防止applicationContext为null
 * 当有多个CommandLineRunner接口实现的时候，可以通过@Order(value = 1)来指定加载顺序，值越小，越先加载
 **/
@Component
@Order(value = 1)
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    /*@Value("${tcp_port}")
    private String msg;*/

    @Override
    public void run(String... args) throws Exception {
        /*int port=Integer.parseInt(msg);
        new AwSever(port).start();*/
        new BootstrapHelper();
    }
}
