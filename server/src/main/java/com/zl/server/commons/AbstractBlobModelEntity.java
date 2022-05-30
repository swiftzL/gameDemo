package com.zl.server.commons;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;

@MappedSuperclass
public abstract class AbstractBlobModelEntity<M> {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String data;

    @Transient
    private M mode;

    public void setData(String data) {
        this.data = data;
    }

    public M getModel() {
        return this.mode;
    }

    public void setMode(M mode) {
        this.mode = mode;
    }

    public String getData() {
        return data;
    }

    public Integer getId() {
        return id;
    }
}
