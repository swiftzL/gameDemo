package com.zl.server.netty.invoke;


import com.zl.server.netty.anno.NetMessageInvoke;
import com.zl.server.netty.model.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ObjectInvoke implements Invoke {
    private boolean isVoid;
    private Object object;
    private Method method;
    private Class[] args;
    private boolean returnIsResponse;
    private Parameter[] parameters;
    private NetMessageInvoke netMessageInvoke;

    public ObjectInvoke(Object obj, Method method, NetMessageInvoke netMessageInvoke) {
        this.object = obj;
        this.method = method;
        Parameter[] parameters = method.getParameters();
        args = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            args[i] = parameters[i].getType();
        }
        this.isVoid = method.getReturnType().getName().equals("void");
        this.returnIsResponse = method.getReturnType().equals(Response.class);
        this.parameters = parameters;
        this.netMessageInvoke = netMessageInvoke;
    }

    @Override
    public Object invoke(Object... obj) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(object, obj);
    }

    @Override
    public Class[] getArgsType() {
        return this.args;
    }

    @Override
    public boolean isVoid() {
        return this.isVoid;
    }

    @Override
    public boolean returnIsResponse() {
        return this.returnIsResponse;
    }

    @Override
    public Parameter[] getParameters() {
        return this.parameters;
    }

    public NetMessageInvoke getNetMessageInvoke() {
        return this.netMessageInvoke;
    }
}
