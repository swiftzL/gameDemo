package com.zl.server.play.bag.model;

import com.zl.server.play.bag.resource.Item;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BagModel {
    private Item[] items;
    private int bagCap;   //背包容量

}
