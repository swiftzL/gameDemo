package com.zl.server.netty.codec;

import com.alibaba.fastjson.JSON;
import com.zl.server.netty.commons.CodeConstants;
import com.zl.server.netty.model.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.springframework.stereotype.Component;

@Component
public class ResponseEncoder extends MessageToByteEncoder<Response> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Response msg, ByteBuf out) throws Exception {
        out.writeInt(CodeConstants.FLAG);
        out.writeInt(msg.getRequestId());
        out.writeInt(msg.getStatusCode());
        String s = JSON.toJSONString(msg.getContent());
        out.writeInt(s.getBytes().length);
        out.writeBytes(s.getBytes());
    }
}
