package com.zl.server.play.bag.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public  abstract class Item {
    private int modelId;
    private int count;
}
