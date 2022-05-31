package com.zl.server.play.bag.service;

import com.zl.server.play.bag.packet.MS_ConsumeProps;
import com.zl.server.play.bag.packet.MS_Props;

public interface BagService {
    void putProps(Integer playerId, MS_Props ms_props) ;

    void consumeProps(Integer playerId, MS_ConsumeProps req);

    void showBag(Integer playerId);


}
