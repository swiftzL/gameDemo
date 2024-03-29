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
public class UseEquipment extends Function {
    @Override
    public int getCode() {
        return Command.UseEquipment.getCode();
    }

    @Override
    public void run() throws ExecutionException, InterruptedException {
        MS_Equipment ms_equipment = new MS_Equipment();
        String s = lineReader.readLine("输入装备id>");
        try {
            int i = Integer.parseInt(s);
            ms_equipment.setModelId(i);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        RequestUtil.requestFuture(this.channel, getCode(), JSON.toJSONString(ms_equipment).getBytes());
    }
}
