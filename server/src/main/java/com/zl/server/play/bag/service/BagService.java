package com.zl.server.play.bag.service;

import com.zl.common.message.NetMessage;
import com.zl.server.play.bag.packet.MR_BagStatus;
import com.zl.server.play.bag.packet.MS_ConsumeProps;
import com.zl.server.play.bag.packet.MS_Equipment;
import com.zl.server.play.bag.packet.MS_Props;

public interface BagService {
    void putProps(Integer playerId, MS_Props ms_props) throws Exception;

    void consumeProps(Integer playerId, MS_ConsumeProps ms_consumProps);

    void showBag(Integer playerId);

    void removeEquipment(Integer playerId, MS_Equipment ms_equipment) throws Exception;

    void useEquipment(Integer playerId, MS_Equipment ms_equipment);
}
