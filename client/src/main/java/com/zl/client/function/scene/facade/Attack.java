package com.zl.client.function.scene.facade;

import com.alibaba.fastjson.JSON;
import com.zl.client.common.RequestUtil;
import com.zl.client.function.Function;
import com.zl.client.function.scene.model.MS_Attack;
import com.zl.common.common.Command;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class Attack extends Function {
    @Override
    public int getCode() {
        return Command.Attack.getCode();
    }

    @Override
    public void run() throws ExecutionException, InterruptedException {
        MS_Attack req = new MS_Attack();
        String s = lineReader.readLine("请输入攻击目标ID>");
        try {
            int i = Integer.parseInt(s);
            req.setAttackedPlayerId(i);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        RequestUtil.requestFuture(this.channel, getCode(), JSON.toJSONString(req).getBytes());
    }
}
