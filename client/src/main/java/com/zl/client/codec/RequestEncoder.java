package com.zl.client.codec;

import com.zl.client.common.Request;
import com.zl.common.common.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.springframework.stereotype.Component;


public class RequestEncoder extends MessageToByteEncoder<Request> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Request msg, ByteBuf out) throws Exception {
        out.writeInt(Constants.FLAG);
        out.writeInt(msg.getRequestId());
        out.writeInt(msg.getCommand());
        if(msg.getContent()!=null){
            out.writeInt(msg.getContent().length);
            out.writeBytes(msg.getContent());
        }else{
            out.writeInt(0);
        }


    }
}
