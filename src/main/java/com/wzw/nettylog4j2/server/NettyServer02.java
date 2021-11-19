package com.wzw.nettylog4j2.server;

import com.wzw.nettylog4j2.client.WasClientInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "server.server02")
public class NettyServer02 implements NettyServer{
    private EventLoopGroup eventServerLoopGroup;
    private EventLoopGroup eventLoopGroup;
    private ServerBootstrap serverbootstrap;
    private String ip;
    private Integer port;
    public void run() throws InterruptedException {
        //创建线程数为1的Netty专有线程池
        eventServerLoopGroup = new NioEventLoopGroup(1);
        //创建线程数为12的Netty专有线程池
        eventLoopGroup = new NioEventLoopGroup(1);
        serverbootstrap = new ServerBootstrap();
        serverbootstrap.group(eventServerLoopGroup,eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new WasServerInitializer());
//        sync.channel().closeFuture().sync();
        connect();
    }

    @Override
    public void connect() throws InterruptedException {
        ChannelFuture sync = serverbootstrap.bind(port).sync().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
//                    System.out.println(channelFuture.channel().localAddress());
                    System.out.println("绑定成功");
                }
            }
        });
    }
}
