package com.zl.server.event;


import com.alibaba.fastjson.JSON;
import com.zl.server.cache.EntityCache;
import com.zl.server.cache.Persist;
import com.zl.server.commons.TaskType;
import com.zl.server.dto.TaskDto;
import com.zl.server.model.Account;
import com.zl.server.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.concurrent.Executor;


@Component
public class EventBroadCast {

    @Qualifier("asyncPoolExecutor")
    @Autowired
    private Executor executor;

    @Autowired
    @Qualifier("taskEntityCache")
    private EntityCache<Integer, Task> taskEntityCache;


    public void publish(Event event, Object obj) {
        executor.execute(() -> {
            switch (event) {
                case LEVEL:
                    Integer id = (Integer) obj;
                    Task task = taskEntityCache.loadOrCreate(id);
                    List<TaskDto> dtos = JSON.parseArray(task.getData(),TaskDto.class);
                    for (TaskDto taskDto : dtos) {
                        if (taskDto.getTaskType() == TaskType.LevelUp.getCode()) {
                            taskDto.setTaskStatus(1);
                        }
                    }
                    task.setData(JSON.toJSONString(dtos));
                default:
                    break;
            }
        });
    }

}
