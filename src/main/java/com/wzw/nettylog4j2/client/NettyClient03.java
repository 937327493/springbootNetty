package com.wzw.nettylog4j2.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "client.client03")
public class NettyClient03 implements NettyClient{
    private EventLoopGroup eventLoopGroup;
//    @Value("${client.client03.ip}")
    private String ip;
//    @Value("${client.client03.port}")

    private Integer port;
    private Bootstrap bootstrap;

    public void run() throws InterruptedException {
        //创建线程数为12的Netty专有线程池
        eventLoopGroup = new NioEventLoopGroup(1);
         bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,128)
                .handler(new WasClientInitializer());
        connect();
    }

    //连接和重连都调用这个
    public void connect() throws InterruptedException {
        ChannelFuture sync = bootstrap.connect(ip,port).sync().addListener(
                new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (channelFuture.isSuccess()) {
                            System.out.println("连接成功");
                        }else System.out.println("连接失败");
                    }
                }
        );
//        sync.channel().closeFuture().sync();
    }
}
