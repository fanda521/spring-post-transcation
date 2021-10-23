package com.wang.springposttransaction;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("com.wang.springposttransaction.mapper")
class SpringPostTransactionApplicationTests {

    @Test
    void contextLoads() {
    }

}
