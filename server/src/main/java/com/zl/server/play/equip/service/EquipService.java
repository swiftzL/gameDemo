package com.zl.server.play.equip.service;

import com.zl.server.play.equip.packet.MS_Equipment;

public interface EquipService {

    void unUseEquipment(Integer playerId, MS_Equipment ms_equipment);

    void useEquipment(Integer playerId, MS_Equipment ms_equipment);
}
