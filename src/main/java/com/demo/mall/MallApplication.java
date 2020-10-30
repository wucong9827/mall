package com.demo.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wucong
 * @date 2020/10/29 16:29
 * @description PACKAGE_NAME
 */
@SpringBootApplication
@MapperScan(basePackages = "com.demo.mall.dao")
public class MallApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallApplication.class);
    }
}
