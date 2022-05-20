package com.zl.server.cache;

public interface Persist extends Runnable {

    void destroy();

    Object get(Object id);

    void put(Object id, Object obj);


}
