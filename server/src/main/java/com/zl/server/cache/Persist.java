package com.zl.server.cache;

public interface Persist extends Runnable {

    void destroy();


    void put(Object obj);


}
