package com.zl.server.play.base.model;

import com.zl.server.play.bag.item.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipmentStorage {
    private Item cloth;//衣服
    private Item shoe;//鞋子
    private Item weapon;//武器
}
