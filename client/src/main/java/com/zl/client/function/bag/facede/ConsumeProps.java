package com.zl.client.function.bag.facede;

import com.alibaba.fastjson.JSON;
import com.zl.client.common.RequestUtil;
import com.zl.client.common.Response;
import com.zl.client.function.Function;
import com.zl.client.function.bag.model.MS_ConsumProps;
import com.zl.client.function.bag.model.MS_Props;
import com.zl.common.common.Command;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class ConsumeProps extends Function {
    @Override
    public int getCode() {
        return Command.ConsumeProps.getCode();
    }

    @Override
    public void run() throws ExecutionException, InterruptedException {
        System.out.println("输入背包索引1-2-3");
        String s = scanner.nextLine();
        MS_ConsumProps ms_consumProps = new MS_ConsumProps();
        String[] split = s.split("-");
        int[] idxs = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            idxs[i] = Integer.valueOf(split[i]);
        }
        System.out.println("道具数量");
        int num = scanner.nextInt();
        System.out.println("道具id");
        ms_consumProps.setModelId(scanner.nextInt());
        ms_consumProps.setIdxs(idxs);
        ms_consumProps.setNum(num);
        RequestUtil.requestFuture(this.channel, getCode(), JSON.toJSONBytes(ms_consumProps));
    }
}
