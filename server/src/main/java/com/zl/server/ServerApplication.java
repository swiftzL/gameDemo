package com.zl.server;

import com.alibaba.fastjson.JSON;
import com.zl.server.cache.EntityCache;
import com.zl.server.cache.Persist;
import com.zl.server.dto.TaskDto;
import com.zl.server.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableTransactionManagement
public class ServerApplication {

    @Autowired
    private Persist persist;

    @PersistenceContext
    private EntityManager entityManager;

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


}
