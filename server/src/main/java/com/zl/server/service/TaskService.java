package com.zl.server.service;

import com.zl.server.anno.NetMessageHandler;
import com.zl.server.anno.NetMessageInvoke;
import com.zl.server.cache.EntityCache;
import com.zl.server.cache.Persist;
import com.zl.server.commons.Command;
import com.zl.server.commons.Response;
import com.zl.server.model.Account;
import com.zl.server.model.Task;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.EntityManager;


@NetMessageHandler
public class TaskService {

    @Autowired
    @Qualifier("taskEntityCache")
    EntityCache<Integer, Task> entityCache;


    @NetMessageInvoke(Command.ShowTask)
    public Response showTask(Channel channel) {
        AttributeKey attributeKey = AttributeKey.valueOf("id");
        Attribute<Integer> attr = channel.attr(attributeKey);
        if (attr.get() == null) {
            return Response.err("当前用户未登录");
        }
        Task task = entityCache.loadOrCreate(attr.get());
        return Response.success(task.getData());
    }

}
