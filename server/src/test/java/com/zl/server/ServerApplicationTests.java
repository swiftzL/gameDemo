package com.zl.server;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.Persist;
import com.zl.server.dao.AccountDao;
import com.zl.server.dto.AccountDto;
import com.zl.server.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class ServerApplicationTests {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private Persist persist;


//    @Test
//    @Transactional
//    void contextLoads() throws InterruptedException {
//
//        EntityCache<Integer, Account> entityCache = new EntityCache<>(Account.class,entityManager,persist);
//        new Thread(entityCache.getPersist()).start();
//        Account account = entityCache.load(1);
//        account.setLevel(6);
//        entityCache.writeBack(1,account);
////        accountDao.save(account);
////        entityManager.persist(account);
//        System.out.println(entityCache.load(1));
//        TimeUnit.SECONDS.sleep(100);
//
//
//    }

}
