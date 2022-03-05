package com.xiaolong.netty.coder;

import com.xiaolong.netty.bean.impl.PacketCodeC;
import com.xiaolong.netty.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * @Description: 编码解码器
 * @Author xiaolong
 * @Date 2022/3/5 11:13 下午
 */
public class PacketDecoderEncoder extends MessageToMessageCodec<ByteBuf, Packet> {

    public static final PacketDecoderEncoder INSTANCE = new PacketDecoderEncoder();

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, List<Object> out) throws Exception {
        out.add(PacketCodeC.INSTANCE.encode(msg));
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        out.add(PacketCodeC.INSTANCE.decode(msg));
    }
}
