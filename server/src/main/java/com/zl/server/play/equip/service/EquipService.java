package com.zl.server.play.equip.service;

import com.zl.server.play.equip.packet.MS_Equipment;

public interface EquipService {

    void removeEquipment(Integer playerId, MS_Equipment ms_equipment) throws Exception;

    void useEquipment(Integer playerId, MS_Equipment ms_equipment);
}
