package com.zl.server.play.bag.packet;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MS_ConsumProps {
    private int type;
    private int[] idxs;
    private int num;
}
