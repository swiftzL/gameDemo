package com.zl.client.function.quest.facade;

import com.alibaba.fastjson.JSON;
import com.zl.client.common.RequestUtil;
import com.zl.client.function.Function;
import com.zl.client.function.quest.model.MS_Quest;
import com.zl.common.common.Command;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class DrawAwardQuest extends Function {
    @Override
    public int getCode() {
        return Command.DrawAward.getCode();
    }

    @Override
    public void run() throws ExecutionException, InterruptedException {
        System.out.println("输入任务id");
        MS_Quest ms_quest = new MS_Quest();
        ms_quest.setQuestId(scanner.nextInt());
        RequestUtil.requestFuture(this.channel, getCode(), JSON.toJSONBytes(ms_quest));
    }
}
