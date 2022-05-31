package com.zl.server.play.player;

import com.zl.server.cache.EntityCache;
import com.zl.server.cache.anno.Storage;
import com.zl.server.play.bag.context.PropsContext;
import com.zl.server.play.bag.item.Item;
import com.zl.server.play.bag.model.Bag;
import com.zl.server.play.bag.model.BagBox;
import com.zl.server.play.bag.resource.Props;
import com.zl.server.play.base.model.Account;
import com.zl.server.play.base.model.AttrStorage;
import com.zl.server.play.player.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PlayerServiceContext {

    public static PlayerServiceContext INSTANCE;

    @Autowired
    private PlayerService playerService;

    @PostConstruct
    public void init() {
        INSTANCE = this;
    }

    public static PlayerService getPlayerService() {
        return INSTANCE.playerService;
    }
}
