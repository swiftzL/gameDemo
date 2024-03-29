package com.zl.client.function.scene.facade;

import com.alibaba.fastjson.JSON;
import com.zl.client.common.RequestUtil;
import com.zl.client.function.Function;
import com.zl.client.function.scene.model.MS_JoinFightScene;
import com.zl.common.common.Command;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class JoinScene extends Function {

    @Override
    public int getCode() {
        return Command.JoinScene.getCode();
    }

    @Override
    public void run() throws ExecutionException, InterruptedException {
        MS_JoinFightScene req = new MS_JoinFightScene();
        try {
            String s = lineReader.readLine("请输入场景Id");
            int i = Integer.parseInt(s);
            req.setSceneId(i);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        RequestUtil.requestFuture(this.channel, getCode(), JSON.toJSONString(req).getBytes());

    }
}
