package com.wzw.nettylog4j2.code;

import com.wzw.nettylog4j2.entitis.NettyClientMessage;
import com.wzw.nettylog4j2.entitis.PlcMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.List;

public class ClientDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int unsignedShort = byteBuf.getUnsignedShort(10);
        if (unsignedShort == 1) {
            System.out.println("这是个心跳数据");
            return;
        }else if (unsignedShort == 0xFFFF){
            System.out.println("这是个错误数据");
            return;
        }
        //我需要把这个数据转成字符串打印在日志文件里，并且需要把这个字节数据发送到顺丰那里
        NettyClientMessage nettyClientMessage = new NettyClientMessage();
        nettyClientMessage.setSequence(SequenctManager.generateSequenceForClient());
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.getBytes(0,bytes);
        PlcMessage plcMessage = new PlcMessage();
        plcMessage.setMessageType(unsignedShort);
        plcMessage.setDataContext(String.valueOf(bytes).replaceAll("_",""));
        plcMessage.setSendBytes(bytes);
        List<PlcMessage> plcMessageList = nettyClientMessage.getPlcMessageList();
        plcMessageList.add(plcMessage);
        nettyClientMessage.setLength(byteBuf.getUnsignedShort(byteBuf.readerIndex()+2));
        byteBuf.skipBytes(byteBuf.getUnsignedShort(byteBuf.readerIndex()+2));
        //然后将数据加入链
        list.add(nettyClientMessage);
    }
}
