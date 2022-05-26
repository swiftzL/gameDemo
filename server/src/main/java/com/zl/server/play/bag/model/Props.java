package com.zl.server.play.bag.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Props<T> {
    private int id;
    private int type;
    private String name;
    private int count;
    private T properties;
}
