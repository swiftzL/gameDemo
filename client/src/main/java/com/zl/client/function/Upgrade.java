package com.zl.client.function;

import com.zl.client.common.RequestUtil;

public class Upgrade extends Function{
    @Override
    public int getCode() {
        return 4;
    }

    @Override
    public void run() {
        System.out.println("升级中...");
        RequestUtil.request(getCode(),null);

    }
}
