package com.zl.server.config;


import com.zl.server.anno.NetMessageHandler;
import com.zl.server.anno.NetMessageInvoke;
import com.zl.server.commons.Command;
import com.zl.server.netty.Invoke;
import com.zl.server.netty.ObjectInvoke;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class NetMessageProcessor implements  BeanFactoryAware, ApplicationRunner {
    public static Map<Integer, Invoke> invokes = new HashMap<>();

    private ConfigurableListableBeanFactory beanFactory;




    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, Object> objectMap = this.beanFactory.getBeansWithAnnotation(NetMessageHandler.class);
        objectMap.forEach((name,bean)->{
            Class<?> clazz = beanFactory.getType(name);
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                NetMessageInvoke netMessageInvoke = method.getDeclaredAnnotation(NetMessageInvoke.class);
                if (netMessageInvoke == null) {
                    continue;
                }
                Command command = netMessageInvoke.value();
                invokes.put(command.getCode(), new ObjectInvoke(bean, method));
            }
        });

    }
}
