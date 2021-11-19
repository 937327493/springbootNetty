package com.wzw.nettylog4j2.server;

import com.wzw.nettylog4j2.client.WasClientHandler;
import com.wzw.nettylog4j2.code.ClientDecoder;
import com.wzw.nettylog4j2.code.ServerEncoder;
import com.wzw.nettylog4j2.code.SmaterClientDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;

public class WasServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new ServerEncoder());
//        pipeline.addLast(null);
        pipeline.addLast(new WasServerHandler());
    }
}
