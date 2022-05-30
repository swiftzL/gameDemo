package com.zl.server.cache.persist;

public interface Persist extends Runnable {

    void destroy();


    void put(Object obj);


}
