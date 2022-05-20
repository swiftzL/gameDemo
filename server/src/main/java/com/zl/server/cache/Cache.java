package com.zl.server.cache;

public interface Cache<PK,T> {

    T load(PK id);

    T loadOrCreate(PK id);

    void update(PK id,T t);
}
