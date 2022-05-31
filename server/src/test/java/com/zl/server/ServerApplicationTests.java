package com.zl.server;

import com.alibaba.fastjson.JSON;
import com.zl.server.cache.persist.Persist;
import com.zl.server.play.bag.model.BagBox;
import com.zl.server.play.bag.resource.Attack;
import com.zl.server.play.base.dao.AccountDao;
import com.zl.server.play.base.model.AccountBox;
import com.zl.server.play.base.model.AttrStorage;
import com.zl.server.play.base.model.EquipmentStorage;
import com.zl.server.play.quest.model.QuestBox;
import com.zl.server.play.bag.item.Item;
import com.zl.server.play.quest.model.QuestStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ServerApplicationTests {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private Persist persist;

    public static void mainww(String[] args) {


    }

    public static void main2(String[] args) {


    }

    public static void main(String[] args) {
//        BagBox bagBox = new BagBox();
//        bagBox.setBagCap(100);
//        bagBox.setItems(new Item[100]);
//        System.out.println(JSON.toJSONString(bagBox));

        QuestBox questBox   = new QuestBox();
        questBox.setQuestStorages(new ArrayList<>());
        System.out.println(JSON.toJSONString(questBox));
    }


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
