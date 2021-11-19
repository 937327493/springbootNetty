package com.wzw.nettylog4j2.client;

import com.wzw.nettylog4j2.code.ClientDecoder;
import com.wzw.nettylog4j2.code.SmaterClientDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class WasClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new SmaterClientDecoder());
        pipeline.addLast(new ClientDecoder());
//        pipeline.addLast(new IdleStateHandler(8,30,50, TimeUnit.SECONDS));
        pipeline.addLast(new WasClientHandler());
    }
}
