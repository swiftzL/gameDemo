package com.zl.client.function.scene.facade;

import com.alibaba.fastjson.JSON;
import com.zl.client.common.RequestUtil;
import com.zl.client.function.Function;
import com.zl.client.function.scene.model.MS_MovePoint;
import com.zl.client.function.scene.model.Point;
import com.zl.common.common.Command;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class MovePoint extends Function {
    @Override
    public int getCode() {
        return Command.MovePoint.getCode();
    }

    @Override
    public void run() throws ExecutionException, InterruptedException {
        MS_MovePoint req = new MS_MovePoint();
        System.out.println("请输入坐标x-y");
        String s = scanner.nextLine();
        String[] split = s.split("-");
        req.setPoint(new Point(Integer.parseInt(split[0]),Integer.parseInt(split[1])));
        RequestUtil.requestFuture(this.channel, getCode(), JSON.toJSONString(req).getBytes());
    }
}
