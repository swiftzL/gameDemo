package com.zl.server.netty.invoke;

import com.zl.server.netty.anno.NetMessageInvoke;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

public interface Invoke {

    Object invoke(Object ...obj) throws InvocationTargetException, IllegalAccessException;

    Class[] getArgsType();

    boolean isVoid();

    boolean returnIsResponse();

    Parameter[] getParameters();

    NetMessageInvoke getNetMessageInvoke();

}
