package com.zl.client.function.equipment.facede;

import com.alibaba.fastjson.JSON;
import com.zl.client.common.RequestUtil;
import com.zl.client.common.Response;
import com.zl.client.function.Function;
import com.zl.client.function.equipment.model.MS_Equipment;
import com.zl.common.common.Command;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class RemoveEquipment extends Function {
    @Override
    public int getCode() {
        return Command.RemoveEquipment.getCode();
    }

    @Override
    public void run() throws ExecutionException, InterruptedException {
        System.out.println("输入装备id");
        MS_Equipment ms_equipment =new MS_Equipment();
        ms_equipment.setModelId(scanner.nextInt());
        RequestUtil.requestFuture(this.channel, getCode(), JSON.toJSONBytes(ms_equipment));
    }
}
