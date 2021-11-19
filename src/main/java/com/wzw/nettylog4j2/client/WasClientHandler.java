package com.wzw.nettylog4j2.client;

import com.wzw.nettylog4j2.MainApplication;
import com.wzw.nettylog4j2.entitis.NettyClientMessage;
import com.wzw.nettylog4j2.server.WasServerHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;

import java.net.SocketAddress;
import java.util.HashMap;

public class WasClientHandler extends SimpleChannelInboundHandler<NettyClientMessage> {
    //存储所有通道来监测是否断开
    public static HashMap<String, ChannelHandlerContext> socketChannelMap = new HashMap<>();
    //所有客户端
    public HashMap<String,NettyClient> hashMap = MainApplication.nettyClientHashMap;
    //当有消息可读


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        switch (ctx.channel().remoteAddress().toString()){
            case "/127.0.0.1:2000":
                ctx.pipeline().removeFirst();
                ctx.pipeline().removeFirst();
                ctx.pipeline().removeFirst();
                ctx.pipeline().removeFirst();
                socketChannelMap.remove("nettyClient01");
                MainApplication.nettyClientHashMap.get("nettyClient01").connect();
                ctx.channel().close();
                break;
            case "/127.0.0.1:2001":
                ctx.pipeline().removeFirst();
                ctx.pipeline().removeFirst();
                ctx.pipeline().removeFirst();
                ctx.pipeline().removeFirst();
                socketChannelMap.remove("nettyClient02");
                MainApplication.nettyClientHashMap.get("nettyClient02").connect();
                ctx.channel().close();
                break;
            case "/127.0.0.1:2002":
                ctx.pipeline().removeFirst();
                ctx.pipeline().removeFirst();
                ctx.pipeline().removeFirst();
                ctx.pipeline().removeFirst();
                socketChannelMap.remove("nettyClient03");
                MainApplication.nettyClientHashMap.get("nettyClient03").connect();
                ctx.channel().close();
                break;
            case "/127.0.0.1:2003":
                ctx.pipeline().removeFirst();
                ctx.pipeline().removeFirst();
                ctx.pipeline().removeFirst();
                ctx.pipeline().removeFirst();
                socketChannelMap.remove("nettyClient04");
                MainApplication.nettyClientHashMap.get("nettyClient04").connect();
                ctx.channel().close();
                break;
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, NettyClientMessage o) throws Exception {
        System.out.println(o);
        HashMap<String, ChannelHandlerContext> socketChannelMap = WasServerHandler.socketChannelMap;
        if (channelHandlerContext.channel().remoteAddress().toString().equals("/127.0.0.1:2000")) {
            ChannelHandlerContext nettyServer01 = WasServerHandler.socketChannelMap.get("nettyServer01");
            nettyServer01.channel().writeAndFlush(o);
        }
        if (channelHandlerContext.channel().remoteAddress().toString().equals("/127.0.0.1:2001")) {
            ChannelHandlerContext nettyServer02 = WasServerHandler.socketChannelMap.get("nettyServer02");
            nettyServer02.channel().writeAndFlush(o);
        }if (channelHandlerContext.channel().remoteAddress().toString().equals("/127.0.0.1:2002")) {
            ChannelHandlerContext nettyServer01 = WasServerHandler.socketChannelMap.get("nettyServer01");
            nettyServer01.channel().writeAndFlush(o);
        }if (channelHandlerContext.channel().remoteAddress().toString().equals("/127.0.0.1:2003")) {
            ChannelHandlerContext nettyServer02 = WasServerHandler.socketChannelMap.get("nettyServer02");
            nettyServer02.channel().writeAndFlush(o);
        }
    }

    //当连接建立成功
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String key;
        System.out.println(ctx.channel().remoteAddress());
        switch (ctx.channel().remoteAddress()+"") {
            case "/127.0.0.1:2000":
                key = "nettyClient01";
                break;
            case "/127.0.0.1:2001":
                key = "nettyClient02";
                break;
            case "/127.0.0.1:2002":
                key = "nettyClient03";
                break;
            case "/127.0.0.1:2003":
                key = "nettyClient04";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + ctx.channel().remoteAddress() + "");
        }
        //通道加入通道到集合
        socketChannelMap.put(key,ctx);
//        super.channelActive(ctx);
    }

    //四次挥手时
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String key;
        //当连接断开我们需要重连
        handlerRemoved(ctx);
        ctx.channel().close();
        switch (ctx.channel().remoteAddress()+"") {
            case "/127.0.0.1:2000":
                key = "nettyClient01";
                break;
            case "/127.0.0.1:2001":
                key = "nettyClient02";
                break;
            case "/127.0.0.1:2002":
                key = "nettyClient03";
                break;
            case "/127.0.0.1:2003":
                key = "nettyClient04";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + ctx.channel().remoteAddress() + "");
        }
        socketChannelMap.remove(key);
        hashMap.get(key).connect();
        super.channelInactive(ctx);
    }

    //清除Handler
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        ctx.pipeline().removeFirst();
        ctx.pipeline().removeFirst();
        ctx.pipeline().removeFirst();
        ctx.pipeline().removeFirst();
        super.handlerRemoved(ctx);
    }
}
