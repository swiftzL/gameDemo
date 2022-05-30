package com.zl.server.play.bag.facade;

import com.zl.common.message.NetMessage;
import com.zl.server.commons.Command;
import com.zl.server.netty.anno.NetMessageHandler;
import com.zl.server.netty.anno.NetMessageInvoke;
import com.zl.server.netty.anno.Param;
import com.zl.server.play.bag.packet.MR_BagStatus;
import com.zl.server.play.bag.packet.MS_ConsumeProps;
import com.zl.server.play.bag.packet.MS_Equipment;
import com.zl.server.play.bag.packet.MS_Props;
import com.zl.server.play.bag.service.BagService;
import org.springframework.beans.factory.annotation.Autowired;

@NetMessageHandler
public class BagFacade {

    @Autowired
    private BagService bagService;

    @NetMessageInvoke(Command.PutProps)
    public NetMessage putProps(@Param("id") Integer playerId, MS_Props ms_props) throws Exception {
        return bagService.putProps(playerId, ms_props);
    }

    @NetMessageInvoke(Command.ConsumeProps)
    public NetMessage consumeProps(@Param("id") Integer playerId, MS_ConsumeProps ms_consumeProps) {
        return bagService.consumeProps(playerId, ms_consumeProps);
    }

    @NetMessageInvoke(Command.ShowBag)
    public MR_BagStatus showBag(@Param("id") Integer playerId) {
        return bagService.showBag(playerId);
    }

    @NetMessageInvoke(Command.UseEquipment)
    public NetMessage useEquipment(@Param("id") Integer playerId, MS_Equipment ms_equipment) {
        return bagService.useEquipment(playerId, ms_equipment);
    }

    @NetMessageInvoke(Command.RemoveEquipment)
    public NetMessage removeEquipment(@Param("id") Integer playerId, MS_Equipment ms_equipment) throws Exception {
        return bagService.removeEquipment(playerId, ms_equipment);
    }




}
