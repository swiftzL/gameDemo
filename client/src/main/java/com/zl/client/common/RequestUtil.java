package com.zl.client.common;

import java.util.concurrent.atomic.AtomicInteger;

public class RequestUtil {
    private static AtomicInteger atomicInteger;

    public static Request request(int code, byte[] content) {
        Request request = new Request();
        request.setRequestId(atomicInteger.addAndGet(1));
        request.setCommand(code);
        request.setContent(content);
        return request;
    }
}
