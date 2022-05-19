package com.zl.server.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LocalCache extends LinkedHashMap {

    public static LocalCache Instance = new LocalCache(200);

    private int capacity;

    public LocalCache(int capacity){
        super(16,0.75f,true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > capacity;
    }

}
