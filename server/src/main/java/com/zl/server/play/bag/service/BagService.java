package com.zl.server.play.bag.service;

import com.zl.common.message.NetMessage;
import com.zl.server.play.bag.packet.MR_BagStatus;
import com.zl.server.play.bag.packet.MS_ConsumProps;
import com.zl.server.play.bag.packet.MS_Props;

public interface BagService {
    NetMessage putProps(Integer playerId, MS_Props ms_props) throws Exception;

    NetMessage consumeProps(Integer playerId, MS_ConsumProps ms_consumProps);

    MR_BagStatus showBag(Integer playerId);
}
