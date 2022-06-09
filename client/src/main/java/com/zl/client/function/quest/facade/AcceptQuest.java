package com.zl.client.function.quest.facade;

import com.alibaba.fastjson.JSON;
import com.zl.client.common.RequestUtil;
import com.zl.client.function.Function;
import com.zl.client.function.quest.model.MS_Quest;
import com.zl.common.common.Command;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class AcceptQuest extends Function {
    @Override
    public int getCode() {
        return Command.AcceptQuest.getCode();
    }

    @Override
    public void run() throws ExecutionException, InterruptedException {
        String s = lineReader.readLine("输入任务id>");
        MS_Quest ms_quest = new MS_Quest();
        try {
            int i = Integer.parseInt(s);
            ms_quest.setQuestId(i);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        RequestUtil.requestFuture(this.channel, getCode(), JSON.toJSONString(ms_quest).getBytes());
    }
}
