package com.zl.server;

import com.alibaba.fastjson.JSON;
import com.zl.server.cache.EntityManagerContext;
import com.zl.server.cache.Persist;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.quest.model.Quest;
import com.zl.server.play.quest.packet.QuestBox;
import com.zl.server.play.quest.packet.QuestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ServerApplication  {

    @Autowired
    private EntityManagerContext entityManagerContext;


    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
