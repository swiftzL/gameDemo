package com.zl.server.play.quest.config;

import com.zl.server.play.bag.item.Item;
import com.zl.server.play.quest.resource.Award;

import java.util.HashMap;
import java.util.Map;

public class AwardManager {
    public static Map<Integer, Award> awardMap = new HashMap<>();

    static {

        awardMap.put(1, new Award(1, 2));
        awardMap.put(2, new Award(2, 2));

    }
}
