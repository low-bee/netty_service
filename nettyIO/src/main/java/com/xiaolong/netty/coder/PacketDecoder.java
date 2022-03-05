package com.xiaolong.netty.coder;

import com.xiaolong.netty.bean.Packet;
import com.xiaolong.netty.bean.impl.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        Packet decode = PacketCodeC.INSTANCE.decode(in);
        out.add(decode);
    }
}
