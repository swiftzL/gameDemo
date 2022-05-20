package com.zl.client.netty;

import com.alibaba.fastjson.JSON;
import com.zl.client.codec.RequestEncoder;
import com.zl.client.codec.ResponseDecoder;
import com.zl.client.common.Request;
import com.zl.client.common.RequestUtil;
import com.zl.client.handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
public class NettyClient {

    @Value("${gamedemo.host}")
    private String host;

    @Value("${gamedemo.port}")
    private int port;

    @Bean
    public Channel channel() throws InterruptedException {
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host, port)).handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ResponseDecoder()).addLast(new RequestEncoder()).addLast(new ClientHandler());
                    }
                });

        Channel channel = bootstrap.connect().sync().channel();
//        Request request = RequestUtil.request(1, "dd".getBytes());
//        channel.writeAndFlush(request);
        return channel;
    }
}
