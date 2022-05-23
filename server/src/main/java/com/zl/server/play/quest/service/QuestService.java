package com.zl.server.play.quest.service;

import com.zl.common.message.NetMessage;
import com.zl.server.netty.NetConnection;

public interface QuestService {
    NetMessage showTask(NetConnection netConnection);
}
