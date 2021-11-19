package com.wzw.nettylog4j2.code;

import com.wzw.nettylog4j2.entitis.NettyClientMessage;
import com.wzw.nettylog4j2.entitis.PlcMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ServerEncoder extends MessageToByteEncoder<NettyClientMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, NettyClientMessage nettyClientMessage, ByteBuf byteBuf) throws Exception {
        byteBuf.writeShort(nettyClientMessage.getStart());
        byteBuf.writeShort(nettyClientMessage.getLength());
        byteBuf.writeShort(nettyClientMessage.getSequence());
        byteBuf.writeShort(nettyClientMessage.getVersion());
        for (PlcMessage plcMessage : nettyClientMessage.getPlcMessageList()) {
            byteBuf.writeBytes(plcMessage.getSendBytes());
        }
    }
}
