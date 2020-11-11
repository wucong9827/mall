package com.demo.pay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wucong
 * @date 2020/10/31 21:40
 * @description com.demo.pay
 */
@SpringBootApplication
@MapperScan(basePackages = "com.demo.pay.dao")
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class);
    }
}
