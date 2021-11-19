package com.wzw.nettylog4j2.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;



public interface NettyClient {
    //
    public void run() throws InterruptedException;

    //连接和重连都调用这个
    public void connect() throws InterruptedException ;

}
