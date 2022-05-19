package com.zl.server.proxy;

import java.lang.reflect.InvocationTargetException;

public interface Invoke {

    Object invoke(Object ...obj) throws InvocationTargetException, IllegalAccessException;

    Class[] getArgsType();

    boolean isVoid();

    boolean returnIsResponse();
}
