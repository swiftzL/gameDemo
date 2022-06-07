package com.zl.client.function.scene.facade;

import com.alibaba.fastjson.JSON;
import com.zl.client.common.RequestUtil;
import com.zl.client.function.Function;
import com.zl.client.function.scene.model.MS_CreateFightScene;
import com.zl.common.common.Command;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class CreateScene extends Function {
    @Override
    public int getCode() {
        return Command.CreateFightScene.getCode();
    }

    @Override
    public void run() throws ExecutionException, InterruptedException {
        System.out.println("请输入边界和场景最大人数 3-5-5");
        String s = scanner.nextLine();
        String[] split = s.split("-");
        MS_CreateFightScene req = new MS_CreateFightScene();
        req.setDown(Integer.parseInt(split[0]));
        req.setRight(Integer.parseInt(split[1]));
        req.setMaxCount(Integer.parseInt(split[2]));
        RequestUtil.requestFuture(this.channel, getCode(), JSON.toJSONString(req).getBytes());
    }
}
