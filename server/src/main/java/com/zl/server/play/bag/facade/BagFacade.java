package com.zl.server.play.bag.facade;

import com.zl.server.commons.Command;
import com.zl.server.netty.anno.NetMessageHandler;
import com.zl.server.netty.anno.NetMessageInvoke;
import com.zl.server.netty.anno.Param;
import com.zl.server.play.bag.packet.MS_ConsumeProps;
import com.zl.server.play.bag.packet.MS_Props;
import com.zl.server.play.bag.service.BagService;
import org.springframework.beans.factory.annotation.Autowired;

@NetMessageHandler
public class BagFacade {

    @Autowired
    private BagService bagService;

    @NetMessageInvoke(Command.PutProps)
    public void putProps(@Param("id") Integer playerId, MS_Props req) throws Exception {
        bagService.putProps(playerId, req);
    }

    @NetMessageInvoke(Command.ConsumeProps)
    public void consumeProps(@Param("id") Integer playerId, MS_ConsumeProps req) {
        bagService.consumeProps(playerId, req);
    }

    @NetMessageInvoke(Command.ShowBag)
    public void showBag(@Param("id") Integer playerId) {
        bagService.showBag(playerId);
    }




}
