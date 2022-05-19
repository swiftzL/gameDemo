package com.zl.gamedemo;

import com.zl.gamedemo.dao.AccountDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GameDemoApplicationTests {

    @Autowired
    private AccountDao accountDao;

    @Test
    void contextLoads() {
        System.out.println(accountDao.findAll());
    }

}
