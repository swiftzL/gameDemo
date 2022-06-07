package com.zl.client.codec;

import com.zl.client.common.Response;
import com.zl.common.common.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
public class ResponseDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes()<16) {
            return;
        }
        in.markReaderIndex();
        if(Constants.FLAG!=in.readInt()){
            ctx.channel().close();
            log.error("错误请求");
            return;
        }
        int requestId = in.readInt();
        int statusCode = in.readInt();
        int len = in.readInt();
        if(in.readableBytes()<len){
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[len];
        in.readBytes(bytes);
        Response response = new Response();
        response.setRequestId(requestId);
        response.setStatusCode(statusCode);
        response.setContent(bytes);
        out.add(response);
    }
}
