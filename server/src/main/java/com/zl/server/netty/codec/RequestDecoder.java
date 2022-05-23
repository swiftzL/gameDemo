package com.zl.server.netty.codec;

import com.alibaba.fastjson.JSON;
import com.zl.server.commons.Constants;
import com.zl.server.netty.model.Request;
import com.zl.server.netty.config.NetMessageProcessor;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * flag-requestId-command-len-bytes
 * 4-4-4-4-
 */
@Slf4j
public class RequestDecoder extends ByteToMessageDecoder {

    private Map<Integer, Class<?>> commandToClass = NetMessageProcessor.commandToClass;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 16) {
            return;
        }
        in.markReaderIndex();
        if (Constants.FLAG != in.readInt()) {
            ctx.channel().close();
            log.error("错误请求");
        }
        int requestId = in.readInt();
        int command = in.readInt();
        int len = in.readInt();
        if (in.readableBytes() < len) {
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[len];
        in.readBytes(bytes);
        Request request = new Request();
        request.setRequestId(requestId);
        Object content = null;
        Class<?> clazz = commandToClass.get(command);
        if (clazz != null) {
            content = JSON.parseObject(bytes, clazz);
        }
        request.setCommand(command);
        request.setContent(content);
        out.add(request);
    }
}
