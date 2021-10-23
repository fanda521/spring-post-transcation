package com.wang.springposttransaction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wang.springposttransaction.mapper")
public class SpringPostTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPostTransactionApplication.class, args);
    }

}
