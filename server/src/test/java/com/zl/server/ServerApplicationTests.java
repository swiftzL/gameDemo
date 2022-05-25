package com.zl.server;

import com.alibaba.fastjson.JSON;
import com.zl.server.cache.Persist;
import com.zl.server.play.bag.model.BagModel;
import com.zl.server.play.base.dao.AccountDao;
import com.zl.server.play.quest.model.QuestBox;
import com.zl.server.play.quest.model.QuestModel;
import com.zl.server.play.bag.resource.Item;
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

    public static void main(String[] args) {
        BagModel bagModel = new BagModel();
        bagModel.setBagCap(100);
        bagModel.setItems(new Item[100]);
        System.out.println(JSON.toJSONString(bagModel));
    }

    public static void main2(String[] args) {
        QuestBox questBox = new QuestBox();
        List<QuestModel> list = new ArrayList<>();
        QuestModel questModel = new QuestModel();
        questModel.setCurrent(0);
        questModel.setMaxCount(1);
        questModel.setTaskStatus(1);
        questModel.setTaskId(1);
        questModel.setTaskType(1);
        questModel.setTaskName("提升等级到14级");
        questModel.setTaskStatus(0);
        list.add(questModel);
        questBox.setQuestModels(list);
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
