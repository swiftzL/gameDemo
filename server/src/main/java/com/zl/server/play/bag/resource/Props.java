package com.zl.server.play.bag.resource;

import com.zl.server.play.bag.item.param.ItemParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Props {
    private int id;
    private int type;
    private String name;
    private int count;
    private ItemParam itemParam;
}
