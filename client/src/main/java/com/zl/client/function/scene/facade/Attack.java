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
        System.out.println("请输入攻击目标ID");
        MS_Attack req = new MS_Attack();
        req.setAttackedPlayerId(scanner.nextInt());
        RequestUtil.requestFuture(this.channel, getCode(), JSON.toJSONString(req).getBytes());
    }
}
