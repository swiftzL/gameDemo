package com.zl.server.config;


import com.zl.server.anno.NetMessageHandler;
import com.zl.server.anno.NetMessageInvoke;
import com.zl.server.commons.Command;
import com.zl.server.proxy.Invoke;
import com.zl.server.proxy.ObjectInvoke;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class NetMessageProcessor implements BeanFactoryPostProcessor {
    public static Map<Integer, Invoke> invokes = new HashMap<>();

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanNames = beanFactory.getBeanNamesForAnnotation(NetMessageHandler.class);
        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            String beanClassName = beanDefinition.getBeanClassName();
            Class<?> beanClazz = null;
            Object obj = beanFactory.getBean(beanName);
            try {
                beanClazz = Class.forName(beanClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (beanClazz == null) {
                continue;
            }
            Method[] methods = beanClazz.getDeclaredMethods();
            for (Method method : methods) {
                NetMessageInvoke netMessageInvoke = method.getDeclaredAnnotation(NetMessageInvoke.class);
                Command command = netMessageInvoke.value();
                invokes.put(command.getCode(),new ObjectInvoke(obj,method));
            }
        }
    }
}
