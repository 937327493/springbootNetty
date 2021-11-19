package com.wzw.nettylog4j2.code;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ByteProcessor;

import java.util.List;

public class SmaterClientDecoder extends ByteToMessageDecoder{

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.getBytes(0,bytes);
        //解码的时候我需要根据协议找到开头的长度为14个字节的数据，因为数据的最小长度为14个字节——28个16进制数
        if (byteBuf.readableBytes() < 14) {
            return;
        }
        ByteBuf byteBuf1 = Unpooled.wrappedBuffer(new byte[]{ -1,  -1});
        int i = indexOf(byteBuf,byteBuf1);
        byteBuf.skipBytes(i);
        int length =  byteBuf.getUnsignedShort(byteBuf.readerIndex()+2);
        //如果整个缓冲区的长度小于这条报文全长度则不进行处理
        if (length > byteBuf.readableBytes()) {
            return;
        }
        //读取数据放到list中
        ByteBuf byteBuf2 = byteBuf.readBytes(length);
        list.add(byteBuf2);
    }

    public static int indexOf(ByteBuf byteBuf,ByteBuf byteBuf1){

        for (int i = byteBuf.readerIndex(); i < byteBuf.writerIndex(); i++)
        {
            int haystackIndex = i;
            int needleIndex;
            for (needleIndex = 0; needleIndex < byteBuf1.capacity(); needleIndex++)
            {
                byte aByte = byteBuf.getByte(haystackIndex);
                byte aByte1 = byteBuf1.getByte(needleIndex);
                if (byteBuf.getByte(haystackIndex) != byteBuf1.getByte(needleIndex))
                {
                    break;
                }
                else
                {
                    haystackIndex++;
                    if (haystackIndex == byteBuf.writerIndex() && needleIndex != byteBuf1.capacity() - 1)
                    {
                        return -1;
                    }
                }
            }

            if (needleIndex == byteBuf1.capacity())
            {
                // Found the needle from the haystack!
                return i - byteBuf.readerIndex();
            }
        }
        return -1;
    }
}
