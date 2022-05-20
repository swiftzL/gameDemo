package com.zl.server.dto;

import lombok.Data;

@Data
public class TaskDto {
    private Integer taskId; //任务Id
    private Integer taskStatus; //任务状态
    private String taskName; //任务名
    private Integer taskType; //任务类型
}
