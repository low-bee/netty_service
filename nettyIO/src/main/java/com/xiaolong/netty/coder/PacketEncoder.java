package com.xiaolong.netty.coder;

import com.xiaolong.netty.packet.Packet;
import com.xiaolong.netty.bean.impl.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) {
        PacketCodeC.INSTANCE.encode(out, msg);
    }
}
