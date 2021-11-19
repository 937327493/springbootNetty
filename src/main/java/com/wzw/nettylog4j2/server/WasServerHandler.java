package com.wzw.nettylog4j2.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;

public class WasServerHandler extends SimpleChannelInboundHandler {
    //存储所有通道来监测是否断开
    public static HashMap<String, ChannelHandlerContext> socketChannelMap = new HashMap<>();
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String key;
        switch(ctx.channel().localAddress().toString()){
            case "/127.0.0.1:2004":
                key = "nettyServer01";
                break;
            case "/127.0.0.1:2005":
                key = "nettyServer02";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + ctx.channel().localAddress().toString());
        }
        socketChannelMap.put(key,ctx);
        super.channelActive(ctx);
    }
}
