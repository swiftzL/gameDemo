package com.zl.server.codec;

import com.zl.server.commons.Constants;
import com.zl.server.commons.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * flag-requestId-command-len-bytes
 * 4-4-4-4-
 */
@Slf4j
@Component
public class RequestDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes()<16) {
            return;
        }
        in.markReaderIndex();
        if(Constants.FLAG!=in.readInt()){
            ctx.channel().close();
            log.error("错误请求");
        }
        int requestId = in.readInt();
        int command = in.readInt();
        int len = in.readInt();
        if(in.readableBytes()<len){
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[len];
        in.readBytes(bytes);
        Request request = new Request();
        request.setRequestId(requestId);
        request.setCommand(command);
        request.setContent(bytes);
        out.add(request);
    }
}
