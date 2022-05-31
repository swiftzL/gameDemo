package com.zl.server.play.quest.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuestStorage implements Cloneable{
    private Integer taskId; //任务Id
    private Integer taskStatus; //任务状态
    private Integer current;
    private Integer maxCount;
    private String taskName; //任务名
    private Integer taskType; //任务类型
    private Integer isReceive;//是否领取
}
