package com.wyfx.aw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wyfx.aw.dao")
public class AwApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwApplication.class, args);
    }

}
