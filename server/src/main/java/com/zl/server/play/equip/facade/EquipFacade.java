package com.zl.server.play.equip.facade;

import com.zl.server.commons.Command;
import com.zl.server.netty.anno.NetMessageHandler;
import com.zl.server.netty.anno.NetMessageInvoke;
import com.zl.server.netty.anno.Param;
import com.zl.server.play.equip.packet.MS_Equipment;
import com.zl.server.play.equip.service.EquipService;
import org.springframework.beans.factory.annotation.Autowired;

@NetMessageHandler
public class EquipFacade {

    @Autowired
    private EquipService equipService;

    @NetMessageInvoke(Command.UseEquipment)
    public void useEquipment(@Param("id") Integer playerId, MS_Equipment req) {
        equipService.useEquipment(playerId, req);
    }

    @NetMessageInvoke(Command.RemoveEquipment)
    public void removeEquipment(@Param("id") Integer playerId, MS_Equipment req) throws Exception {
        equipService.removeEquipment(playerId, req);
    }
}
