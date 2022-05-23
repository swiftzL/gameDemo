package com.zl.server;

import com.zl.server.cache.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootApplication
public class ServerApplication {

    @Autowired
    private Persist persist;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ServerApplication.class, args);
//        List<TaskDto> taskDtos = new ArrayList<>();
//        TaskDto taskDto = new TaskDto();
//        taskDto.setTaskId(1);
//        taskDto.setTaskName("提升等级");
//        taskDto.setTaskType(1);
//        taskDto.setTaskStatus(0);
//        taskDtos.add(taskDto);
//        System.out.println(JSON.toJSONString(taskDtos));
    }



//    @Transactional
//    public void test() {
//        Account account1 = entityManager.find(Account.class, 1);
//        System.out.println(account1);
//        account1.setLevel(6);
//        entityManager.persist(account1);
//        System.out.println("ok");
//        System.out.println(entityManager.find(Account.class, 1));
//    }
//
//
//    @Override
//    public void run(String... args) throws Exception {
//        new Thread(() -> {
//            applicationContext.getBean(ServerApplication.class).test();
//        }).start();
//        Account account = new Account();
//        account.setUsername("zl");
//        account.setId(1);
//        account.setPassword("123456");
//        account.setLevel(5);
////        accountDao.save(account);
////        entityManager.persist(account);
//
//    }


}
