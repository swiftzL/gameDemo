package com.zl.server.play.bag.model;

import com.zl.server.resource.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class BagModel {
    private Item[] items;
    private int bagCap;   //背包容量

}
