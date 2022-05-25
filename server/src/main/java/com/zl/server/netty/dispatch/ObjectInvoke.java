package com.zl.server.netty.dispatch;


import com.zl.server.netty.dispatch.Invoke;
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

    public ObjectInvoke(Object obj,Method method) {
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
    }

    @Override
    public Object invoke(Object... obj) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(object,obj);
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
}
