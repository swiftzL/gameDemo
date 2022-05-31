package com.zl.server.play.base.packet.vo;

import com.zl.server.play.bag.item.Item;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountVo {
    private int attack;
    private int defense;
    private Item cloth;
    private Item shoe;
    private Item weapon;
}
