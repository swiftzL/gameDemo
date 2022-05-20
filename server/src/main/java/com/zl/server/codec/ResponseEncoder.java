package com.zl.server.codec;

import com.zl.server.commons.Constants;
import com.zl.server.commons.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.springframework.stereotype.Component;

@Component
public class ResponseEncoder extends MessageToByteEncoder<Response> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Response msg, ByteBuf out) throws Exception {
        out.writeInt(Constants.FLAG);
        out.writeInt(msg.getRequestId());
        out.writeInt(msg.getStatusCode());
        out.writeInt(msg.getContent().length);
        out.writeBytes(msg.getContent());
    }
}
